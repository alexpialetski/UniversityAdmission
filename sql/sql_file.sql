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
SELECT university_admission.faculty_entrants.Faculty_idFaculty AS `facultyId`,
       university_admission.mark.Entrant_idEntrant             AS `entrantId`,
       SUM(university_admission.mark.value)                    AS `preliminary_sum`,
       entrant.diplomaMark
FROM university_admission.faculty_entrants
       INNER JOIN university_admission.mark
                  ON university_admission.faculty_entrants.Entrant_idEntrant =
                     university_admission.mark.Entrant_idEntrant
       INNER JOIN entrant on faculty_entrants.Entrant_idEntrant = entrant.id
GROUP BY faculty_entrants.Faculty_idFaculty, entrantId;


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
      REFERENCES `university_admission`.`formofeducation` (`id`),
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
  `formRu`  VARCHAR(45) NOT NULL,
  `formEng` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



DROP PROCEDURE IF EXISTS `getBudgetEntrants`;
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
END;


--          getNotBudgetEntrants
DROP PROCEDURE IF EXISTS `getNotBudgetEntrants`;
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
END;


--          getScore
DROP PROCEDURE IF EXISTS `getScore`;
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
END;

