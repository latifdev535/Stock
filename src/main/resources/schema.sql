CREATE TABLE IF NOT EXISTS USERS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(10) UNIQUE NOT NULL,
  password VARCHAR(10) NOT NULL,
  active_status CHAR,
  created_date timestamp default CURRENT_TIMESTAMP()
);

CREATE TABLE IF NOT EXISTS USER_STOCK_DATA (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id INT NOT NULL,
  stock_symbol VARCHAR(5) NOT NULL,
  stock_qty INT NOT NULL,
  created_date timestamp default CURRENT_TIMESTAMP()
);


CREATE TABLE IF NOT EXISTS USER_STOCK_DATA_HIST (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  stock_data_id INT NOT NULL,
  stock_qty INT NOT NULL,
  stock_operation VARCHAR(5) NOT NULL,
  created_date timestamp default CURRENT_TIMESTAMP()
);

INSERT INTO USERS (username, password,active_status) VALUES
  ('user1', 'password','A'),
  ('user2', 'password','A'),
  ('user3', 'password','A');