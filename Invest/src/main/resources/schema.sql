DROP TABLE IF EXISTS product;
CREATE TABLE product(
    product_id INT PRIMARY KEY, 
    title VARCHAR(100),
    total_amount INT,
    started_at	DATE,
    finished_at	DATE
);

DROP TABLE IF EXISTS invest;
CREATE TABLE invest(
    invest_id INT PRIMARY KEY, 
    user_id INT,
    product_id INT,
    amount INT,
    reg_dtm	DATE
);

DROP SEQUENCE IF EXISTS INVEST_SEQ;
CREATE SEQUENCE INVEST_SEQ START WITH 2 INCREMENT BY 1;
