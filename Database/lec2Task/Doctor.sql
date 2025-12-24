CREATE TABLE Doctor (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    salary INT,
    address VARCHAR(200)
);

INSERT INTO Doctor (id, name, salary, address) VALUES
(1, 'Ahmed Ali', 1000, 'Cairo'),
(2, 'Mohamed Hassan', 2000, 'Giza'),
(3, 'Sara Ibrahim', 3000, 'Alexandria'),
(4, 'Omar Khaled', 4000, 'Tanta'),
(5, 'Laila Mostafa', 5000, 'Mansoura'),
(6, 'Hany Adel', 6000, 'Aswan'),
(7, 'Nour Said', 7000, 'Luxor'),
(8, 'Youssef Amin', 8000, 'Sohag'),
(9, 'Mona Fathy', 9000, 'Minya'),
(10, 'Khaled Nabil', 10000, 'Ismailia');

UPDATE Doctor SET salary = 20000 WHERE id = 3;

DELETE FROM Doctor WHERE id = 9;

SELECT CONCAT(name, ' - ', salary) AS name_salary FROM Doctor;

SELECT id, name, salary * 2 AS doubled_salary, address FROM Doctor;

SELECT * FROM Doctor WHERE salary IN (1000, 2000, 3000);

RENAME TABLE Doctor TO PRD_DOCTOR;

/*oracle */
--ALTER TABLE Doctor RENAME TO PRD_DOCTOR;