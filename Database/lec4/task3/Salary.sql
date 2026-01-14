--Q1
SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY') AS current_date
FROM dual;

--Q2
SELECT TO_CHAR(SYSDATE, 'Month YYYY') AS current_date
FROM dual;
--example
SELECT TRIM(TO_CHAR(SYSDATE, 'Month YYYY')) AS current_date
FROM dual;


--Q3
SELECT TO_CHAR(12345.67, '99,999.99') AS formatted_number
FROM dual;

--Q4
SELECT TO_CHAR(8500, '$99,999.00') AS formatted_salary
FROM dual;

SELECT TO_CHAR(8500, 'L99,999.00') AS formatted_salary
FROM dual;
