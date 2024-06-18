DROP DATABASE IF EXISTS `balance`;
CREATE DATABASE `balance`;

CREATE TABLE `balance`.`positions` (
    `position_id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `rate` int unsigned NOT NULL,
    PRIMARY KEY (position_id),
    UNIQUE KEY (`name`),
    CONSTRAINT `check_not_empty_string` CHECK ((not(regexp_like(`name`,_utf8mb4'^s*$'))))
);

CREATE TABLE `balance`.`tasks` (
    id int unsigned NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (title),
    CONSTRAINT `check_not_empty_string_tasks_title` CHECK ((not(regexp_like(`title`,_utf8mb4'^s*$'))))

);

CREATE TABLE `balance`.`employees` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `position_id` int unsigned NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`name`),
    KEY `position_id` (`position_id`),
    FOREIGN KEY (`position_id`)
    REFERENCES `balance`.`positions`(`position_id`)

);

CREATE TABLE `balance`.`timesheet` (
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `task_id` int unsigned NOT NULL,
    `employee_id` int unsigned NOT NULL,
    `date_start` datetime NOT NULL,
    `date_end` datetime NOT NULL,
    PRIMARY KEY (`id`),
    KEY `task_id` (`task_id`),
    KEY `employer_id` (`employee_id`),
    CONSTRAINT `timesheet_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
    CONSTRAINT `timesheet_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
    CONSTRAINT `date_time_timesheet_check` CHECK ((`date_end` > `date_start`))
);

CREATE TABLE balance.`timesheet_history` (
     `id` int unsigned NOT NULL AUTO_INCREMENT,
     `task_id` int unsigned NOT NULL,
     `employee_id` int unsigned NOT NULL,
     `title` varchar(255) NOT NULL,
     `date_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `date_update` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`)
)