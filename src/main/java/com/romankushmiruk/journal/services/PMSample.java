/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.services;

import java.util.Locale;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *
 * @author Roman
 */
public class PMSample {
    public static void main(String[] args) {
         GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("dataSource.xml");
        ctx.refresh();
        ProjectManagerDAOImpl pmImpl = ctx.getBean("ProjectManagerDAOImpl",ProjectManagerDAOImpl.class);
        
        Locale.setDefault(Locale.ENGLISH);
        
        
    }
}
