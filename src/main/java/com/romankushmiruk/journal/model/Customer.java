/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.model;

import java.util.List;

/**
 *
 * @author Roman
 */

public class Customer {
    private int id;
    private String companyName;
    private String person;

    public Customer(String companyName, String person) {
        this.companyName = companyName;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String company) {
        this.companyName = company;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

  
    
    
    
}
