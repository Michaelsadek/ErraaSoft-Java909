CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    manager_id INT,
    hire_date DATE,
    department_id INT,
    salary DECIMAL(10,2),
    job_id VARCHAR(20),
    email VARCHAR(100)
);


INSERT INTO employees (emp_id, first_name, last_name, manager_id, hire_date, department_id, salary, job_id, email) VALUES
(100, 'John', 'Doe', NULL, '2020-01-15', 10, 5000.00, 'AD_VP', 'john.doe@email.com'),
(101, 'Jane', 'Smith', 100, '2020-03-20', 20, 4500.00, 'IT_PROG', 'jane.smith@email.com'),
(102, 'Peter', 'Johnson', 100, '2021-02-10', 10, 5500.00, 'AD_VP', 'peter.johnson@email.com'),
(103, 'Patricia', 'Williams', 102, '2021-05-15', 30, 4000.00, 'HR_REP', 'patricia.williams@email.com'),
(104, 'Michael', 'Brown', 100, '2022-01-30', 20, 4800.00, 'IT_PROG', 'michael.brown@email.com'),
(105, 'Linda', 'Davis', 102, '2022-03-25', 10, 5200.00, 'AD_VP', 'linda.davis@email.com'),
(151, 'Sarah', 'Miller', NULL, '2023-01-10', 40, 6000.00, 'MK_MGR', 'sarah.miller@email.com'),
(152, 'Robert', 'Wilson', 151, '2023-02-15', 40, 4700.00, 'MK_REP', 'robert.wilson@email.com'),
(153, 'Jennifer', 'Moore', 151, '2023-03-20', 40, 4600.00, 'MK_REP', 'jennifer.moore@email.com'),
(154, 'David', 'Taylor', 100, '2023-04-25', 20, 4900.00, 'IT_PROG', 'david.taylor@email.com'),
(155, 'Susan', 'Anderson', NULL, '2023-05-30', 50, 5800.00, 'FI_MGR', 'susan.anderson@email.com'),
(156, 'Pamela', 'Thomas', 155, '2023-06-05', 50, 4200.00, 'FI_ACC', 'pamela.thomas@email.com'),
(157, 'Paul', 'Jackson', 100, '2023-07-10', 10, 5300.00, 'AD_VP', 'paul.jackson@email.com'),
(158, 'Amanda', 'White', 151, '2023-08-15', 40, 4400.00, 'MK_REP', 'amanda.white@email.com'),
(159, 'Patricia', 'Harris', 155, '2023-09-20', 50, 4100.00, 'FI_ACC', 'patricia.harris@email.com'),
(160, 'PETER', 'Martin', 102, '2023-10-25', 30, 4300.00, 'HR_REP', 'peter.martin@email.com'),
(161, 'Robert', 'Smith', 100, '2023-11-30', 20, 4750.00, 'IT_PROG', 'robert.smith@email.com'),
(162, 'Jessica', 'Lee', NULL, '2023-12-05', 60, 6100.00, 'PR_MGR', 'jessica.lee@email.com'),
(163, 'Alexandra', 'Walker', 162, '2024-01-10', 60, 3950.00, 'PR_REP', 'alexandra.walker@email.com'),
(164, 'Pat', 'King', 100, '2024-02-15', 10, 5400.00, 'AD_VP', 'pat.king@email.com'),
(165, 'Alice', 'Green', 151, '2024-03-20', 40, 4550.00, 'MK_REP', 'alice.green@email.com'),
(166, 'Patrick', 'Scott', 162, '2024-04-25', 60, 3850.00, 'PR_REP', 'patrick.scott@email.com'),
(167, 'Ann', 'Adams', 155, '2024-05-30', 50, 4050.00, 'FI_ACC', 'ann.adams@email.com'),
(168, 'Angela', 'Baker', 100, '2024-06-05', 20, 4850.00, 'IT_PROG', 'angela.baker@email.com'),
(169, 'Stephanie', 'Gonzalez', NULL, '2024-07-10', 70, 5950.00, 'SA_MGR', 'stephanie.gonzalez@email.com'),
(170, 'peterson', 'Nelson', 169, '2024-08-15', 70, 4250.00, 'SA_REP', 'peterson.nelson@email.com');
-- 1  Find all employee where the emp_id is within a certain range 100 and 105
SELECT * FROM employees WHERE emp_id BETWEEN 100 AND 105;

-- 2 Find all employee that belong to a specific set of emp_id 151, 152, 153, 154, 155
SELECT * FROM employees WHERE emp_id IN (151, 152, 153, 154, 155);

--3 Retrieve all employee where the employee first_name starts with the letter 'P' OR 'p'
SELECT * FROM employees WHERE first_name ILIKE 'P%';

-- 4 Retrieve all employee where the employee first_name end with the letter 'A' OR 'a'
SELECT * FROM employees WHERE first_name ILIKE '%A';

--5 Retrieve all employee where the employee first_name with the letter 'A' OR 'a'
SELECT * FROM employees WHERE first_name ILIKE '%A%';

-- 6. Retrieve all employee where the employee first_name third char with the letter 'e' OR 'E'
SELECT * FROM employees WHERE SUBSTRING(first_name, 3, 1) ILIKE 'E';

--7 Retrieve all employees who don't have a manager assigned (i.e., manager_id is NULL)
SELECT * FROM employees WHERE manager_id IS NULL;

--8 Find all employees who have a manager assigned
SELECT * FROM employees WHERE manager_id IS NOT NULL;

--9 Insert a new employee without assigning a manager (NULL value for manager_id)
INSERT INTO employees (emp_id, first_name, last_name, manager_id, hire_date, department_id, salary, job_id) 
VALUES (200, 'New', 'Employee', NULL, '2024-12-01', 10, 5000.00, 'IT_PROG');

--10 Find all employees who work either in the 'AD_VP' JOB_ID or the 'IT_PROG' JOB_ID
SELECT * FROM employees WHERE job_id IN ('AD_VP', 'IT_PROG');

--11 all employees sorted by their last_name in ascending order
SELECT * FROM employees ORDER BY last_name ASC;

--12 all employees sorted by their hire_date in descending order
SELECT * FROM employees ORDER BY hire_date DESC;

-- 13. Sort employees first by department in ascending order and then by salary in descending order within each department_id
SELECT * FROM employees ORDER BY department_id ASC, salary DESC;

--14 all employees with their last_name in lowercase
SELECT emp_id, first_name, LOWER(last_name) AS last_name_lower FROM employees;

--15 all employees with their first_name in uppercase
SELECT emp_id, UPPER(first_name) AS first_name_upper, last_name FROM employees;

--16 all employees with their first_name and last_name in title case (first letter capitalized)
SELECT 
    emp_id,
    CONCAT(UPPER(LEFT(first_name, 1)), LOWER(SUBSTRING(first_name, 2))) AS first_name_title,
    CONCAT(UPPER(LEFT(last_name, 1)), LOWER(SUBSTRING(last_name, 2))) AS last_name_title
FROM employees;

--17 Find employees whose last_name is 'smith', regardless of the case
SELECT * FROM employees WHERE UPPER(last_name) = 'SMITH';


