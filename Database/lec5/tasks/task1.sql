-- 1️⃣ Player table
CREATE TABLE Player (
    id INT NOT NULL UNIQUE,
    name VARCHAR(50) UNIQUE,
    age INT
);

--------------------------------------------------

-- 2️⃣ Manager table (id and name unique together)
CREATE TABLE Manager1 (
    id INT NOT NULL,
    name VARCHAR(50),
    salary NUMBER,
    CONSTRAINT manager1_unique UNIQUE (id, name)
);

--------------------------------------------------

-- 3️⃣ Manager table (Primary Key)
CREATE TABLE Manager2 (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    age INT
);