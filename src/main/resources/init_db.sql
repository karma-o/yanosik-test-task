CREATE SCHEMA IF NOT EXISTS `yanosik` DEFAULT CHARACTER SET utf8;
USE `yanosik`;
-- every entity got a field is_deleted as part of the soft delete mechanism
-- to make our data more consistent and easy to manage
-- we will use this field to mark the entity as deleted and not to delete it from the database
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                         `nick` VARCHAR(255) NOT NULL,
                         `login` VARCHAR(255) NOT NULL,
                         `password` VARCHAR(255) NOT NULL,
                         `insert_time` DATETIME NOT NULL DEFAULT NOW(),
                         `is_deleted` TINYINT NOT NULL DEFAULT 0,
                         PRIMARY KEY (`id`),
                         UNIQUE INDEX `id_UNIQUE` (id ASC) VISIBLE
);
-- This table was changed as part of the question about
-- what we could have changed in the structure of our database
DROP TABLE IF EXISTS `vehicles`;
CREATE TABLE `vehicles` (
                        `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                         -- this column must be an ID of a user instead of a login,
                         -- because login cant guarantee a uniqueness, so user_login cant reference user.login.
                         -- also using an ID of the entity is the best practice anyways.
                        `user_id` BIGINT(11) NOT NULL,
                        `brand` VARCHAR(255) NOT NULL,
                        `model` VARCHAR(255) NOT NULL,
                        `insert_time` DATETIME NOT NULL DEFAULT NOW(),
                        `is_deleted` tinyint NOT NULL DEFAULT 0,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `vehicles_users_fk`
                            FOREIGN KEY (user_id)
                                REFERENCES `yanosik`.`users` (`id`)
                                ON UPDATE NO ACTION
                                ON DELETE NO ACTION
);

DROP TABLE  IF EXISTS `insurance_offers`;
CREATE TABLE `insurance_offers` (
                                  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                  `vehicle_id` BIGINT(11) NOT NULL,
                                  `insurer` VARCHAR(255) NOT NULL,
                                  `price` DECIMAL(10,2) NOT NULL,
                                  `insert_time` DATETIME NOT NULL DEFAULT NOW(),
                                  `is_deleted` tinyint NOT NULL DEFAULT 0,
                                  PRIMARY KEY (`id`),
                                  CONSTRAINT `insurance_offers_vehicles_fk`
                                      FOREIGN KEY (vehicle_id)
                                          REFERENCES `yanosik`.`vehicles` (`id`)
                                          ON UPDATE NO ACTION
                                          ON DELETE NO ACTION
);
