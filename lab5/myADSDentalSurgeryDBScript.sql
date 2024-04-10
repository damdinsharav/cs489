CREATE SCHEMA `cs489-apsd-lab5a-dental-sugery-db`;

CREATE TABLE `cs489-apsd-lab5a-dental-sugery-db`.`dentists` (
  `dentistId` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`dentistId`));

CREATE TABLE `cs489-apsd-lab5a-dental-sugery-db`.`patients` (
  `patientId` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`patientId`));

CREATE TABLE `cs489-apsd-lab5a-dental-sugery-db`.`surgeries` (
  `surgeryId` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surgeryNo` CHAR(3) NULL,
  PRIMARY KEY (`surgeryId`),
  UNIQUE INDEX `surgeryNo_UNIQUE` (`surgeryNo` ASC) VISIBLE);

CREATE TABLE `cs489-apsd-lab5a-dental-sugery-db`.`addresses` (
  `addressId` INT NOT NULL AUTO_INCREMENT,
  `street` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(2) NULL,
  `zipCode` VARCHAR(10) NULL,
  PRIMARY KEY (`addressId`));

CREATE TABLE `cs489-apsd-lab5a-dental-sugery-db`.`appointments` (
  `appointmentId` BIGINT NOT NULL AUTO_INCREMENT,
  `appointmentDate` DATE NOT NULL,
  `appointmentTime` TIME NOT NULL,
  `dentistId` INT NULL,
  `patientId` INT NULL,
  `surgeryId` INT NULL,
  PRIMARY KEY (`appointmentId`),
  INDEX `fk_dentist_appointment_idx` (`dentistId` ASC) VISIBLE,
  INDEX `fk_patient_appointment_idx` (`patientId` ASC) VISIBLE,
  INDEX `fk_surgery_appointment_idx` (`surgeryId` ASC) VISIBLE,
  CONSTRAINT `fk_dentist_appointment`
    FOREIGN KEY (`dentistId`)
    REFERENCES `cs489-apsd-lab5a-dental-sugery-db`.`dentists` (`dentistId`)
    ON DELETE SET NULL
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_patient_appointment`
    FOREIGN KEY (`patientId`)
    REFERENCES `cs489-apsd-lab5a-dental-sugery-db`.`patients` (`patientId`)
    ON DELETE SET NULL
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_surgery_appointment`
    FOREIGN KEY (`surgeryId`)
    REFERENCES `cs489-apsd-lab5a-dental-sugery-db`.`surgeries` (`surgeryId`)
    ON DELETE SET NULL
    ON UPDATE RESTRICT);

ALTER TABLE `cs489-apsd-lab5a-dental-sugery-db`.`patients` 
ADD COLUMN `addressId` INT NULL AFTER `lastName`,
ADD INDEX `fk_patient_address_idx` (`addressId` ASC) VISIBLE;
;
ALTER TABLE `cs489-apsd-lab5a-dental-sugery-db`.`patients` 
ADD CONSTRAINT `fk_patient_address`
  FOREIGN KEY (`addressId`)
  REFERENCES `cs489-apsd-lab5a-dental-sugery-db`.`addresses` (`addressId`)
  ON DELETE SET NULL
  ON UPDATE RESTRICT;

ALTER TABLE `cs489-apsd-lab5a-dental-sugery-db`.`surgeries` 
ADD COLUMN `addressId` INT NULL AFTER `surgeryNo`,
ADD INDEX `fk_surgery_address_idx` (`addressId` ASC) VISIBLE;
;
ALTER TABLE `cs489-apsd-lab5a-dental-sugery-db`.`surgeries` 
ADD CONSTRAINT `fk_surgery_address`
  FOREIGN KEY (`addressId`)
  REFERENCES `cs489-apsd-lab5a-dental-sugery-db`.`addresses` (`addressId`)
  ON DELETE SET NULL
  ON UPDATE RESTRICT;
