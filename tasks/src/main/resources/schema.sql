-- CREATE TABLE IF NOT EXISTS `cards` (
--   `card_id` int NOT NULL AUTO_INCREMENT,
--   `mobile_number` varchar(15) NOT NULL,
--   `card_number` varchar(100) NOT NULL,
--   `card_type` varchar(100) NOT NULL,
--   `total_limit` int NOT NULL,
--   `amount_used` int NOT NULL,
--   `available_amount` int NOT NULL,
--   `created_at` date NOT NULL,
--   `created_by` varchar(20) NOT NULL,
--   `updated_at` date DEFAULT NULL,
--   `updated_by` varchar(20) DEFAULT NULL,
--   PRIMARY KEY (`card_id`)
-- );

CREATE TABLE IF NOT EXISTS `task` (
    `id` BINARY(16) NOT NULL,
    `description` VARCHAR(500) NOT NULL,
    `email` VARCHAR(254) NOT NULL,
    `status` VARCHAR(20) NOT NULL,
    `attempts` INT NOT NULL DEFAULT 0,
    `created_at` DATETIME(6) NOT NULL,
    `updated_at` DATETIME(6) NULL,
    `execute_at` DATETIME(6) NULL,
    `last_error` LONGTEXT NULL,
    PRIMARY KEY (`id`)
);