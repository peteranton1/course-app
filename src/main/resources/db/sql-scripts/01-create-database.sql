CREATE DATABASE `coursedb` COLLATE 'utf8_general_ci';

ALTER TABLE `lesson`
ADD FOREIGN KEY (`course_id`) REFERENCES `course` (`id`);