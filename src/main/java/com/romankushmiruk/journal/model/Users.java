package com.romankushmiruk.journal.model;



import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Roman
 */
public class Users {
  
   private int id;
   @NotEmpty(message = "Please enter your Surname")
   private String surname;
   @NotEmpty(message = "Please enter your Name")
   @Size(min=2, message = "Name can not have 1 letter" )
   private String name;
   private String login;
   private String pass;
   private String post;
   private int Enable;
   @Email(message = "Not valid email")
   @NotEmpty(message = "Please enter your email addresss.")
   private String email;
 
   private String phone;

    public Users(String surname, String name, String pass, String email, String phone,String post) {
        this.surname = surname;
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
        this.post = post;
    }
    
    public Users(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getEnable() {
        return Enable;
    }

    public void setEnable(int Enable) {
        this.Enable= Enable;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
   
   
   
}
