CREATE SCHEMA `internet-shop` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `internet-shop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `productname` VARCHAR(256) NOT NULL,
  `productprice` DOUBLE NOT NULL,
  PRIMARY KEY (`product_id`));
CREATE TABLE `internet-shop`.`users` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `login` VARCHAR(256) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `salt` VARBINARY(8000) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
CREATE TABLE `internet-shop`.`orders` (
  `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NULL,
  PRIMARY KEY (`order_id`),
  INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `orders_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet-shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
CREATE TABLE `internet-shop`.`orders_products` (
  `order_id` BIGINT(11) NULL,
  `product_id` BIGINT(11) NULL,
  INDEX `orders_products_orders_idx` (`order_id` ASC) VISIBLE,
  INDEX `orders_products_products_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `orders_products_orders_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `internet-shop`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `orders_products_products_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet-shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
CREATE TABLE `internet-shop`.`shopping_carts` (
  `shopping_cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NULL,
  PRIMARY KEY (`shopping_cart_id`),
  INDEX `shopping_carts_users_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `shopping_carts_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet-shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
CREATE TABLE `internet-shop`.`shopping_carts_products` (
  `shopping_cart_id` BIGINT(11) NULL,
  `product_id` BIGINT(11) NULL,
  INDEX `shopping_carts_products_shopping_carts_fk_idx` (`shopping_cart_id` ASC) VISIBLE,
  INDEX `shopping_carts_products_products_fk_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `shopping_carts_products_shopping_carts_fk`
    FOREIGN KEY (`shopping_cart_id`)
    REFERENCES `internet-shop`.`shopping_carts` (`shopping_cart_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `shopping_carts_products_products_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet-shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
CREATE TABLE `internet-shop`.`roles` (
  `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
CREATE TABLE `internet-shop`.`users_roles` (
  `user_id` BIGINT(11) NULL,
  `role_id` BIGINT(11) NULL,
  INDEX `users-roles_users_fk_idx` (`user_id` ASC) VISIBLE,
  INDEX `users_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `users-roles_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet-shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `users_roles_roles_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `internet-shop`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
INSERT INTO `internet-shop`.`roles` (`role_name`) VALUES ('USER');
INSERT INTO `internet-shop`.`roles` (`role_name`) VALUES ('ADMIN');
