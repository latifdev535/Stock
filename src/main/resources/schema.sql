CREATE TABLE IF NOT EXISTS USERS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) UNIQUE NOT NULL,
  password VARCHAR(1000) NOT NULL,
  created_date timestamp default CURRENT_TIMESTAMP()
);

CREATE TABLE IF NOT EXISTS USER_STOCK_DATA (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id VARCHAR(250) NOT NULL,
  stock_symbol VARCHAR(500) NOT NULL,
  stock_qty VARCHAR(500) NOT NULL,
  invested_amt VARCHAR(500) NOT NULL,
  created_date timestamp default CURRENT_TIMESTAMP()
);


INSERT INTO USERS (username, password) VALUES
  ('latif', 'U2FsdGVkX18tAQo4z8uGDk3oyJUwSNzHZR66ucCtqng='),
  ('khan', 'U2FsdGVkX18tAQo4z8uGDk3oyJUwSNzHZR66ucCtqng='),
  ('user', 'U2FsdGVkX18tAQo4z8uGDk3oyJUwSNzHZR66ucCtqng=');