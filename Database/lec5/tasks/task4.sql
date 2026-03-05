-- Employee table
CREATE TABLE Employee (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    age INT
);

--------------------------------------------------

-- Phone table
CREATE TABLE Phone (
    id INT PRIMARY KEY,
    phoneNumber VARCHAR(20),
    employee_id INT UNIQUE,
    
    FOREIGN KEY (employee_id) REFERENCES Employee(id)
);