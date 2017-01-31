/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;


/**
 *
 * @author Roman
 */

public class Task {
    private int sprint_id;
    private String sprint;
    private int id;
    private String title;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date beginDate;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date endDate;
    private Date finishDate;
    private String description;
    private Status status;
    private int Estimate;

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }
    
    public int getSprint_id() {
        return sprint_id;
    }

    public void setSprint_id(int sprint_id) {
        this.sprint_id = sprint_id;
    }

    public int getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getEstimate() {
        return Estimate;
    }

    public void setEstimate(int Estimate) {
        this.Estimate = Estimate;
    }

  
    
    
}
