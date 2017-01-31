package com.romankushmiruk.journal.controller;

import com.romankushmiruk.journal.model.Task;
import com.romankushmiruk.journal.services.EmployeeServiceImpl;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Roman
 */
@Controller
public class EmployeeController {
    private EmployeeServiceImpl employeeServiceImpl;
    private String employeeName;
    
    @RequestMapping(value ={"/employee"},method = RequestMethod.GET)
        public String adminPage(Model model,Principal principal){
            employeeName = principal.getName();
            return "employee/employeePage";
      }
    
    @RequestMapping(value = {"/employee/listOfNominations"},method = RequestMethod.GET)
    public String listOfNominations(Model model){
        List<Task> tasks = employeeServiceImpl.showListOfNominations(employeeName);
        model.addAttribute("tasks", tasks);
        return "employee/listOfNominations";
    }
    
    @RequestMapping(value = {"/employee/acceptTask"},method = RequestMethod.GET)
    public String showAcceptTaskForm(Model model){
         if(employeeName == null){
            return "<input type =\"button\" name =\"Back->\" value =\"Back->\" onClick =\"history.back()\"><br/></td></tr>";
        }
        List<Task> tasks = employeeServiceImpl.showListOfNominations(employeeName);
        model.addAttribute("tasks", tasks);
        return "employee/acceptTask";
    }
    
   @RequestMapping(value = {"/employee/acceptTask"},method = RequestMethod.POST)
    public @ResponseBody String acceptTask(@Valid @ModelAttribute("taskTitle") String taskTitle){
        List<Task> tasks = employeeServiceImpl.showListOfNominations(employeeName);
        if(employeeName == null){
            return "RefreshPage";
        }
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getTitle().equals(taskTitle)){
               employeeServiceImpl.acceptTask(employeeName, taskTitle);
               return "Task accepted!";
            }
        }
        return "Task is accepted already!";
    }
    
    
    
    
    @Autowired
    public void setEmployeeService(EmployeeServiceImpl employeeServiceImpl){
            Locale.setDefault(Locale.ENGLISH);
            this.employeeServiceImpl = employeeServiceImpl;
    }
    
}
