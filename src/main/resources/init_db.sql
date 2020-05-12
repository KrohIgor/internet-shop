CREATE SCHEMA `internet-shop` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `internet-shop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `productname` VARCHAR(225) NOT NULL,
  `productprice` DOUBLE NOT NULL,
  PRIMARY KEY (`product_id`));
