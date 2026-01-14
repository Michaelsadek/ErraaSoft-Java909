-- 1. AVG Function
SELECT AVG(salary) AS avg_salary
FROM EMPLOYEES;

-- 2. COUNT Function
SELECT COUNT(*) AS total_employees
FROM EMPLOYEES;

-- 3. MAX Function
SELECT MAX(salary) AS max_salary
FROM EMPLOYEES;

-- 4. MIN Function
SELECT MIN(salary) AS min_salary
FROM EMPLOYEES;

-- 5. SUM Function
SELECT SUM(salary) AS total_salary
FROM EMPLOYEES;

-- 6. GROUP BY Clause (Part 1)
SELECT department_id,
       AVG(salary) AS avg_salary
FROM EMPLOYEES
GROUP BY department_id;

-- 7. GROUP BY Clause (Part 2)
SELECT job_id,
       COUNT(*) AS total_employees
FROM EMPLOYEES
GROUP BY job_id;

-- 8. HAVING Clause
SELECT department_id,
       SUM(salary) AS total_salary
FROM EMPLOYEES
GROUP BY department_id
HAVING SUM(salary) > 50000;

-- 9. AVG Function (Advanced)
SELECT AVG(COMMISSION_PCT) AS avg_commission
FROM EMPLOYEES
WHERE COMMISSION_PCT IS NOT NULL;

-- 10. COUNT Function (Advanced)
SELECT COUNT(*) AS employees_over_10000
FROM EMPLOYEES
WHERE SALARY > 10000;

-- 11. MAX and MIN Together
SELECT job_id,
       MAX(salary) AS max_salary,
       MIN(salary) AS min_salary
FROM EMPLOYEES
GROUP BY job_id;

-- 12. SUM Function (Advanced)
SELECT manager_id,
       SUM(salary) AS total_salary
FROM EMPLOYEES
GROUP BY manager_id;

-- 13. GROUP BY with Multiple Columns
SELECT department_id,
       job_id,
       SUM(salary) AS total_salary
FROM EMPLOYEES
GROUP BY department_id, job_id;

-- 14. HAVING with COUNT
SELECT job_id,
       COUNT(*) AS num_employees
FROM EMPLOYEES
GROUP BY job_id
HAVING COUNT(*) > 5;

-- 15. Using Aggregate Functions Together
SELECT department_id,
       COUNT(*) AS total_employees,
       AVG(salary) AS avg_salary,
       MAX(salary) AS max_salary,
       MIN(salary) AS min_salary
FROM EMPLOYEES
GROUP BY department_id;

-- 16. Complex HAVING
SELECT department_id,
       AVG(salary) AS avg_salary,
       COUNT(*) AS num_employees
FROM EMPLOYEES
GROUP BY department_id
HAVING AVG(salary) > 8000
   AND COUNT(*) < 10;

-- 17. Nested Aggregation (Department with highest total salary)
SELECT department_id,
       SUM(salary) AS total_salary
FROM EMPLOYEES
GROUP BY department_id
ORDER BY total_salary DESC
FETCH FIRST 1 ROW ONLY;

-- 18. Using Aliases with Aggregates
SELECT department_id,
       SUM(salary) AS Total_Salary,
       AVG(salary) AS Average_Salary
FROM EMPLOYEES
GROUP BY department_id;
