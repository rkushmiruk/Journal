package com.romankushmiruk.journal.DAO;

import com.romankushmiruk.journal.model.Task;
import java.util.List;


public interface EmployeeDAO {
    
    List<Task> showListOfNominations(String employeeLogin);
    
    int findUserId(String userLogin);
    
    int findTaskId(String taskTitle);
    
    void acceptTask(String employeeLogin,String taskTitle);
    
    void finishTask(String taskTitle,int sprintId);
    
    
}

