/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Roman
 */
public class Employee {
   private int id;
   private String surname;
   private String name;
   private String login;
   private String pass;
   private Date bthDate;
   private Post post;
   
 
   public Employee(){
       
   }
   
    public Employee(String surname, String name,Post post,String pass) {
        this.surname = surname;
        this.name = name;
        this.login = surname+name;
        this.pass = pass;
        this.post = post;
    }
      public Employee(String surname, String name,String pass) {
        this.surname = surname;
        this.name = name;
        this.login = surname+name;
        this.pass = pass;
       
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

    public Date getBthDate() {
        return bthDate;
    }

    public void setBthDate(Date bthDate) {
        this.bthDate = bthDate;
    }
    

//    @Override
//    public String toString() {
//        return "Employee{" + "surname=" + surname + ", name=" + name + ", login=" + login + ", pass=" + pass + ", bthDate=" + bthDate + ", post=" + post + '}';
//    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

  
   
   
}
