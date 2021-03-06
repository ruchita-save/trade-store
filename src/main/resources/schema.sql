DROP TABLE IF EXISTS TRADE_STORE;

CREATE TABLE TRADE_STORE (
id INT AUTO_INCREMENT PRIMARY KEY,
trade_id VARCHAR(10) NOT NULL,
version INT NOT NULL,
cnt_prty_id VARCHAR(10) NOT NULL,
book_id VARCHAR(10) NOT NULL,
maturity_date DATE NOT NULL,
created_date DATE NOT NULL,
expired CHARACTER NOT NULL
);