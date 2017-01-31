/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.model;

import java.util.Date;
import javax.validation.constraints.Future;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author Roman
 */
public class Project {
    private int id;
    @NotEmpty(message = "Title can not be empty!")
    private String title;
    private int customer_id;
    private int manager_id;
    private String manager;
    private String customer;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date beginDate;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date endDate;
    private Date finishDate;


     public Project(){
              }

    public Project(String title,int company_id) {
        this.title = title;
        this.customer_id = company_id;
    }
    public Project(String title,Date beginDate,Date endDate,String customer){
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.title = title;
            this.customer = customer;
    }
     public Project(String title,Date beginDate,Date endDate,int customer_id){
            this.beginDate = beginDate;
            this.endDate = endDate;
            this.title = title;
            this.customer_id = customer_id;
    }
      

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }
    
    

    public int getId() {
        return id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
    
    

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

 

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

   

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }


    
    
            
}
