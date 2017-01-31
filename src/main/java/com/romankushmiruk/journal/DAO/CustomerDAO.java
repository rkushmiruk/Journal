/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.DAO;

import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Sprint;
import java.util.List;



/**
 *
 * @author Roman
 */
public interface CustomerDAO {
    List<Project> showAllCustomerProjects(String customerLogin);
    int findCustomerId(String customerLogin);
    List<Sprint> showAllSprintForProject(String projectTitle);
    List<Sprint> showAllFinishedSprints(String projectTitle);
    int findProjectId(String projectTitle);
     List<Project> findProjectByTitle(String title);
}
