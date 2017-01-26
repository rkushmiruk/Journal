/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.validation;

import com.romankushmiruk.journal.model.Users;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Roman
 */
@Component
public class UserValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
       return Users.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users user = (Users) o;
        String name = user.getName();
        String surname = user.getName();
        String post = user.getPost();
        String email = user.getEmail();
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Name can not be emty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "Surame can not be emty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "post", "Post can not be emty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Email can not be emty");
           
        
        
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
//        if (user.getUsername().length() < 8 || user.getUsername().length() > 32) {
//            errors.rejectValue("username", "Size.userForm.username");
//        }
//
//        if (userService.findByUsername(user.getUsername()) != null) {
//            errors.rejectValue("username", "Duplicate.userForm.username");
//        }
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
//        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
//            errors.rejectValue("password", "Size.userForm.password");
//        }
//
//        if (!user.getConfirmPassword().equals(user.getPassword())) {
//            errors.rejectValue("confirmPassword", "Different.userForm.password");
//        }
       
    }
    
    
}
