SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `university_admission`;
CREATE SCHEMA IF NOT EXISTS `university_admission` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `university_admission`;

--          Role Table
CREATE TABLE `university_admission`.`role`
(
  `id`        TINYINT     NOT NULL,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

--          exam_type Table
DROP TABLE IF EXISTS `university_admission`.`exam_type`;

CREATE TABLE IF NOT EXISTS `university_admission`.`exam_type`
(
  `id`        INT         NOT NULL,
  `exam_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

--          User Table
DROP TABLE IF EXISTS `university_admission`.`User`;

CREATE TABLE IF NOT EXISTS `university_admission`.`user`
(
  `id`         INT(11)      NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45)  NOT NULL,
  `last_name`  VARCHAR(45)  NOT NULL,
  `email`      VARCHAR(255) NOT NULL,
  `password`   VARCHAR(32)  NOT NULL,
  `role_id`    TINYINT(4)   NOT NULL,
  PRIMARY KEY (`id`, `role_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
      REFERENCES `university_admission`.`role` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

--          Entrant Table
DROP TABLE IF EXISTS `university_admission`.`Entrant`;

CREATE TABLE IF NOT EXISTS `university_admission`.`entrant`
(
  `id`          INT(11)     NOT NULL AUTO_INCREMENT,
  `city`        VARCHAR(45) NOT NULL,
  `district`    VARCHAR(45) NOT NULL,
  `school`      VARCHAR(45) NOT NULL,
  `User_idUser` INT(11)     NOT NULL,
  --   `isBlocked` TINYINT(1) NOT NULL DEFAULT '0',
  `diplomaMark` INT         NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `User_idUser_idx` (`User_idUser` ASC) VISIBLE,
  CONSTRAINT `fk_Entrant_User`
    FOREIGN KEY (`User_idUser`)
      REFERENCES `university_admission`.`user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

--          Faculty Table
DROP TABLE IF EXISTS `university_admission`.`Faculty`;

CREATE TABLE IF NOT EXISTS `university_admission`.`faculty`
(
  `id`           INT(11)             NOT NULL AUTO_INCREMENT,
  `name_ru`      VARCHAR(100)        NOT NULL,
  `name_eng`     VARCHAR(100)        NOT NULL,
  `total_seats`  INT(11)             NOT NULL,
  `budget_seats` INT(11)             NOT NULL,
  `infoEng`      VARCHAR(1000)       NULL DEFAULT 'NO INFORMATION',
  `infoRu`       VARCHAR(1000)       NULL DEFAULT 'НЕТ ИНФОРМАЦИИ',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name_ru` ASC) VISIBLE,
  UNIQUE INDEX `name_eng_UNIQUE` (`name_eng` ASC) VISIBLE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


--          Subject  Table
DROP TABLE IF EXISTS `university_admission`.`Subject`;

CREATE TABLE IF NOT EXISTS `university_admission`.`subject`
(
  `id`       INT(11)     NOT NULL AUTO_INCREMENT,
  `name_ru`  VARCHAR(45) NOT NULL,
  `name_eng` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name_ru` ASC) VISIBLE,
  UNIQUE INDEX `name_eng_UNIQUE` (`name_eng` ASC) VISIBLE
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

--          Faculty_Entrants  Table
DROP TABLE IF EXISTS `university_admission`.`Faculty_Entrants`;

CREATE TABLE IF NOT EXISTS `university_admission`.`Faculty_Entrants`
(
  `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Entrant_idEntrant` INT          NOT NULL,
  `Faculty_idFaculty` INT          NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `Faculty_idFaculty_idx` (`Faculty_idFaculty` ASC),
  INDEX `Entrant_idEntrant_idx` (`Entrant_idEntrant` ASC),
  UNIQUE INDEX `idFacultyEntrants_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_Entrant_has_Faculty_Entrant1`
    FOREIGN KEY (`Entrant_idEntrant`)
      REFERENCES `university_admission`.`Entrant` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_Entrant_has_Faculty_Faculty1`
    FOREIGN KEY (`Faculty_idFaculty`)
      REFERENCES `university_admission`.`Faculty` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

--          Faculty_Subjects  Table
DROP TABLE IF EXISTS `university_admission`.`Faculty_Subjects`;

CREATE TABLE IF NOT EXISTS `university_admission`.`Faculty_Subjects`
(
  `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Faculty_idFaculty` INT          NOT NULL,
  `Subject_idSubject` INT          NOT NULL,
  PRIMARY KEY (`id`, `Faculty_idFaculty`, `Subject_idSubject`),
  INDEX `Subject_idSubject_idx` (`Subject_idSubject` ASC),
  INDEX `Faculty_idFaculty_idx` (`Faculty_idFaculty` ASC),
  UNIQUE INDEX `idFacultySubjects_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_Faculty_has_Subject_Faculty1`
    FOREIGN KEY (`Faculty_idFaculty`)
      REFERENCES `university_admission`.`Faculty` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_Faculty_has_Subject_Subject1`
    FOREIGN KEY (`Subject_idSubject`)
      REFERENCES `university_admission`.`Subject` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

--          Mark  Table
DROP TABLE IF EXISTS `university_admission`.`mark`;

CREATE TABLE IF NOT EXISTS `university_admission`.`mark`
(
  `id`                INT(10) UNSIGNED    NOT NULL AUTO_INCREMENT,
  `Entrant_idEntrant` INT(11)             NOT NULL,
  `Subject_idSubject` INT(11)             NOT NULL,
  `value`             TINYINT(3) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idMark_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `Subject_idSubject_idx` (`Subject_idSubject` ASC) VISIBLE,
  INDEX `Entrant_idEntrant_idx` (`Entrant_idEntrant` ASC) VISIBLE,
  CONSTRAINT `fk_Entrant_has_Subject_Entrant1`
    FOREIGN KEY (`Entrant_idEntrant`)
      REFERENCES `university_admission`.`entrant` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_Entrant_has_Subject_Subject1`
    FOREIGN KEY (`Subject_idSubject`)
      REFERENCES `university_admission`.`subject` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


--          Mail  Table
DROP TABLE IF EXISTS `university_admission`.`mail`;

CREATE TABLE `university_admission`.`mail`
(
  `id`     INT         NOT NULL AUTO_INCREMENT,
  `mailId` VARCHAR(45) NOT NULL,
  `key`    VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `mailId_UNIQUE` (`mailId` ASC) VISIBLE
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8
  COLLATE = utf8_bin;

--          entrant_marks_sum View
DROP VIEW IF EXISTS `university_admission`.`entrant_marks_sum`;
CREATE OR REPLACE VIEW `entrant_marks_sum` AS
SELECT university_admission.Faculty_Entrants.Faculty_idFaculty AS `facultyId`,
       university_admission.mark.Entrant_idEntrant             AS `entrantId`,
       SUM(university_admission.mark.value)                    AS `preliminary_sum`,
       entrant.diplomaMark
FROM university_admission.Faculty_Entrants
       INNER JOIN university_admission.mark
                  ON university_admission.Faculty_Entrants.Entrant_idEntrant =
                     university_admission.mark.Entrant_idEntrant
       INNER JOIN entrant on Faculty_Entrants.Entrant_idEntrant = entrant.id
GROUP BY Faculty_Entrants.Faculty_idFaculty, entrantId;


--          faculties_report_sheet View
DROP VIEW IF EXISTS `university_admission`.`faculties_report_sheet`;
USE `university_admission`;
CREATE OR REPLACE VIEW university_admission.`faculties_report_sheet` AS
SELECT `facultyId`,
       university_admission.user.id                      as User_idUser,
       university_admission.user.first_name,
       university_admission.user.last_name,
       university_admission.user.email,
       `preliminary_sum`,
       entrant_marks_sum.diplomaMark,
       `preliminary_sum` + entrant_marks_sum.diplomaMark AS `total_sum`
FROM university_admission.`entrant_marks_sum`
       INNER JOIN
     university_admission.faculty ON `entrant_marks_sum`.`entrantId` = university_admission.faculty.id
       INNER JOIN
     university_admission.entrant ON `entrantId` = university_admission.entrant.id
       INNER JOIN
     university_admission.user ON university_admission.entrant.User_idUser = university_admission.user.id
ORDER BY `total_sum` DESC, university_admission.faculty.id;

--      result

DROP TABLE IF EXISTS `university_admission`.`result`;

CREATE TABLE IF NOT EXISTS `university_admission`.`result`
(
  `User_idUser` INT(11) NOT NULL,
  `faculty_id`  INT(11) NOT NULL,
  `form_id`     INT(11) NOT NULL,
  INDEX `fk_result_formOfEducation1` (`form_id` ASC) VISIBLE,
  INDEX `fk_result_user1_idx` (`User_idUser` ASC) VISIBLE,
  INDEX `fk_result_faculty1_idx` (`faculty_id` ASC) VISIBLE,
  CONSTRAINT `fk_result_formOfEducation1`
    FOREIGN KEY (`form_id`)
      REFERENCES `university_admission`.`formOfEducation` (`id`),
  CONSTRAINT `fk_result_user1`
    FOREIGN KEY (`User_idUser`)
      REFERENCES `university_admission`.`user` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_result_faculty1`
    FOREIGN KEY (`faculty_id`)
      REFERENCES `university_admission`.`faculty` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

--      formOfEducation

DROP TABLE IF EXISTS `university_admission`.`formOfEducation`;

CREATE TABLE IF NOT EXISTS `university_admission`.`formOfEducation`
(
  `id`      INT         NOT NULL,
  `formRu`  VARCHAR(100) NOT NULL,
  `formEng` VARCHAR(100) NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `entrant` AUTO_INCREMENT = 1;
insert into entrant (city, district, school, User_idUser, diplomaMark) values ('Oklahoma City', 'Oklahoma', 'School #1', 1, 44);
insert into entrant (city, district, school, User_idUser, diplomaMark) values ('Stamford', 'Connecticut', 'School #2', 2, 67);
insert into entrant (city, district, school, User_idUser, diplomaMark) values ('Yakima', 'Washington', 'School #3', 3, 98);
insert into entrant (city, district, school, User_idUser, diplomaMark) values ('New Orleans', 'Louisiana', 'School #4', 4, 87);
insert into entrant (city, district, school, User_idUser, diplomaMark) values ('Scottsdale', 'Arizona', 'School #5', 5, 56);
insert into entrant (city, district, school, User_idUser, diplomaMark) values ('Fayetteville', 'North Carolina', 'School #6', 6,  67);
insert into entrant (city, district, school, User_idUser, diplomaMark) values ('Dallas', 'Texas', 'School #7', 7, 88);

ALTER TABLE `exam_type` AUTO_INCREMENT = 1;
insert into exam_type (id, exam_type) values (1, 'diploma');
insert into exam_type (id, exam_type) values (2, 'preliminary');

ALTER TABLE `faculty` AUTO_INCREMENT = 1;
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Автоматика и приборостроения', 'DEPARTMENT OF AUTOMATICS AND INSTRUMENT', 20,10);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Бизнес и финансы', 'DEPARTMENT OF BUSINESS AND FINANCE', 30,20);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Инженерно-физический', 'DEPARTMENT FOR ENGINEERING PHYSICS', 40,30);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Интегральной подготовки', 'DEPARTMENT OF INTEGRAL EDUCATION', 50,40);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Интегрированные технологии и химическая техника', 'DEPARTMENT OF THE INTEGRAL TECHNOLOGY AND APPLIED CHEMISTRY', 60,50);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Информатика и управление', 'DEPARTMENT FOR COMPUTER SCIENCE AND MANAGEMENT', 70,60);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Компьютерные и информационные технологии', 'DEPARTMENT OF COMPUTER SCIENCE AND INFORMATION TECHNOLOGIES', 80,70);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Машиностроительный', 'DEPARTMENT OF MECHANICAL ENGINEERING', 90,80);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Международного образования', 'DEPARTMENT OF INTERNATIONAL EDUCATION', 100,90);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Механико-технологический', 'MECHANICAL & TECHNOLOGICAL DEPARTMENT', 15,5);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Немецкий технический', 'GERMAN TECHNICAL DEPARTMENT', 25,15);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Технологии неорганических веществ', 'DEPARTMENT TECHNOLOGY OF INORGANIC SUBSTANCES', 35,25);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Технологии органических веществ', 'DEPARTMENT TECHNOLOGY OF ORGANIC SUBSTANCES', 45,35);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Транспортного машиностроения', 'DEPARTMENT FOR TRANSPORT ENGINEERING INDUSTRY', 55,45);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Физико-технический', 'PHYSICO-TECHNICAL DEPARTMENT', 65,55);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Экономическая информатика и менеджмент', 'DEPARTMENT OF ECONOMIC INFORMATION SCIENCE AND MANAGEMENT', 75,65);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Экономический', 'ECONOMIC DEPARTMENT', 85,75);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Электромашиностроительный', 'ELECTRIC MACHINE INDUSTRY', 95,85);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Электроэнергетический', 'DEPARTMENT OF ELECTRIC POWER STATIONS', 105,95);
INSERT INTO university_admission.faculty(name_ru, name_eng,total_seats,budget_seats) VALUES ('Энергомашиностроительный', 'ENERGY MACHINE INDUSTRY', 115,105);

ALTER TABLE `Faculty_Subjects` AUTO_INCREMENT = 1;
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (1,1);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (1,2);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (1,3);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (2,4);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (2,5);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (2,6);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (3,7);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (3,8);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (3,1);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (4,2);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (4,3);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (4,4);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (5,5);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (5,6);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (5,7);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (6,8);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (6,1);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (6,2);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (7,3);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (7,4);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (7,5);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (8,6);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (8,7);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (8,8);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (9,1);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (9,2);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (9,3);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (10,4);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (10,5);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (10,6);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (11,7);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (11,8);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (11,1);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (12,2);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (12,3);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (12,4);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (13,5);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (13,6);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (13,7);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (14,8);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (14,1);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (14,2);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (15,3);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (15,4);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (15,5);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (16,6);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (16,7);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (16,8);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (17,1);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (17,2);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (17,3);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (18,4);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (18,5);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (18,6);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (19,7);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (19,8);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (19,1);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (20,2);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (20,3);
INSERT INTO university_admission.Faculty_Subjects(Faculty_idFaculty, Subject_idSubject) VALUES (20,4);

ALTER TABLE `formOfEducation` AUTO_INCREMENT = 1;
insert into university_admission.formOfEducation (id, formEng, formRu) values (1, 'free of charge form of education', 'бесплатную форму обучения');
insert into university_admission.formOfEducation (id, formEng, formRu) values (2, 'fee-based form of education', 'платную форму обучения');

ALTER TABLE `mark` AUTO_INCREMENT = 1;
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (1, 1, 33);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (1, 2, 43);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (1, 3, 53);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (2, 4, 12);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (2, 5, 56);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (2, 6, 78);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (3, 7, 34);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (3, 8, 56);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (3, 1, 12);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (4, 2, 23);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (4, 3, 34);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (4, 4, 99);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (5, 5, 99);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (5, 6, 49);
INSERT INTO university_admission.mark(Entrant_idEntrant, Subject_idSubject, value) VALUES (5, 7, 94);

ALTER TABLE `role` AUTO_INCREMENT = 1;
insert into role (id, role_name) values (1, 'client');
insert into role (id, role_name) values (2, 'admin');

ALTER TABLE `subject` AUTO_INCREMENT = 1;
INSERT INTO `subject` (name_ru, name_eng) VALUES ("Математика", "Math");
INSERT INTO `subject` (name_ru, name_eng) VALUES ("Литература", "Literature");
INSERT INTO `subject` (name_ru, name_eng) VALUES ("Биология", "Biology");
INSERT INTO `subject` (name_ru, name_eng) VALUES ("Химия", "Chemistry");
INSERT INTO `subject` (name_ru, name_eng) VALUES ("Украинский язык", "Ukrainian");
INSERT INTO `subject` (name_ru, name_eng) VALUES ("История", "History");
INSERT INTO `subject` (name_ru, name_eng) VALUES ("Физика", "Physics");
INSERT INTO `subject` (name_ru, name_eng) VALUES ("Английский язык", "English");

ALTER TABLE `user` AUTO_INCREMENT = 1;
INSERT INTO `user` (`id`, `first_name`,`last_name`,`email`,`password`,`role_id`) VALUES (1, "Sharon","Wiggins","arcu@sodalesnisi.edu",7068,1);
INSERT INTO `user` (`first_name`,`last_name`,`email`,`password`,`role_id`) VALUES ("Noah","Frost","mollis.nec@malesuadavel.edu",6433,1);
INSERT INTO `user` (`first_name`,`last_name`,`email`,`password`,`role_id`) VALUES ("Lillian","Wilder","dolor.Fusce.mi@liberoatauctor.co.uk",6152,1);
INSERT INTO `user` (`first_name`,`last_name`,`email`,`password`,`role_id`) VALUES ("Kameko","Bush","tortor@inaliquet.net",2556,1);
INSERT INTO `user` (`first_name`,`last_name`,`email`,`password`,`role_id`) VALUES ("Brennan","Irwin","aliquam@quisdiam.edu",5965,1);
INSERT INTO `user` (`first_name`,`last_name`,`email`,`password`,`role_id`) VALUES ("Shafira","Barnett","iaculis@Nullamvitaediam.net",6577,1);
INSERT INTO `user` (`first_name`,`last_name`,`email`,`password`,`role_id`) VALUES ("Macey","Jefferson","sit.amet.diam@sitamet.edu",1282,1);
INSERT INTO `user` (`first_name`,`last_name`,`email`,`password`,`role_id`) VALUES ("Darth","Vader","vader@gmail.com",1234,2);
INSERT INTO `user` (`first_name`,`last_name`,`email`,`password`,`role_id`) VALUES ("Shafira","Barnett","Aliquam@Nullamvitaediam.net",6577,1);

DELIMITER $$
CREATE PROCEDURE `getBudgetEntrants`(faculty_id INT, numberOfSeats INT)
BEGIN
  DECLARE numberOfRecords INT DEFAULT 0;
  SELECT COUNT(*) INTO numberOfRecords
  FROM `faculties_report_sheet`
  where faculties_report_sheet.facultyId = faculty_id;
  if numberOfRecords > numberOfSeats then
    INSERT INTO university_admission.result(User_idUser, faculty_id, form_id)
    SELECT User_idUser, facultyId, 1 AS form_id
    FROM faculties_report_sheet
    WHERE faculties_report_sheet.facultyId = faculty_id
    ORDER BY faculties_report_sheet.total_sum DESC
    LIMIT numberOfSeats;
  end if;
  if numberOfRecords <= numberOfSeats THEN
    INSERT INTO university_admission.result(User_idUser, faculty_id, form_id)
    SELECT User_idUser, facultyId, 1 AS form_id
    FROM faculties_report_sheet
    WHERE faculties_report_sheet.facultyId = faculty_id
    ORDER BY faculties_report_sheet.total_sum DESC;
  end if;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `getNotBudgetEntrants`(faculty_id INT, numberOfSeats INT, numberOfBudgetSeats INT)
BEGIN
  DECLARE numberOfRecords INT DEFAULT 0;
  DECLARE variable INT DEFAULT 0;
  SELECT COUNT(*) INTO numberOfRecords
  FROM `faculties_report_sheet`
  where faculties_report_sheet.facultyId = faculty_id;
  if numberOfRecords > numberOfBudgetSeats then
    set variable = numberOfSeats - numberOfBudgetSeats;
    INSERT INTO university_admission.result(User_idUser, faculty_id, form_id)
    SELECT User_idUser, facultyId, 2 AS form_id
    FROM faculties_report_sheet
    WHERE faculties_report_sheet.facultyId = faculty_id
    ORDER BY faculties_report_sheet.total_sum DESC
    LIMIT variable offset numberOfBudgetSeats;
  end if;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `getScore`(faculty_id INT, numberOfSeats INT)
BEGIN
  DECLARE numberOfRecords INT DEFAULT 0;
  DECLARE variable INT DEFAULT 0;
  SELECT COUNT(*) INTO numberOfRecords
  FROM `faculties_report_sheet`
  where faculties_report_sheet.facultyId = faculty_id;
  if numberOfRecords >= numberOfSeats then
    set variable = numberOfSeats - 1;
    SELECT total_sum
    from faculties_report_sheet
    where faculties_report_sheet.facultyId = faculty_id
    order by faculties_report_sheet.total_sum desc
    limit 1 offset variable;
  end if;
END$$
DELIMITER ;
