-- =====================================================
-- CREATE TABLES
-- =====================================================

CREATE TABLE departments (
    department_id NUMBER PRIMARY KEY,
    name VARCHAR2(50),
    location VARCHAR2(50),
    budget NUMBER
);

CREATE TABLE employees (
    employee_id NUMBER PRIMARY KEY,
    name VARCHAR2(50),
    salary NUMBER,
    department_id NUMBER,
    manager_id NUMBER,
    location VARCHAR2(50)
);

CREATE TABLE customers (
    customer_id NUMBER PRIMARY KEY,
    name VARCHAR2(50),
    salesperson_id NUMBER
);

CREATE TABLE orders (
    order_id NUMBER PRIMARY KEY,
    order_date DATE
);

CREATE TABLE order_details (
    order_id NUMBER,
    product_id NUMBER
);

CREATE TABLE students (
    student_id NUMBER PRIMARY KEY,
    name VARCHAR2(50),
    instructor_id NUMBER
);

CREATE TABLE instructors (
    instructor_id NUMBER PRIMARY KEY,
    name VARCHAR2(50)
);

CREATE TABLE projects (
    project_id NUMBER PRIMARY KEY,
    name VARCHAR2(50)
);

CREATE TABLE tasks (
    task_id NUMBER PRIMARY KEY,
    name VARCHAR2(50),
    project_id NUMBER
);

CREATE TABLE courses (
    course_id NUMBER PRIMARY KEY,
    start_date DATE
);

CREATE TABLE exams (
    exam_id NUMBER PRIMARY KEY,
    course_id NUMBER,
    exam_date DATE
);

CREATE TABLE categories (
    category_id NUMBER PRIMARY KEY,
    name VARCHAR2(50)
);

CREATE TABLE products (
    product_id NUMBER PRIMARY KEY,
    name VARCHAR2(50),
    category_id NUMBER
);

CREATE TABLE publishers (
    publisher_id NUMBER PRIMARY KEY,
    name VARCHAR2(50)
);

CREATE TABLE books (
    book_id NUMBER PRIMARY KEY,
    title VARCHAR2(50),
    publisher_id NUMBER
);

CREATE TABLE accounts (
    account_id NUMBER PRIMARY KEY,
    account_holder_name VARCHAR2(50)
);

CREATE TABLE transactions (
    transaction_id NUMBER PRIMARY KEY,
    account_id NUMBER,
    amount NUMBER
);

-- =====================================================
-- INSERT SAMPLE DATA
-- =====================================================

INSERT INTO departments VALUES (1,'IT','Cairo',100000);
INSERT INTO employees VALUES (101,'Ahmed',15000,1,NULL,'Giza');
INSERT INTO employees VALUES (102,'Mona',20000,1,101,'Cairo');

INSERT INTO customers VALUES (1,'Ali',101);

INSERT INTO orders VALUES (1001,SYSDATE);
INSERT INTO order_details VALUES (1001,1);

INSERT INTO students VALUES (1,'Omar',1);
INSERT INTO instructors VALUES (1,'Dr. Hassan');

INSERT INTO projects VALUES (1,'ERP');
INSERT INTO tasks VALUES (1,'Design',1);

INSERT INTO courses VALUES (1,SYSDATE);
INSERT INTO exams VALUES (1,1,SYSDATE+7);

INSERT INTO categories VALUES (1,'Electronics');
INSERT INTO products VALUES (1,'Laptop',1);

INSERT INTO publishers VALUES (1,'Penguin');
INSERT INTO books VALUES (1,'SQL Guide',1);

INSERT INTO accounts VALUES (1,'Sara');
INSERT INTO transactions VALUES (1,1,5000);

COMMIT;

-- =====================================================
-- REQUIRED QUERIES (RESOLVING AMBIGUITY USING ALIASES)
-- =====================================================

-- 1 Employee & Manager Names (Self Join)
SELECT e.name AS employee_name,
       m.name AS manager_name
FROM employees e
LEFT JOIN employees m
ON e.manager_id = m.employee_id;

-- 2 Customer & Salesperson Names
SELECT c.name AS customer_name,
       e.name AS salesperson_name
FROM customers c
JOIN employees e
ON c.salesperson_id = e.employee_id;

-- 3 Orders & Order Details
SELECT o.order_id,
       od.product_id
FROM orders o
JOIN order_details od
ON o.order_id = od.order_id;

-- 4 Student & Instructor Names
SELECT s.name AS student_name,
       i.name AS instructor_name
FROM students s
JOIN instructors i
ON s.instructor_id = i.instructor_id;

-- 5 Employee Salary & Department Budget
SELECT e.name AS employee_name,
       e.salary,
       d.budget
FROM employees e
JOIN departments d
ON e.department_id = d.department_id;

-- 6 Project & Task Names
SELECT p.name AS project_name,
       t.name AS task_name
FROM projects p
JOIN tasks t
ON p.project_id = t.project_id;

-- 7 Course & Exam Dates
SELECT c.course_id,
       c.start_date,
       e.exam_date
FROM courses c
JOIN exams e
ON c.course_id = e.course_id;

-- 8 Product & Category Names
SELECT p.name AS product_name,
       c.name AS category_name
FROM products p
JOIN categories c
ON p.category_id = c.category_id;

-- 9 Book Title & Publisher Name
SELECT b.title AS book_title,
       p.name AS publisher_name
FROM books b
JOIN publishers p
ON b.publisher_id = p.publisher_id;

-- 10 Employee & Department Location
SELECT e.name AS employee_name,
       d.location AS department_location
FROM employees e
JOIN departments d
ON e.department_id = d.department_id;

-- 11 Transactions & Account Holder Names
SELECT t.transaction_id,
       a.account_holder_name,
       t.amount
FROM transactions t
JOIN accounts a
ON t.account_id = a.account_id;