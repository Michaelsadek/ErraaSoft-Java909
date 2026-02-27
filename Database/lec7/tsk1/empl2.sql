-- =====================================================
-- CREATE TABLES
-- =====================================================

CREATE TABLE departments (
    department_id NUMBER PRIMARY KEY,
    department_name VARCHAR2(50)
);

CREATE TABLE employees (
    employee_id NUMBER PRIMARY KEY,
    employee_name VARCHAR2(50),
    department_id NUMBER,
    project_id NUMBER
);

CREATE TABLE customers (
    customer_id NUMBER PRIMARY KEY,
    customer_name VARCHAR2(50)
);

CREATE TABLE orders (
    order_id NUMBER PRIMARY KEY,
    order_date DATE,
    customer_id NUMBER
);

CREATE TABLE suppliers (
    supplier_id NUMBER PRIMARY KEY,
    supplier_name VARCHAR2(50)
);

CREATE TABLE products (
    product_id NUMBER PRIMARY KEY,
    product_name VARCHAR2(50),
    supplier_id NUMBER
);

CREATE TABLE students (
    student_id NUMBER PRIMARY KEY,
    student_name VARCHAR2(50)
);

CREATE TABLE enrollments (
    student_id NUMBER,
    course_title VARCHAR2(50)
);

CREATE TABLE invoices (
    invoice_number NUMBER PRIMARY KEY,
    product_id NUMBER
);

CREATE TABLE projects (
    project_id NUMBER PRIMARY KEY,
    project_name VARCHAR2(50)
);

CREATE TABLE authors (
    author_id NUMBER PRIMARY KEY,
    author_name VARCHAR2(50)
);

CREATE TABLE books (
    book_id NUMBER PRIMARY KEY,
    book_title VARCHAR2(50),
    author_id NUMBER
);

CREATE TABLE sales_orders (
    sales_order_id NUMBER PRIMARY KEY,
    employee_id NUMBER
);

CREATE TABLE instructors (
    instructor_id NUMBER PRIMARY KEY,
    instructor_name VARCHAR2(50)
);

CREATE TABLE course_schedules (
    schedule_id NUMBER PRIMARY KEY,
    schedule_time VARCHAR2(50),
    instructor_id NUMBER
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

INSERT INTO departments VALUES (1,'IT');
INSERT INTO employees VALUES (101,'Ahmed',1,1);

INSERT INTO customers VALUES (1,'Ali');
INSERT INTO orders VALUES (1001,SYSDATE,1);

INSERT INTO suppliers VALUES (1,'Tech Supplies');
INSERT INTO products VALUES (1,'Laptop',1);

INSERT INTO students VALUES (1,'Omar');
INSERT INTO enrollments VALUES (1,'Database');

INSERT INTO invoices VALUES (5001,1);

INSERT INTO projects VALUES (1,'ERP System');

INSERT INTO authors VALUES (1,'Naguib Mahfouz');
INSERT INTO books VALUES (101,'Cairo Trilogy',1);

INSERT INTO sales_orders VALUES (9001,101);

INSERT INTO instructors VALUES (1,'Dr. Hassan');
INSERT INTO course_schedules VALUES (1,'10:00 AM',1);

INSERT INTO accounts VALUES (1,'Mona');
INSERT INTO transactions VALUES (7001,1,5000);

COMMIT;

-- =====================================================
-- REQUIRED USING CLAUSE QUERIES
-- =====================================================

-- 1 Employees & Departments
SELECT employee_name, department_name
FROM employees
JOIN departments USING (department_id);

-- 2 Orders & Customers
SELECT order_id, order_date, customer_name
FROM orders
JOIN customers USING (customer_id);

-- 3 Products & Suppliers
SELECT product_name, supplier_name
FROM products
JOIN suppliers USING (supplier_id);

-- 4 Students & Enrollments
SELECT student_name, course_title
FROM students
JOIN enrollments USING (student_id);

-- 5 Invoices & Products
SELECT invoice_number, product_name
FROM invoices
JOIN products USING (product_id);

-- 6 Projects & Employees
SELECT project_name, employee_name
FROM projects
JOIN employees USING (project_id);

-- 7 Authors & Books
SELECT author_name, book_title
FROM authors
JOIN books USING (author_id);

-- 8 Sales Orders & Employees
SELECT sales_order_id, employee_name
FROM sales_orders
JOIN employees USING (employee_id);

-- 9 Course Schedules & Instructors
SELECT schedule_time, instructor_name
FROM course_schedules
JOIN instructors USING (instructor_id);

-- 10 Transactions & Accounts
SELECT transaction_id, account_holder_name, amount
FROM transactions
JOIN accounts USING (account_id);