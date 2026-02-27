-- -------------------------
-- CREATE TABLES
-- -------------------------

-- Departments + Employees (dept may have manager; employee may have no dept)
CREATE TABLE departments (
  department_id   NUMBER PRIMARY KEY,
  department_name VARCHAR2(50) NOT NULL,
  manager_id      NUMBER NULL
);

CREATE TABLE employees (
  employee_id   NUMBER PRIMARY KEY,
  employee_name VARCHAR2(50) NOT NULL,
  department_id NUMBER NULL
);

-- Categories + Products (product may have no category)
CREATE TABLE categories (
  category_id   NUMBER PRIMARY KEY,
  category_name VARCHAR2(50) NOT NULL
);

CREATE TABLE products (
  product_id   NUMBER PRIMARY KEY,
  product_name VARCHAR2(50) NOT NULL,
  category_id  NUMBER NULL
);

-- Customers + Orders (order may have customer_id that doesn't match or is null)
CREATE TABLE customers (
  customer_id   NUMBER PRIMARY KEY,
  customer_name VARCHAR2(50) NOT NULL
);

CREATE TABLE orders (
  order_id    NUMBER PRIMARY KEY,
  order_date  DATE NOT NULL,
  customer_id NUMBER NULL
);

-- Students + Courses + Enrollments (student may have no enrollments)
CREATE TABLE students (
  student_id   NUMBER PRIMARY KEY,
  student_name VARCHAR2(50) NOT NULL
);

CREATE TABLE courses (
  course_id    NUMBER PRIMARY KEY,
  course_title VARCHAR2(50) NOT NULL
);

CREATE TABLE enrollments (
  student_id NUMBER NOT NULL,
  course_id  NUMBER NOT NULL
);

-- Authors + Books (book may have no author)
CREATE TABLE authors (
  author_id   NUMBER PRIMARY KEY,
  author_name VARCHAR2(50) NOT NULL
);

CREATE TABLE books (
  book_id    NUMBER PRIMARY KEY,
  book_title VARCHAR2(80) NOT NULL,
  author_id  NUMBER NULL
);

-- Invoices + Payments (invoice may have no payment)
CREATE TABLE invoices (
  invoice_id     NUMBER PRIMARY KEY,
  invoice_number VARCHAR2(30) NOT NULL,
  amount         NUMBER NOT NULL
);

CREATE TABLE payments (
  payment_id NUMBER PRIMARY KEY,
  invoice_id NUMBER NOT NULL,
  status     VARCHAR2(20) NOT NULL
);

-- Employees + Projects Assigned (employee may have no project)
CREATE TABLE projects_assigned (
  employee_id   NUMBER NOT NULL,
  project_name  VARCHAR2(80) NOT NULL
);

-- -------------------------
-- INSERT SAMPLE DATA
-- -------------------------

-- Employees/Departments
INSERT INTO departments VALUES (10, 'IT', 1);
INSERT INTO departments VALUES (20, 'HR', NULL);        -- no manager
INSERT INTO departments VALUES (30, 'Finance', 999);    -- manager_id doesn't exist in employees (to show LEFT join still keeps dept)

INSERT INTO employees VALUES (1, 'Ahmed', 10);
INSERT INTO employees VALUES (2, 'Mona', 10);
INSERT INTO employees VALUES (3, 'Sara', NULL);         -- no department

-- Categories/Products
INSERT INTO categories VALUES (1, 'Electronics');
INSERT INTO categories VALUES (2, 'Books');

INSERT INTO products VALUES (100, 'Laptop', 1);
INSERT INTO products VALUES (101, 'Notebook', NULL);    -- no category

-- Customers/Orders
INSERT INTO customers VALUES (1, 'Ali');
INSERT INTO customers VALUES (2, 'Omar');

INSERT INTO orders VALUES (5001, SYSDATE, 1);
INSERT INTO orders VALUES (5002, SYSDATE, NULL);        -- order without customer
INSERT INTO orders VALUES (5003, SYSDATE, 999);         -- customer_id not found

-- Students/Courses/Enrollments
INSERT INTO students VALUES (1, 'Hana');
INSERT INTO students VALUES (2, 'Youssef');             -- not enrolled

INSERT INTO courses VALUES (10, 'Database');
INSERT INTO courses VALUES (20, 'Java');

INSERT INTO enrollments VALUES (1, 10);

-- Authors/Books
INSERT INTO authors VALUES (1, 'Naguib Mahfouz');

INSERT INTO books VALUES (1001, 'Cairo Trilogy', 1);
INSERT INTO books VALUES (1002, 'Unknown Author Book', NULL);

-- Invoices/Payments
INSERT INTO invoices VALUES (1, 'INV-001', 1500);
INSERT INTO invoices VALUES (2, 'INV-002', 2200);       -- no payment

INSERT INTO payments VALUES (10, 1, 'PAID');

-- Projects assigned
INSERT INTO projects_assigned VALUES (1, 'ERP Migration');
-- employee 2 has no project, employee 3 has no project

COMMIT;

-- =====================================================
-- LEFT OUTER JOIN QUERIES (Your Tasks)
-- =====================================================

-- 1) Retrieve all employees and their department names (include employees with no department)
SELECT e.employee_name,
       d.department_name
FROM employees e
LEFT OUTER JOIN departments d
  ON e.department_id = d.department_id;

-- 2) List all products and their associated categories (include products with no category)
SELECT p.product_name,
       c.category_name
FROM products p
LEFT JOIN categories c
  ON p.category_id = c.category_id;

-- 3) Find all students and the courses they are enrolled in (include students with no courses)
SELECT s.student_name,
       c.course_title
FROM students s
LEFT OUTER JOIN enrollments e
  ON s.student_id = e.student_id
LEFT OUTER JOIN courses c
  ON e.course_id = c.course_id;

-- 4) Display all orders with customer names (include orders without matched customer)
SELECT o.order_id,
       o.order_date,
       c.customer_name
FROM orders o
LEFT JOIN customers c
  ON o.customer_id = c.customer_id;

-- 5) Show all departments and their managers (include departments with no manager)
-- (Manager is an employee; self-reference via manager_id)
SELECT d.department_name,
       m.employee_name AS manager_name
FROM departments d
LEFT OUTER JOIN employees m
  ON d.manager_id = m.employee_id;

-- 6) List all books and their authors (include books with no author)
SELECT b.book_title,
       a.author_name
FROM books b
LEFT JOIN authors a
  ON b.author_id = a.author_id;

-- 7) Retrieve all invoices along with payment status (include invoices with no payment)
SELECT i.invoice_number,
       i.amount,
       p.status AS payment_status
FROM invoices i
LEFT JOIN payments p
  ON i.invoice_id = p.invoice_id;

-- 8) Get all employees and their projects (include employees with no project)
SELECT e.employee_name,
       pa.project_name
FROM employees e
LEFT OUTER JOIN projects_assigned pa
  ON e.employee_id = pa.employee_id;