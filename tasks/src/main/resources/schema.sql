CREATE TABLE IF NOT EXISTS `task` (
    `id` BINARY(16) NOT NULL,
    `description` VARCHAR(500) NOT NULL,
    `email` VARCHAR(254) NOT NULL,
    `status` VARCHAR(20) NOT NULL,
    `attempts` INT UNSIGNED NOT NULL DEFAULT 0,
    `created_at` DATETIME(6) NOT NULL,
    `updated_at` DATETIME(6) NULL,
    `execute_at` DATETIME(6) NOT NULL,
    `last_error` LONGTEXT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_task_status_execute` (`status`, `execute_at`)
) ENGINE=InnoDB;