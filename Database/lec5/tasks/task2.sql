-- Doctor table
CREATE TABLE Doctor (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    salary DECIMAL(10,2)
);

--------------------------------------------------

-- Patient table
CREATE TABLE Patient (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    age INT
);

--------------------------------------------------

-- Junction table for Many-to-Many relation
CREATE TABLE Doctor_Patient (
    doctor_id INT,
    patient_id INT,
    
    PRIMARY KEY (doctor_id, patient_id),
    
    FOREIGN KEY (doctor_id) REFERENCES Doctor(id),
    FOREIGN KEY (patient_id) REFERENCES Patient(id)
);