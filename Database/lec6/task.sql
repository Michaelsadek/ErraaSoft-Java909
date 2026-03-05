/* =========================================================
   1) CHECK CONSTRAINTS (CREATE / ADD)
   ========================================================= */

-- 1.1 Employees: age >= 18
CREATE TABLE Employees (
  emp_id   NUMBER       NOT NULL,
  emp_name VARCHAR2(100) NOT NULL,
  age      NUMBER       NOT NULL,
  CONSTRAINT pk_employees PRIMARY KEY (emp_id),
  CONSTRAINT chk_emp_age CHECK (age >= 18)
);

-- 1.2 Staff: salary between 3000 and 10000
CREATE TABLE Staff (
  staff_id NUMBER NOT NULL,
  name     VARCHAR2(100),
  salary   NUMBER NOT NULL,
  CONSTRAINT pk_staff PRIMARY KEY (staff_id),
  CONSTRAINT chk_staff_salary CHECK (salary BETWEEN 3000 AND 10000)
);

-- 1.3 Add CHECK to existing Products: price > 0
ALTER TABLE Products
ADD CONSTRAINT chk_products_price CHECK (price > 0);

-- 1.4 Students: grade only A-F
CREATE TABLE Students (
  student_id NUMBER NOT NULL,
  name       VARCHAR2(100),
  grade      CHAR(1),
  CONSTRAINT pk_students PRIMARY KEY (student_id),
  CONSTRAINT chk_students_grade CHECK (grade IN ('A','B','C','D','E','F'))
);


/* =========================================================
   2) ADDING CONSTRAINTS VIA ALTER TABLE
   ========================================================= */

-- 2.1 Add NOT NULL to Customers.email
ALTER TABLE Customers
MODIFY (email NOT NULL);

-- 2.2 Add UNIQUE to Users.username
ALTER TABLE Users
ADD CONSTRAINT uk_users_username UNIQUE (username);

-- 2.3 Add FK Orders.customer_id -> Customers(id)
ALTER TABLE Orders
ADD CONSTRAINT fk_order_customer
FOREIGN KEY (customer_id) REFERENCES Customers(id);

-- 2.4 Add CHECK Accounts.balance >= 0
ALTER TABLE Accounts
ADD CONSTRAINT chk_accounts_balance CHECK (balance >= 0);

-- 2.5 Add PRIMARY KEY to Departments(dept_id)
ALTER TABLE Departments
ADD CONSTRAINT pk_departments PRIMARY KEY (dept_id);


/* =========================================================
   3) DROPPING (REMOVING) CONSTRAINTS
   ========================================================= */

-- 3.1 Drop CHECK constraint chk_salary from Employees
ALTER TABLE Employees
DROP CONSTRAINT chk_salary;

-- 3.2 Remove UNIQUE constraint on email from Users
ALTER TABLE Users
DROP CONSTRAINT uk_users_email;

-- 3.3 Drop PRIMARY KEY from Products
ALTER TABLE Products
DROP PRIMARY KEY;

-- 3.4 Drop FOREIGN KEY constraint fk_order_customer from Orders
ALTER TABLE Orders
DROP CONSTRAINT fk_order_customer;

-- 3.5 Remove NOT NULL constraint from Contacts.phone
ALTER TABLE Contacts
MODIFY (phone NULL);


/* =========================================================
   4) RENAMING CONSTRAINTS
   ========================================================= */

-- 4.1 Rename CHECK chk_age -> check_min_age on Students
ALTER TABLE Students
RENAME CONSTRAINT chk_age TO check_min_age;

-- 4.2 Rename FK fk_emp_dept -> fk_employee_department on Employees
ALTER TABLE Employees
RENAME CONSTRAINT fk_emp_dept TO fk_employee_department;

-- 4.3 Rename PRIMARY KEY constraint on Users to pk_users_id
-- IMPORTANT: Oracle requires the CURRENT constraint name.
-- Example: if current PK name is PK_USERS, rename it like this:
ALTER TABLE Users
RENAME CONSTRAINT pk_users TO pk_users_id;

-- 4.4 Rename UNIQUE constraint on username to uk_user_name
ALTER TABLE Users
RENAME CONSTRAINT uk_users_username TO uk_user_name;


/* =========================================================
   5) DISABLING CONSTRAINTS
   ========================================================= */

-- 5.1 Disable FK fk_customer_order on Orders
ALTER TABLE Orders
DISABLE CONSTRAINT fk_customer_order;

-- 5.2 Temporarily disable (examples) constraints on Products
-- Oracle doesn't have "DISABLE ALL" in one statement; disable by name:
ALTER TABLE Products DISABLE CONSTRAINT chk_products_price;
-- If Products has FK/PK/UNIQUE constraints, disable them similarly by name.

-- 5.3 Disable CHECK constraint on Accounts.balance
ALTER TABLE Accounts
DISABLE CONSTRAINT chk_accounts_balance;

-- 5.4 Disable PRIMARY KEY constraint on Departments(dept_id)
ALTER TABLE Departments
DISABLE CONSTRAINT pk_departments;

-- 5.5 Bulk insert approach (Oracle idea):
-- Disable needed constraints, load data, then enable constraints again.
-- (repeat DISABLE/ENABLE for each constraint name)


/* =========================================================
   6) ENABLING CONSTRAINTS
   ========================================================= */

-- 6.1 Enable FK fk_customer_order on Orders
ALTER TABLE Orders
ENABLE CONSTRAINT fk_customer_order;

-- 6.2 Re-enable constraints on Products after load
ALTER TABLE Products
ENABLE CONSTRAINT chk_products_price;
-- Enable other constraints by name as needed.

-- 6.3 Enable CHECK constraint on salary in Staff
ALTER TABLE Staff
ENABLE CONSTRAINT chk_staff_salary;

-- 6.4 Enable PRIMARY KEY on Departments(dept_id)
ALTER TABLE Departments
ENABLE CONSTRAINT pk_departments;

-- 6.5 Enable a constraint only if it's currently disabled (Oracle pattern)
-- Query first, then run ENABLE if status = 'DISABLED'
-- (run the SELECT, then run the ALTER TABLE)
SELECT constraint_name, status
FROM user_constraints
WHERE table_name = 'ORDERS'
  AND constraint_name = 'FK_CUSTOMER_ORDER';

-- If STATUS = 'DISABLED', then:
ALTER TABLE Orders
ENABLE CONSTRAINT fk_customer_order;