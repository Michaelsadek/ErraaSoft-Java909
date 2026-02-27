-- -------------------------
-- CREATE TABLES
-- -------------------------

-- Departments + Employees
CREATE TABLE departments (
  department_id   NUMBER PRIMARY KEY,
  department_name VARCHAR2(50) NOT NULL
);

CREATE TABLE employees (
  employee_id   NUMBER PRIMARY KEY,
  employee_name VARCHAR2(50) NOT NULL,
  department_id NUMBER NULL
);

-- Customers + Orders
CREATE TABLE customers (
  customer_id   NUMBER PRIMARY KEY,
  customer_name VARCHAR2(50) NOT NULL
);

CREATE TABLE orders (
  order_id    NUMBER PRIMARY KEY,
  order_date  DATE NOT NULL,
  customer_id NUMBER NULL
);

-- Courses + Students + Enrollments
CREATE TABLE courses (
  course_id    NUMBER PRIMARY KEY,
  course_title VARCHAR2(50) NOT NULL
);

CREATE TABLE students (
  student_id   NUMBER PRIMARY KEY,
  student_name VARCHAR2(50) NOT NULL
);

CREATE TABLE enrollments (
  student_id NUMBER NOT NULL,
  course_id  NUMBER NOT NULL
);

-- Projects + Employee Assignments
CREATE TABLE projects (
  project_id   NUMBER PRIMARY KEY,
  project_name VARCHAR2(80) NOT NULL
);

CREATE TABLE employee_projects (
  employee_id NUMBER NOT NULL,
  project_id  NUMBER NOT NULL
);

-- Payment Methods + Transactions
CREATE TABLE payment_methods (
  payment_method_id NUMBER PRIMARY KEY,
  method_name       VARCHAR2(50) NOT NULL
);

CREATE TABLE transactions (
  transaction_id     NUMBER PRIMARY KEY,
  payment_method_id  NUMBER NULL,
  amount             NUMBER NOT NULL
);

-- Authors + Books
CREATE TABLE authors (
  author_id   NUMBER PRIMARY KEY,
  author_name VARCHAR2(50) NOT NULL
);

CREATE TABLE books (
  book_id    NUMBER PRIMARY KEY,
  book_title VARCHAR2(80) NOT NULL,
  author_id  NUMBER NULL
);

-- Categories + Products
CREATE TABLE categories (
  category_id   NUMBER PRIMARY KEY,
  category_name VARCHAR2(50) NOT NULL
);

CREATE TABLE products (
  product_id   NUMBER PRIMARY KEY,
  product_name VARCHAR2(50) NOT NULL,
  category_id  NUMBER NULL
);

-- Dorm Rooms + Student Assignments
CREATE TABLE dorm_rooms (
  dorm_room_id NUMBER PRIMARY KEY,
  room_number  VARCHAR2(20) NOT NULL
);

CREATE TABLE student_dorm_assignments (
  student_id   NUMBER NOT NULL,
  dorm_room_id NUMBER NOT NULL
);

-- -------------------------
-- INSERT SAMPLE DATA
-- -------------------------

-- Departments/Employees (dept 30 has no employees)
INSERT INTO departments VALUES (10, 'IT');
INSERT INTO departments VALUES (20, 'HR');
INSERT INTO departments VALUES (30, 'Finance'); -- no employees

INSERT INTO employees VALUES (1, 'Ahmed', 10);
INSERT INTO employees VALUES (2, 'Mona', 10);
INSERT INTO employees VALUES (3, 'Sara', NULL); -- no dept

-- Customers/Orders (customer 3 has no orders)
INSERT INTO customers VALUES (1, 'Ali');
INSERT INTO customers VALUES (2, 'Omar');
INSERT INTO customers VALUES (3, 'Lina'); -- no orders

INSERT INTO orders VALUES (5001, SYSDATE, 1);
INSERT INTO orders VALUES (5002, SYSDATE, 2);

-- Courses/Students/Enrollments (course 30 has no students)
INSERT INTO courses VALUES (10, 'Database');
INSERT INTO courses VALUES (20, 'Java');
INSERT INTO courses VALUES (30, 'Networking'); -- no enrollments

INSERT INTO students VALUES (1, 'Hana');
INSERT INTO students VALUES (2, 'Youssef'); -- may be unenrolled

INSERT INTO enrollments VALUES (1, 10);

-- Projects/Employee Projects (project 3 has no employees)
INSERT INTO projects VALUES (1, 'ERP Migration');
INSERT INTO projects VALUES (2, 'Mobile App');
INSERT INTO projects VALUES (3, 'Data Warehouse'); -- no assignments

INSERT INTO employee_projects VALUES (1, 1);
INSERT INTO employee_projects VALUES (2, 1);

-- Payment Methods/Transactions (method 3 has no transactions)
INSERT INTO payment_methods VALUES (1, 'Credit Card');
INSERT INTO payment_methods VALUES (2, 'Bank Transfer');
INSERT INTO payment_methods VALUES (3, 'Cash'); -- no transactions

INSERT INTO transactions VALUES (100, 1, 2500);
INSERT INTO transactions VALUES (101, 2, 1800);

-- Authors/Books (author 2 has no books)
INSERT INTO authors VALUES (1, 'Naguib Mahfouz');
INSERT INTO authors VALUES (2, 'New Author'); -- no books

INSERT INTO books VALUES (1, 'Cairo Trilogy', 1);

-- Categories/Products (category 3 has no products)
INSERT INTO categories VALUES (1, 'Electronics');
INSERT INTO categories VALUES (2, 'Books');
INSERT INTO categories VALUES (3, 'Furniture'); -- no products

INSERT INTO products VALUES (10, 'Laptop', 1);
INSERT INTO products VALUES (11, 'SQL Guide', 2);

-- Dorm Rooms/Assignments (room 3 unassigned)
INSERT INTO dorm_rooms VALUES (1, 'A-101');
INSERT INTO dorm_rooms VALUES (2, 'A-102');
INSERT INTO dorm_rooms VALUES (3, 'B-201'); -- unassigned

INSERT INTO student_dorm_assignments VALUES (1, 1);

COMMIT;

-- =====================================================
-- RIGHT OUTER JOIN QUERIES (Your Tasks)
-- =====================================================

-- 1) All departments + employees (show all departments even if no employees)
SELECT d.department_name,
       e.employee_name
FROM employees e
RIGHT OUTER JOIN departments d
  ON e.department_id = d.department_id;

-- 2) All customers + orders (show all customers even if no orders)
SELECT c.customer_name,
       o.order_id,
       o.order_date
FROM orders o
RIGHT JOIN customers c
  ON o.customer_id = c.customer_id;

-- 3) All courses + enrolled students (show all courses even if no enrollments)
SELECT c.course_title,
       s.student_name
FROM (students s
      JOIN enrollments e
        ON s.student_id = e.student_id)
RIGHT JOIN courses c
  ON e.course_id = c.course_id;

-- 4) All projects + assigned employees (show all projects even if no employees assigned)
SELECT p.project_name,
       e.employee_name
FROM (employees e
      JOIN employee_projects ep
        ON e.employee_id = ep.employee_id)
RIGHT OUTER JOIN projects p
  ON ep.project_id = p.project_id;

-- 5) All payment methods + transactions (show all methods even if no transactions)
SELECT pm.method_name,
       t.transaction_id,
       t.amount
FROM transactions t
RIGHT JOIN payment_methods pm
  ON t.payment_method_id = pm.payment_method_id;

-- 6) All authors + books (show all authors even if no books)
SELECT a.author_name,
       b.book_title
FROM books b
RIGHT OUTER JOIN authors a
  ON b.author_id = a.author_id;

-- 7) All categories + products (show all categories even if no products)
SELECT c.category_name,
       p.product_name
FROM products p
RIGHT JOIN categories c
  ON p.category_id = c.category_id;

-- 8) All dorm rooms + assigned students (show all dorm rooms even if unassigned)
SELECT dr.room_number,
       s.student_name
FROM (students s
      JOIN student_dorm_assignments sda
        ON s.student_id = sda.student_id)
RIGHT OUTER JOIN dorm_rooms dr
  ON sda.dorm_room_id = dr.dorm_room_id;