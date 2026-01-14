--Q1
SELECT REPLACE('database', 'a', '@') AS replaced_text;

--Q2
SELECT REPLACE('This is old text', 'old', 'new') AS replaced_text;

--Q3
CREATE TABLE PRODUCT (
    product_id   SERIAL PRIMARY KEY,
    product_name VARCHAR(50)
);

INSERT INTO PRODUCT (product_name) VALUES
('Apple'),
('Banana'),
('Cherry');

--Q4
SELECT product_name,
       LPAD(product_name, 15, '*') AS lpad_name
FROM PRODUCT;

--Q5
SELECT product_name,
       RPAD(product_name, 15, '#') AS rpad_name
FROM PRODUCT;
