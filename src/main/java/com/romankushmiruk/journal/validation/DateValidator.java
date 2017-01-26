/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.validation;

import java.lang.annotation.Annotation;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Roman
 */


public class DateValidator implements ConstraintValidator<NotNullDate, Date>{
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        return !(value == null || value.toString().length() == 0);
    }

    @Override
    public void initialize(NotNullDate constraintAnnotation) {
       
    }
    
}
