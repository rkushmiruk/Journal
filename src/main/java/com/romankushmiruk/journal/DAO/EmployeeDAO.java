/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.DAO;

import com.romankushmiruk.journal.model.Employee;
import com.romankushmiruk.journal.model.Task;

/**
 *
 * @author Roman
 */
public interface EmployeeDAO {
     boolean acceptTask(Task task);
     void finishTime();
     void askTime();
     
    
//    Employee findEmpById(int id);
//    Employee findEmpByName(String name);
    
    
    
    
    
}
