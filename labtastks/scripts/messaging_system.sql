CREATE DATABASE `messaging_system`;

USE `messaging_system`;

CREATE TABLE `user` (
  `id`        INT(10) UNSIGNED AUTO_INCREMENT NOT NULL,
  `username`  VARCHAR(255) NOT NULL UNIQUE,
  `email`     VARCHAR(255) NOT NULL UNIQUE,
  `password`  VARCHAR(255) NOT NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY(`id`)
);

ALTER TABLE `user` ADD CONSTRAINT `pk_user_id` PRIMARY KEY(`id`);

CREATE TABLE `message` (
  `id`           INT(10) UNSIGNED AUTO_INCREMENT NOT NULL,
  `sender_id`    INT(10) UNSIGNED NOT NULL,
  `recipient_id` INT(10) UNSIGNED NOT NULL,
  `subject`      VARCHAR(255) NOT NULL,
  `content`      VARCHAR(1023) NOT NULL,
  `timestamp`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status`       VARCHAR(1) NOT NULL DEFAULT 'N',
  KEY(`id`)
);

ALTER TABLE `message` ADD CONSTRAINT `pk_message_id` PRIMARY KEY(`id`);
ALTER TABLE `message` ADD CONSTRAINT `fk_message_sender_id` FOREIGN KEY(`sender_id`) REFERENCES `user`(`id`) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE `message` ADD CONSTRAINT `fk_message_recipient_id` FOREIGN KEY(`recipient_id`) REFERENCES `user`(`id`) ON UPDATE CASCADE ON DELETE CASCADE;