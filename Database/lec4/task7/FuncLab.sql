CREATE TABLE customers (
    full_name VARCHAR2(50)
);

INSERT INTO customers VALUES ('  Ahmed Ali');
INSERT INTO customers VALUES ('Mona Hassan  ');
INSERT INTO customers VALUES ('  Sara Omar  ');
INSERT INTO customers VALUES ('Youssef');

COMMIT;

SELECT TRIM(full_name) AS trimmed_name
FROM customers;

SELECT LTRIM(full_name) AS no_leading_space
FROM customers;

SELECT RTRIM(full_name) AS no_trailing_space
FROM customers;

SELECT TRIM('$#*' FROM '$#*Oracle SQL*#$') AS trimmed_value
FROM dual;

SELECT REPLACE('promotion', 'o', '0') AS replaced_text
FROM dual;

SELECT REPLACE('This is a basic course', 'basic', 'advanced') AS replaced_sentence
FROM dual;

CREATE TABLE departments (
    dept_name VARCHAR2(30)
);

INSERT INTO departments VALUES ('HR');
INSERT INTO departments VALUES ('IT');
INSERT INTO departments VALUES ('Sales');

COMMIT;


SELECT dept_name,
       LPAD(dept_name, 15, '*') AS lpad_name
FROM departments;

SELECT dept_name,
       RPAD(dept_name, 15, '-') AS rpad_name
FROM departments;

SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY') AS formatted_date
FROM dual;


SELECT TRIM(TO_CHAR(SYSDATE, 'Day, Month YYYY')) AS formatted_date
FROM dual;

SELECT TO_CHAR(1234567.89, '9,999,999.99') AS formatted_number
FROM dual;

SELECT TO_CHAR(8500, '$99,999.00') AS formatted_salary
FROM dual;

SELECT TO_CHAR(SYSDATE, 'YYYY/MM/DD HH24:MI:SS') AS full_datetime
FROM dual;

CREATE TABLE students (
    name  VARCHAR2(50),
    score NUMBER(3)
);

INSERT INTO students VALUES ('Ahmed', 95);
INSERT INTO students VALUES ('Mona', 82);
INSERT INTO students VALUES ('Sara', 74);
INSERT INTO students VALUES ('Omar', 66);
INSERT INTO students VALUES ('Yara', 55);

COMMIT;

SELECT name,
       score,
       CASE
           WHEN score >= 90 THEN 'A'
           WHEN score BETWEEN 80 AND 89 THEN 'B'
           WHEN score BETWEEN 70 AND 79 THEN 'C'
           ELSE 'F'
       END AS grade
FROM students;

SELECT name,
       score,
       CASE
           WHEN score >= 60 THEN 'Pass'
           ELSE 'Fail'
       END AS result
FROM students;

SELECT name,
       score,
       CASE
           WHEN score >= 90 THEN 'Excellent'
           WHEN score BETWEEN 80 AND 89 THEN 'Good'
           WHEN score BETWEEN 70 AND 79 THEN 'Average'
           ELSE 'Needs Improvement'
       END AS message
FROM students;

SELECT 'Today is ' || TRIM(TO_CHAR(SYSDATE, 'Day')) AS today_message
FROM dual;

SELECT score,
       DECODE(score,
              100, 'A',
               90, 'B',
               80, 'C',
               'F') AS grade
FROM students;

CREATE TABLE status_log (
    status_code CHAR(1)
);

INSERT INTO status_log VALUES ('N');
INSERT INTO status_log VALUES ('I');
INSERT INTO status_log VALUES ('C');

COMMIT;

SELECT status_code,
       DECODE(status_code,
              'N', 'New',
              'I', 'In Progress',
              'C', 'Completed',
              'Unknown') AS status_description
FROM status_log;


SELECT DECODE(quantity, 0, 'Out of Stock', 'Available') AS stock_status
FROM (SELECT 0 AS quantity FROM dual);

SELECT dept_name,
       DECODE(dept_name,
              'HR', 500,
              'IT', 1000,
              'Sales', 1500,
              300) AS bonus
FROM departments;

