CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  first_name VARCHAR(45) NOT NULL,
  surname VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  PRIMARY KEY (id));

CREATE TABLE orders(
  dispatcherCode VARCHAR(45) NOT NULL,
  orderNumber VARCHAR(45) NOT NULL,
  nameOfItem VARCHAR(45) NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(21,2) NOT NULL,
  isActive TINYINT NOT NULL,
  PRIMARY KEY (dispatcherCode));