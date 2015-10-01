CREATE USER 'das'@'localhost' IDENTIFIED BY 'das!@#';

GRANT ALL PRIVILEGES ON das_olap . * TO 'das'@'localhost';

FLUSH PRIVILEGES;

--
-- Create data base
--
CREATE DATABASE IF NOT EXISTS das_olap CHARACTER SET utf8 COLLATE utf8_general_ci;

--
-- Use database
--
USE das_olap;
