-- Create syntax for TABLE 'users'
CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `expires_at` datetime NOT NULL,
  `first_name` varchar(255) NOT NULL DEFAULT '',
  `last_name` varchar(255) NOT NULL DEFAULT '',
  `admin_flag` tinyint(1) NOT NULL DEFAULT '0',
  `state` enum('CREATED','INITIALIZED','SIGNEDUP') NOT NULL DEFAULT 'CREATED',
  `lock_version` bigint(20) NOT NULL DEFAULT '0',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`,`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'channels'
CREATE TABLE `channels` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `overview` text NOT NULL,
  `user` bigint(20) unsigned NOT NULL,
  `lock_version` bigint(20) unsigned NOT NULL DEFAULT '0',
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `CHANNEL_ID` (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `title` (`title`,`deleted_at`),
  KEY `USER_ID` (`user`),
  CONSTRAINT `channels_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create syntax for TABLE 'knowledges'
CREATE TABLE `knowledges` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `text` text NOT NULL,
  `channel` bigint(20) unsigned NOT NULL,
  `user` bigint(20) unsigned NOT NULL,
  `lock_version` bigint(20) unsigned NOT NULL DEFAULT '0',
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `KNOWLEDGE_ID` (`id`),
  KEY `USER_ID` (`user`),
  KEY `CHANNEL_ID` (`channel`),
  CONSTRAINT `knowledges_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`id`),
  CONSTRAINT `knowledges_ibfk_2` FOREIGN KEY (`channel`) REFERENCES `channels` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;