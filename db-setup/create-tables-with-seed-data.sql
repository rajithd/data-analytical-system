--
-- Table structure for table customer
--

CREATE TABLE customer (
  customer_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(500) NOT NULL,
  email VARCHAR(500) NOT NULL,
  phone_number VARCHAR(500),
  PRIMARY KEY (customer_id)
) ENGINE =InnoDB;

--
-- Table structure for table address
--

CREATE TABLE address (
  address_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  street VARCHAR(500) NOT NULL,
  city VARCHAR(500) NOT NULL,
  zip_code VARCHAR(500),
  state VARCHAR(500),
  delivery TINYINT(1),
  resident TINYINT(1),
  customer_id INT UNSIGNED,
  PRIMARY KEY (address_id),
  CONSTRAINT fk_ADDRESS_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
) ENGINE =InnoDB;

--
-- Table structure for table product_category
--

CREATE TABLE product_category (
  product_category_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(500) NOT NULL,
  PRIMARY KEY (product_category_id)
) ENGINE =InnoDB;

--
-- Table structure for table search_criteria
--

CREATE TABLE search_criteria (
  search_criteria_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id INT UNSIGNED ,
  search_string VARCHAR(500) ,
  search_date DATETIME,
  product_category_id INT UNSIGNED,
  PRIMARY KEY (search_criteria_id),
  CONSTRAINT fk_SEARCH_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
  CONSTRAINT fk_SEARCH_PRODUCT FOREIGN KEY (product_category_id) REFERENCES product_category (product_category_id)
) ENGINE =InnoDB;


--
-- Table structure for table brand
--

CREATE TABLE brand (
  brand_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(500) NOT NULL,
  PRIMARY KEY (brand_id)
) ENGINE =InnoDB;

--
-- Table structure for table product
--

CREATE TABLE product (
  product_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(500) NOT NULL,
  price DECIMAL(30,30),
  description text,
  brand_new TINYINT(1),
  product_category_id INT UNSIGNED,
  brand_id INT UNSIGNED,
  PRIMARY KEY (product_id),
  CONSTRAINT fk_PRODUCT_CATEGORY FOREIGN KEY (product_category_id) REFERENCES product_category (product_category_id),
  CONSTRAINT fk_PRODUCT_BRAND FOREIGN KEY (brand_id) REFERENCES brand (brand_id)
) ENGINE =InnoDB;

--
-- Table structure for table visited_product
--

CREATE TABLE visited_product (
  visited_product_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id INT UNSIGNED,
  product_id INT UNSIGNED,
  visited_date DATETIME,
  PRIMARY KEY (visited_product_id),
  CONSTRAINT fk_VISITED_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
  CONSTRAINT fk_VISITED_PRODUCT FOREIGN KEY (product_id) REFERENCES product (product_id)
) ENGINE =InnoDB;


--
-- Table structure for table shopping_cart
--

CREATE TABLE shopping_cart (
  shopping_cart_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id INT UNSIGNED,
  product_id INT UNSIGNED,
  PRIMARY KEY (shopping_cart_id),
  CONSTRAINT fk_CART_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
  CONSTRAINT fk_CART_PRODUCT FOREIGN KEY (product_id) REFERENCES product (product_id)
) ENGINE =InnoDB;

--
-- Table structure for table transaction
--

CREATE TABLE transaction (
  transaction_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id INT UNSIGNED,
  product_id INT UNSIGNED,
  purchase_date DATETIME,
  delivery_address_id INT UNSIGNED,
  PRIMARY KEY (transaction_id),
  CONSTRAINT fk_TRANSACTION_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
  CONSTRAINT fk_TRANSACTION_PRODUCT FOREIGN KEY (product_id) REFERENCES product (product_id),
  CONSTRAINT fk_TRANSACTION_ADDRESS FOREIGN KEY (delivery_address_id) REFERENCES address (address_id)
) ENGINE =InnoDB;

--
-- Seed data
--

-- customer data
insert into customer values(1,'Rajith','rajithgama@gmail.com','+94779545015');
insert into customer values(2,'Scott','scott@abc.com','006564356');
insert into customer values(3,'Bryan','bryan@aa.com','006564356');
insert into customer values(4,'Julien','julien@aab.com','006564356');
insert into customer values(5,'Rnard','rabc@aabc.com','006564356');

-- address data
insert into address values(1,'109 road','Colombo','11850','Western',true,true,1);
insert into address values(2,'123 lane','Galle','16762','South',true,false,2);
insert into address values(3,'898 lane','Colombo','67543','Western',true,true,3);
insert into address values(4,'777 lane','Gampaha','78456','Western',true,true,4);
insert into address values(5,'886 lane','Colombo','11850','Western',true,true,5);

-- product category
insert into product_category values(1,'Book');
insert into product_category values(2,'Computer');
insert into product_category values(3,'Software');
insert into product_category values(4,'Movies & TV');
insert into product_category values(5,'Electronics');
insert into product_category values(6,'Office Products');
insert into product_category values(7,'Pet Suppliers');
insert into product_category values(8,'Home & Kitchen');
insert into product_category values(9,'Magazines');
insert into product_category values(10,'Sports');

-- search criteria
insert into search_criteria values(1, 1, 'Scala','2015-09-01 12:12',1);
insert into search_criteria values(2, 1, 'Java','2015-09-03 10:12',1);
insert into search_criteria values(3, 1, 'Hadoop','2015-09-05 09:12',1);
insert into search_criteria values(4, 1, 'Mac','2015-09-10 08:12',2);
insert into search_criteria values(5, 1, 'Mac air','2015-09-11 07:12',2);
insert into search_criteria values(6, 1, 'Mac pro','2015-09-12 09:12',2);
insert into search_criteria values(7, 1, 'Kindle','2015-09-23 06:12',5);
insert into search_criteria values(8, 1, 'Hive','2015-09-25 07:12',1);
insert into search_criteria values(9, 1, 'Spark','2015-09-27 11:12',1);
insert into search_criteria values(10, 2, 'C#','2015-09-01 12:12',1);
insert into search_criteria values(11, 3, 'DSLR','2015-09-01 12:12',5);
insert into search_criteria values(12, 4, 'Home:A Novel','2015-09-01 12:12',1);
insert into search_criteria values(13, 2, 'LG TV','2015-09-01 12:12',5);
insert into search_criteria values(14, 2, 'The Story of One Super Boov','2015-09-01 12:12',1);
insert into search_criteria values(15, 3, 'Home Theater','2015-09-01 12:12',5);
insert into search_criteria values(16, 3, 'New Era Management','2015-09-01 12:12',1);

-- brand
insert into brand values(1,'Apple');
insert into brand values(2,'Samsung');
insert into brand values(3,'LG');
insert into brand values(4,'Nokia');
insert into brand values(5,'Book');

-- product
insert into product values(1,'Mac book Air',1200,'Mac',true,2,1);
insert into product values(2,'Effective Java',45,'Java Book',true,1,5);
insert into product values(3,'Effective Java 2nd edition',50,'Java Book',true,1,5);
insert into product values(4,'Head first java',60,'Java Book',true,1,5);
insert into product values(5,'Apache Spark',90,'Big data',true,1,5);
insert into product values(6,'Scala : language',89,'Scala Book',true,1,5);
insert into product values(7,'Akka in action',90,'Scala Book',true,1,5);
insert into product values(8,'Effective Akka',95,'Scala Book',true,1,5);
insert into product values(9,'Samsung Laptop',800,'Labtop',true,2,2);
insert into product values(10,'Nokia phone',200,'Phone',false,5,5);

-- visited product
insert into visited_product values(1,1,2,'2015-09-30 10:20');
insert into visited_product values(2,1,3,'2015-09-30 10:20');
insert into visited_product values(3,1,4,'2015-09-30 10:20');
insert into visited_product values(4,1,5,'2015-09-30 10:20');
insert into visited_product values(5,1,6,'2015-09-30 10:20');
insert into visited_product values(6,1,1,'2015-09-30 10:20');
insert into visited_product values(7,1,1,'2015-09-30 10:20');
insert into visited_product values(8,1,1,'2015-09-30 10:20');
insert into visited_product values(9,1,2,'2015-09-30 10:20');
insert into visited_product values(10,1,3,'2015-09-30 10:20');
insert into visited_product values(11,1,3,'2015-09-30 10:20');
insert into visited_product values(12,1,3,'2015-09-30 10:20');
insert into visited_product values(13,2,9,'2015-09-30 10:20');
insert into visited_product values(14,2,10,'2015-09-30 10:20');

-- transaction
insert into transaction values(1,1,2,'2015-09-30 11:20',1);
insert into transaction values(2,1,3,'2015-09-30 11:20',1);
insert into transaction values(3,1,4,'2015-09-30 11:20',1);
insert into transaction values(4,1,5,'2015-09-30 11:20',1);
insert into transaction values(5,1,6,'2015-09-30 11:20',1);
insert into transaction values(6,1,2,'2015-09-30 11:20',1);
insert into transaction values(7,2,1,'2015-09-30 11:20',2);