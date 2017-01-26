/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.DAO;

import com.romankushmiruk.journal.model.Customer;
import com.romankushmiruk.journal.model.Project;

/**
 *
 * @author Roman
 */
public interface CustomerDAO {
    void checkProject(Project project,Customer customer);
}
