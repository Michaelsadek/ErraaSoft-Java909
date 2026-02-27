-- =====================================================
-- CREATE TABLES
-- =====================================================

CREATE TABLE departments (
    department_id NUMBER PRIMARY KEY,
    department_name VARCHAR2(50)
);

CREATE TABLE jobs (
    job_id NUMBER PRIMARY KEY,
    job_title VARCHAR2(50)
);

CREATE TABLE employees (
    employee_id NUMBER PRIMARY KEY,
    employee_name VARCHAR2(50),
    department_id NUMBER,
    job_id NUMBER
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

CREATE TABLE shipping (
    order_id NUMBER,
    shipping_address VARCHAR2(100),
    shipping_date DATE
);

CREATE TABLE students (
    student_id NUMBER PRIMARY KEY,
    student_name VARCHAR2(50)
);

CREATE TABLE courses (
    course_id NUMBER PRIMARY KEY,
    course_name VARCHAR2(50)
);

CREATE TABLE enrollments (
    student_id NUMBER,
    course_id NUMBER
);

CREATE TABLE projects (
    project_id NUMBER PRIMARY KEY,
    project_name VARCHAR2(50)
);

CREATE TABLE project_assignments (
    project_id NUMBER,
    employee_id NUMBER
);

CREATE TABLE products (
    product_id NUMBER PRIMARY KEY,
    product_name VARCHAR2(50)
);

CREATE TABLE invoice_details (
    invoice_id NUMBER,
    product_id NUMBER,
    quantity NUMBER,
    price NUMBER
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

CREATE TABLE instructors (
    instructor_id NUMBER PRIMARY KEY,
    instructor_name VARCHAR2(50)
);

CREATE TABLE class_schedules (
    class_id NUMBER PRIMARY KEY,
    class_time VARCHAR2(20),
    instructor_id NUMBER
);

CREATE TABLE suppliers (
    supplier_id NUMBER PRIMARY KEY,
    supplier_name VARCHAR2(50)
);

CREATE TABLE supplier_products (
    supplier_id NUMBER,
    product_id NUMBER
);

-- =====================================================
-- INSERT SAMPLE DATA
-- =====================================================

INSERT INTO departments VALUES (1,'IT');
INSERT INTO departments VALUES (2,'HR');

INSERT INTO jobs VALUES (10,'Developer');
INSERT INTO jobs VALUES (20,'HR Manager');

INSERT INTO employees VALUES (101,'Ahmed',1,10);
INSERT INTO employees VALUES (102,'Mona',2,20);

INSERT INTO customers VALUES (1,'Ali');
INSERT INTO customers VALUES (2,'Sara');

INSERT INTO orders VALUES (1001,SYSDATE,1);
INSERT INTO orders VALUES (1002,SYSDATE,2);

INSERT INTO shipping VALUES (1001,'Cairo',SYSDATE);

INSERT INTO students VALUES (1,'Omar');
INSERT INTO students VALUES (2,'Lina');

INSERT INTO courses VALUES (10,'Database');
INSERT INTO courses VALUES (20,'Java');

INSERT INTO enrollments VALUES (1,10);
INSERT INTO enrollments VALUES (2,20);

INSERT INTO projects VALUES (1,'ERP System');
INSERT INTO project_assignments VALUES (1,101);

INSERT INTO products VALUES (1,'Laptop');
INSERT INTO invoice_details VALUES (5001,1,2,15000);

INSERT INTO authors VALUES (1,'Naguib Mahfouz');
INSERT INTO books VALUES (101,'Cairo Trilogy',1);

INSERT INTO instructors VALUES (1,'Dr. Hassan');
INSERT INTO class_schedules VALUES (10,'10:00 AM',1);

INSERT INTO suppliers VALUES (1,'Tech Supplies');
INSERT INTO supplier_products VALUES (1,1);

COMMIT;

-- =====================================================
-- ALL REQUIRED NATURAL JOIN QUERIES
-- =====================================================

-- 1 Employees & Departments
SELECT employee_name, department_name
FROM employees NATURAL JOIN departments;

-- 2 Orders & Customers
SELECT order_id, order_date, customer_name
FROM orders NATURAL JOIN customers;

-- 3 Students & Courses
SELECT student_name, course_name
FROM students NATURAL JOIN enrollments NATURAL JOIN courses;

-- 4 Projects & Employees
SELECT project_name, employee_name
FROM projects NATURAL JOIN project_assignments NATURAL JOIN employees;

-- 5 Invoice & Products
SELECT invoice_id, product_name, quantity, price
FROM invoice_details NATURAL JOIN products;

-- 6 Books & Authors
SELECT book_title, author_name
FROM books NATURAL JOIN authors;

-- 7 Class Schedules & Instructors
SELECT class_time, instructor_name
FROM class_schedules NATURAL JOIN instructors;

-- 8 Suppliers & Products
SELECT supplier_name, product_name
FROM suppliers NATURAL JOIN supplier_products NATURAL JOIN products;

-- 9 Orders & Shipping
SELECT order_id, order_date, shipping_address, shipping_date
FROM orders NATURAL JOIN shipping;

-- 10 Employees & Jobs
SELECT employee_name, job_title
FROM employees NATURAL JOIN jobs;