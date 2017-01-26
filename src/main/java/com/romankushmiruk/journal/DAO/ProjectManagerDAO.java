/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.DAO;

import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Sprint;

/**
 *
 * @author Roman
 */
public interface ProjectManagerDAO {
    
    Project selectProject(String title);
    
    void createSprint(Sprint sprint);
    
    
    
}
