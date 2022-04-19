DROP DATABASE IF EXISTS `yanosik`;
CREATE SCHEMA `yanosik` DEFAULT CHARACTER SET utf8;
USE `yanosik`;

-- every entity got a field is_deleted as part of the soft delete mechanism
-- to make our data more consistent and easy to manage
-- we will use this field to mark the entity as deleted and not to delete it from the database

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

-- Mock data for testing purposes because we dont have a registration system
INSERT INTO `yanosik`.`users` (`nick`, `login`, `password`) VALUES ('Vadym', 'vadym1234', 'bestProgrammer');

INSERT INTO `yanosik`.`vehicles` (`user_id`, `brand`, `model`) VALUES ('1', 'Tesla', 'Model S');
INSERT INTO `yanosik`.`vehicles` (`user_id`, `brand`, `model`) VALUES ('1', 'Tesla', 'Model E');
INSERT INTO `yanosik`.`vehicles` (`user_id`, `brand`, `model`) VALUES ('1', 'Tesla', 'Model X');

INSERT INTO `yanosik`.`insurance_offers` (`vehicle_id`, `insurer`, `price`) VALUES ('1', 'PZU', '999.99');
INSERT INTO `yanosik`.`insurance_offers` (`vehicle_id`, `insurer`, `price`) VALUES ('2', 'PZU', '1499.99');
INSERT INTO `yanosik`.`insurance_offers` (`vehicle_id`, `insurer`, `price`) VALUES ('2', 'Allianz', '2000.00');
