/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.model;

import java.sql.Date;


/**
 *
 * @author Roman
 */

public class Task {
    private Sprint sprint;
    private String title;
    private Date beginDate;
    private Date endDate;
    private Date finishDate;
    private String description;
    private Status status;
    private Date Estimate;
}
