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
  department_id NUMBER NULL,
  manager_id    NUMBER NULL,
  hire_date     DATE NOT NULL
);

CREATE TABLE products (
  product_id   NUMBER PRIMARY KEY,
  product_name VARCHAR2(80) NOT NULL,
  price        NUMBER NOT NULL
);

CREATE TABLE orders (
  order_id    NUMBER PRIMARY KEY,
  order_date  DATE NOT NULL
);

CREATE TABLE students (
  student_id   NUMBER PRIMARY KEY,
  student_name VARCHAR2(80) NOT NULL,
  gpa          NUMBER(3,2) NOT NULL
);

CREATE TABLE categories (
  category_id   NUMBER PRIMARY KEY,
  category_name VARCHAR2(50) NOT NULL
);

CREATE TABLE books (
  book_id     NUMBER PRIMARY KEY,
  book_title  VARCHAR2(80) NOT NULL,
  price       NUMBER NOT NULL,
  category_id NUMBER NOT NULL
);

-- -------------------------
-- INSERT SAMPLE DATA
-- -------------------------

-- Departments
INSERT INTO departments VALUES (10, 'IT');
INSERT INTO departments VALUES (20, 'HR');

-- Employees
-- Include employee 101 for task #8
INSERT INTO employees VALUES (101, 'Ahmed',  9000, 10, NULL, DATE '2024-01-10');
INSERT INTO employees VALUES (102, 'Alice', 12000, 10, 101,  DATE '2024-03-15');
INSERT INTO employees VALUES (103, 'Mona',  12000, 20, 101,  DATE '2024-02-01'); -- tie on highest salary
INSERT INTO employees VALUES (104, 'Sara',   8000, 10, 101,  DATE '2025-12-20'); -- most recent hire

-- Products
INSERT INTO products VALUES (1, 'Mouse',   25);
INSERT INTO products VALUES (2, 'Laptop', 1200);
INSERT INTO products VALUES (3, 'Cable',   10); -- lowest

-- Orders
INSERT INTO orders VALUES (5001, DATE '2025-01-05'); -- earliest
INSERT INTO orders VALUES (5002, DATE '2025-02-10');
INSERT INTO orders VALUES (5003, DATE '2025-01-05'); -- tie earliest

-- Students
INSERT INTO students VALUES (1, 'John Doe', 3.50);
INSERT INTO students VALUES (2, 'Hana',     3.50); -- same GPA as John Doe
INSERT INTO students VALUES (3, 'Karim',    2.90);

-- Categories
INSERT INTO categories VALUES (1, 'Science');
INSERT INTO categories VALUES (2, 'History');

-- Books
-- Most expensive Science book = 200
INSERT INTO books VALUES (10, 'Physics 101', 200, 1);
INSERT INTO books VALUES (11, 'Chemistry',   150, 1);
INSERT INTO books VALUES (12, 'World War',   200, 2); -- same price as most expensive Science, but different category

COMMIT;

-- =====================================================
-- SINGLE-ROW SUBQUERY QUESTIONS (Your Tasks)
-- =====================================================

-- 1) Employee(s) with the highest salary
SELECT employee_id, employee_name, salary
FROM employees
WHERE salary = (SELECT MAX(salary) FROM employees);

-- 2) Employees in the same department as 'Alice'
SELECT employee_id, employee_name, department_id
FROM employees
WHERE department_id = (
  SELECT department_id
  FROM employees
  WHERE employee_name = 'Alice'
);

-- 3) Product with the lowest price
SELECT product_id, product_name, price
FROM products
WHERE price = (SELECT MIN(price) FROM products);

-- 4) Department name of the employee with the highest salary (nested subqueries)
SELECT d.department_name
FROM departments d
WHERE d.department_id = (
  SELECT e.department_id
  FROM employees e
  WHERE e.salary = (SELECT MAX(salary) FROM employees)
  FETCH FIRST 1 ROW ONLY
);

-- 5) Manager of the employee hired most recently
SELECT m.employee_id AS manager_id,
       m.employee_name AS manager_name
FROM employees m
WHERE m.employee_id = (
  SELECT e.manager_id
  FROM employees e
  WHERE e.hire_date = (SELECT MAX(hire_date) FROM employees)
);

-- 6) Employee whose salary equals the company average salary
SELECT employee_id, employee_name, salary
FROM employees
WHERE salary = (SELECT AVG(salary) FROM employees);

-- 7) Order(s) with the earliest order date
SELECT order_id, order_date
FROM orders
WHERE order_date = (SELECT MIN(order_date) FROM orders);

-- 8) Employee(s) who earn more than employee with ID = 101
SELECT employee_id, employee_name, salary
FROM employees
WHERE salary > (
  SELECT salary
  FROM employees
  WHERE employee_id = 101
);

-- 9) Student(s) with the same GPA as 'John Doe'
SELECT student_id, student_name, gpa
FROM students
WHERE gpa = (
  SELECT gpa
  FROM students
  WHERE student_name = 'John Doe'
);

-- 10) Books with same price as the most expensive book in 'Science'
SELECT b.book_id, b.book_title, b.price
FROM books b
WHERE b.price = (
  SELECT MAX(b2.price)
  FROM books b2
  JOIN categories c
    ON b2.category_id = c.category_id
  WHERE c.category_name = 'Science'
);