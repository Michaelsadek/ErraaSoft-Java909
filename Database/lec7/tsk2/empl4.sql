-- -------------------------
-- CREATE TABLES
-- -------------------------

CREATE TABLE departments (
  department_id   NUMBER PRIMARY KEY,
  department_name VARCHAR2(50) NOT NULL
);

CREATE TABLE employees (
  employee_id   NUMBER PRIMARY KEY,
  employee_name VARCHAR2(80) NOT NULL,
  salary        NUMBER NOT NULL,
  department_id NUMBER NULL
);

CREATE TABLE customers (
  customer_id   NUMBER PRIMARY KEY,
  customer_name VARCHAR2(80) NOT NULL,
  city          VARCHAR2(50) NOT NULL
);

CREATE TABLE orders (
  order_id    NUMBER PRIMARY KEY,
  order_date  DATE NOT NULL,
  customer_id NUMBER NOT NULL
);

CREATE TABLE categories (
  category_id   NUMBER PRIMARY KEY,
  category_name VARCHAR2(50) NOT NULL
);

CREATE TABLE products (
  product_id   NUMBER PRIMARY KEY,
  product_name VARCHAR2(80) NOT NULL,
  price        NUMBER NOT NULL,
  category_id  NUMBER NULL
);

CREATE TABLE order_details (
  order_id   NUMBER NOT NULL,
  product_id NUMBER NOT NULL
);

CREATE TABLE students (
  student_id   NUMBER PRIMARY KEY,
  student_name VARCHAR2(80) NOT NULL
);

CREATE TABLE enrollments (
  student_id NUMBER NOT NULL,
  course_id  NUMBER NOT NULL
);

-- -------------------------
-- INSERT SAMPLE DATA
-- -------------------------

-- Departments
INSERT INTO departments VALUES (10, 'IT');
INSERT INTO departments VALUES (20, 'HR');
INSERT INTO departments VALUES (30, 'Finance'); -- will have no employees

-- Employees
INSERT INTO employees VALUES (1, 'John Smith', 12000, 10);
INSERT INTO employees VALUES (2, 'Ahmed Ali',  9000, 10);
INSERT INTO employees VALUES (3, 'Mona Said',  8000, 20);
INSERT INTO employees VALUES (4, 'Sara Noor', 15000, 10);

-- Customers
INSERT INTO customers VALUES (1, 'Ali',  'New York');
INSERT INTO customers VALUES (2, 'Omar', 'Boston');
INSERT INTO customers VALUES (3, 'Lina', 'New York');
INSERT INTO customers VALUES (4, 'Nour', 'Chicago');

-- Orders (customer 1 has 3 orders, customer 3 has 3 orders => tie for max)
INSERT INTO orders VALUES (101, SYSDATE-10, 1);
INSERT INTO orders VALUES (102, SYSDATE-9,  1);
INSERT INTO orders VALUES (103, SYSDATE-8,  1);
INSERT INTO orders VALUES (104, SYSDATE-7,  2);
INSERT INTO orders VALUES (105, SYSDATE-6,  3);
INSERT INTO orders VALUES (106, SYSDATE-5,  3);
INSERT INTO orders VALUES (107, SYSDATE-4,  3);

-- Categories
INSERT INTO categories VALUES (1, 'Accessories');
INSERT INTO categories VALUES (2, 'A');          -- category used in "ordered all products in category A"
INSERT INTO categories VALUES (3, 'Electronics');

-- Products
INSERT INTO products VALUES (10, 'Phone Case',     20,  1); -- Accessories
INSERT INTO products VALUES (11, 'Charger',        30,  1); -- Accessories
INSERT INTO products VALUES (12, 'Laptop',       1200,  3);
INSERT INTO products VALUES (13, 'Mouse',          25,  2); -- Category A
INSERT INTO products VALUES (14, 'Keyboard',       60,  2); -- Category A
INSERT INTO products VALUES (15, 'Monitor',       300,  3);

-- Order details:
-- Customer 1 ordered all products in category 'A' (Mouse, Keyboard)
INSERT INTO order_details VALUES (101, 13);
INSERT INTO order_details VALUES (102, 14);
-- Customer 3 ordered only Mouse (not all A)
INSERT INTO order_details VALUES (105, 13);
-- Some other lines
INSERT INTO order_details VALUES (104, 10);
INSERT INTO order_details VALUES (106, 12);

-- Students + Enrollments (student 2 not enrolled)
INSERT INTO students VALUES (1, 'Hana');
INSERT INTO students VALUES (2, 'Karim'); -- not enrolled
INSERT INTO students VALUES (3, 'Youssef');

INSERT INTO enrollments VALUES (1, 100);
INSERT INTO enrollments VALUES (3, 200);

COMMIT;

-- =====================================================
-- SUBQUERY QUESTIONS (Your Tasks)
-- =====================================================

-- 1) Employees who earn more than the average salary
SELECT employee_name
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);

-- 2) Customers who placed the highest number of orders (handles ties)
SELECT c.customer_id, c.customer_name
FROM customers c
JOIN (
  SELECT customer_id, COUNT(*) AS order_count
  FROM orders
  GROUP BY customer_id
) oc
  ON c.customer_id = oc.customer_id
WHERE oc.order_count = (
  SELECT MAX(cnt)
  FROM (
    SELECT COUNT(*) AS cnt
    FROM orders
    GROUP BY customer_id
  )
);

-- 3) Products whose price is higher than ANY product in the 'Accessories' category
SELECT p.product_id, p.product_name, p.price
FROM products p
WHERE p.price > ANY (
  SELECT p2.price
  FROM products p2
  JOIN categories c2
    ON p2.category_id = c2.category_id
  WHERE c2.category_name = 'Accessories'
);

-- 4) Employees who work in the same department as 'John Smith'
SELECT e.employee_name
FROM employees e
WHERE e.department_id = (
  SELECT department_id
  FROM employees
  WHERE employee_name = 'John Smith'
);

-- 5) Orders placed by customers from 'New York'
SELECT o.order_id, o.order_date, o.customer_id
FROM orders o
WHERE o.customer_id IN (
  SELECT c.customer_id
  FROM customers c
  WHERE c.city = 'New York'
);

-- 6) Departments that have no employees (NOT EXISTS)
SELECT d.department_id, d.department_name
FROM departments d
WHERE NOT EXISTS (
  SELECT 1
  FROM employees e
  WHERE e.department_id = d.department_id
);

-- 7) Students who are not enrolled in any course (NOT EXISTS)
SELECT s.student_id, s.student_name
FROM students s
WHERE NOT EXISTS (
  SELECT 1
  FROM enrollments e
  WHERE e.student_id = s.student_id
);

-- 8) Second highest salary (MAX with WHERE)
SELECT MAX(salary) AS second_highest_salary
FROM employees
WHERE salary < (SELECT MAX(salary) FROM employees);

-- 9) Products with price greater than average price of all products (scalar subquery)
SELECT product_id, product_name, price
FROM products
WHERE price > (SELECT AVG(price) FROM products);

-- 10) Customers who have ordered ALL products in category 'A' (correlated NOT EXISTS)
-- Logic: There must NOT exist a product in category A that the customer has NOT ordered.
SELECT c.customer_id, c.customer_name
FROM customers c
WHERE NOT EXISTS (
  SELECT 1
  FROM products p
  JOIN categories cat
    ON p.category_id = cat.category_id
  WHERE cat.category_name = 'A'
    AND NOT EXISTS (
      SELECT 1
      FROM orders o
      JOIN order_details od
        ON od.order_id = o.order_id
      WHERE o.customer_id = c.customer_id
        AND od.product_id = p.product_id
    )
);