SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `simulator` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `simulator` ;

-- -----------------------------------------------------
-- Table `simulator`.`CompanySingleton`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `simulator`.`Company` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `full_name` VARCHAR(200) NULL ,
  `profit` DOUBLE NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simulator`.`Employees`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `simulator`.`Employees` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `date_of_birth` TIMESTAMP NULL ,
  `first_name` VARCHAR(200) NULL ,
  `second_name` VARCHAR(200) NULL ,
  `salary` INT NULL ,
  `Company_id` INT NOT NULL ,
  `dtype` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_Employees_Company` (`Company_id` ASC) ,
  CONSTRAINT `fk_Employees_Company`
    FOREIGN KEY (`Company_id` )
    REFERENCES `simulator`.`Company` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
