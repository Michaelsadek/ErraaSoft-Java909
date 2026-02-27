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
  department_id NUMBER NOT NULL,
  job_title     VARCHAR2(50) NOT NULL
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
  category_id  NUMBER NOT NULL
);

CREATE TABLE order_details (
  order_id   NUMBER NOT NULL,
  product_id NUMBER NOT NULL
);

CREATE TABLE authors (
  author_id   NUMBER PRIMARY KEY,
  author_name VARCHAR2(80) NOT NULL
);

CREATE TABLE books (
  book_id    NUMBER PRIMARY KEY,
  book_title VARCHAR2(80) NOT NULL,
  author_id  NUMBER NOT NULL
);

-- Students / Courses / Professors
CREATE TABLE students (
  student_id   NUMBER PRIMARY KEY,
  student_name VARCHAR2(80) NOT NULL
);

CREATE TABLE professors (
  professor_id   NUMBER PRIMARY KEY,
  professor_name VARCHAR2(80) NOT NULL
);

CREATE TABLE courses (
  course_id    NUMBER PRIMARY KEY,
  course_title VARCHAR2(80) NOT NULL
);

-- which professor teaches which course (many-to-many)
CREATE TABLE course_offerings (
  course_id    NUMBER NOT NULL,
  professor_id NUMBER NOT NULL
);

-- student enrollments
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
INSERT INTO departments VALUES (30, 'Finance');

-- Employees
-- Dept 10 salaries: 7000, 12000
-- Dept 20 salaries: 8000, 9000
-- Dept 30 salaries: 5000, 11000
INSERT INTO employees VALUES (1, 'Ahmed',   7000, 10, 'Developer');
INSERT INTO employees VALUES (2, 'Mona',   12000, 10, 'Developer');
INSERT INTO employees VALUES (3, 'Sara',    8000, 20, 'HR Specialist');
INSERT INTO employees VALUES (4, 'Omar',    9000, 20, 'HR Specialist');
INSERT INTO employees VALUES (5, 'Lina',    5000, 30, 'Accountant');
INSERT INTO employees VALUES (6, 'Youssef',11000, 30, 'Analyst');

-- Customers
-- Cities: New York has 2 customers (both place orders), Boston has 1 (places orders), Chicago has 1 (no orders)
INSERT INTO customers VALUES (1, 'Ali',  'New York');
INSERT INTO customers VALUES (2, 'Nour', 'New York');
INSERT INTO customers VALUES (3, 'Karim','Boston');
INSERT INTO customers VALUES (4, 'Hana', 'Chicago'); -- no orders

-- Orders
INSERT INTO orders VALUES (101, SYSDATE-10, 1);
INSERT INTO orders VALUES (102, SYSDATE-9,  2);
INSERT INTO orders VALUES (103, SYSDATE-8,  3);

-- Categories + Products
INSERT INTO categories VALUES (1, 'Electronics');
INSERT INTO categories VALUES (2, 'Accessories');

INSERT INTO products VALUES (10, 'Laptop',      1200, 1);
INSERT INTO products VALUES (11, 'Phone',        900, 1);
INSERT INTO products VALUES (12, 'Mouse',         25, 2);
INSERT INTO products VALUES (13, 'Monitor',      300, 1);

-- Order details (customer 1 ordered Laptop, customer 2 ordered Mouse, customer 3 ordered Phone)
INSERT INTO order_details VALUES (101, 10);
INSERT INTO order_details VALUES (102, 12);
INSERT INTO order_details VALUES (103, 11);

-- Authors + Books (author 1 has 2 books, author 2 has 1)
INSERT INTO authors VALUES (1, 'Author One');
INSERT INTO authors VALUES (2, 'Author Two');

INSERT INTO books VALUES (1, 'Book A', 1);
INSERT INTO books VALUES (2, 'Book B', 1);
INSERT INTO books VALUES (3, 'Book C', 2);

-- Professors / Courses / Offerings / Enrollments
INSERT INTO professors VALUES (1, 'Dr. Smith');
INSERT INTO professors VALUES (2, 'Dr. Lee');

INSERT INTO courses VALUES (10, 'Database');
INSERT INTO courses VALUES (20, 'Java');
INSERT INTO courses VALUES (30, 'Networks');

-- Dr. Smith teaches Database + Java
INSERT INTO course_offerings VALUES (10, 1);
INSERT INTO course_offerings VALUES (20, 1);
-- Dr. Lee teaches Networks
INSERT INTO course_offerings VALUES (30, 2);

INSERT INTO students VALUES (1, 'Hossam');
INSERT INTO students VALUES (2, 'Mariam');
INSERT INTO students VALUES (3, 'Salma');

-- Enrollments: Hossam in Database, Mariam in Java, Salma in Networks
INSERT INTO enrollments VALUES (1, 10);
INSERT INTO enrollments VALUES (2, 20);
INSERT INTO enrollments VALUES (3, 30);

COMMIT;

-- =====================================================
-- MULTIPLE-ROW SUBQUERY QUESTIONS (Your Tasks)
-- =====================================================

-- 1) Employees who earn more than at least one employee in department 10 (ANY/SOME)
SELECT employee_id, employee_name, salary
FROM employees
WHERE salary > ANY (
  SELECT salary
  FROM employees
  WHERE department_id = 10
);

-- 2) Employees who earn less than all employees in department 20 (ALL)
SELECT employee_id, employee_name, salary
FROM employees
WHERE salary < ALL (
  SELECT salary
  FROM employees
  WHERE department_id = 20
);

-- 3) Products whose price equals any product in 'Electronics' category (IN)
SELECT product_id, product_name, price
FROM products
WHERE price IN (
  SELECT p2.price
  FROM products p2
  JOIN categories c2
    ON p2.category_id = c2.category_id
  WHERE c2.category_name = 'Electronics'
);

-- 4) Customers who placed an order for a product with price > 1000 (IN)
SELECT customer_id, customer_name
FROM customers
WHERE customer_id IN (
  SELECT o.customer_id
  FROM orders o
  WHERE o.order_id IN (
    SELECT od.order_id
    FROM order_details od
    WHERE od.product_id IN (
      SELECT p.product_id
      FROM products p
      WHERE p.price > 1000
    )
  )
);

-- 5) Employees who work in job titles shared by at least one other employee
SELECT employee_id, employee_name, job_title
FROM employees
WHERE job_title IN (
  SELECT job_title
  FROM employees
  GROUP BY job_title
  HAVING COUNT(*) > 1
);

-- 6) Departments that have more than one employee
SELECT department_id, department_name
FROM departments
WHERE department_id IN (
  SELECT department_id
  FROM employees
  GROUP BY department_id
  HAVING COUNT(*) > 1
);

-- 7) Orders placed by customers from cities where other customers have placed orders too
-- City qualifies if it has >= 2 distinct customers with at least one order.
SELECT o.order_id, o.order_date, o.customer_id
FROM orders o
WHERE o.customer_id IN (
  SELECT c.customer_id
  FROM customers c
  WHERE c.city IN (
    SELECT c2.city
    FROM customers c2
    WHERE c2.customer_id IN (SELECT customer_id FROM orders)
    GROUP BY c2.city
    HAVING COUNT(DISTINCT c2.customer_id) >= 2
  )
);

-- 8) Books written by authors who published more than one book
SELECT book_id, book_title, author_id
FROM books
WHERE author_id IN (
  SELECT author_id
  FROM books
  GROUP BY author_id
  HAVING COUNT(*) > 1
);

-- 9) Students enrolled in any courses taught by 'Dr. Smith'
SELECT s.student_id, s.student_name
FROM students s
WHERE s.student_id IN (
  SELECT e.student_id
  FROM enrollments e
  WHERE e.course_id IN (
    SELECT co.course_id
    FROM course_offerings co
    WHERE co.professor_id = (
      SELECT professor_id
      FROM professors
      WHERE professor_name = 'Dr. Smith'
    )
  )
);

-- 10) Employees whose salary matches any salary in department 30 (IN)
SELECT employee_id, employee_name, salary
FROM employees
WHERE salary IN (
  SELECT salary
  FROM employees
  WHERE department_id = 30
);