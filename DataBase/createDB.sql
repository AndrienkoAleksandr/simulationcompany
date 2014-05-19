SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `simulator` DEFAULT CHARACTER SET latin1 ;
USE `simulator` ;

-- -----------------------------------------------------
-- Table `simulator`.`Company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simulator`.`Company` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(200) NULL DEFAULT NULL,
  `profit` DOUBLE NULL,
  `total_profit` DOUBLE NULL,
  `type_saving_data` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3;


-- -----------------------------------------------------
-- Table `simulator`.`Employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simulator`.`Employees` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date_of_birth` TIMESTAMP NULL DEFAULT NULL,
  `first_name` VARCHAR(200) NULL DEFAULT NULL,
  `second_name` VARCHAR(200) NULL DEFAULT NULL,
  `salary` DOUBLE NULL DEFAULT NULL,
  `Company_id` INT(11) NOT NULL,
  `dtype` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Employees_Company_idx` (`Company_id` ASC),
  CONSTRAINT `fk_Employees_Company`
    FOREIGN KEY (`Company_id`)
    REFERENCES `simulator`.`Company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 42
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
