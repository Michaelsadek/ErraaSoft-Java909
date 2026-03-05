-- Language table
CREATE TABLE Language (
    id INT PRIMARY KEY,
    name VARCHAR(50)
);

----------------------------------------

-- Teacher table
CREATE TABLE Teacher (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    salary DECIMAL(10,2),
    language_id INT,
    
    FOREIGN KEY (language_id) REFERENCES Language(id)
);