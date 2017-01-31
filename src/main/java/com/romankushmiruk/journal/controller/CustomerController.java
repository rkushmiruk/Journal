/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.controller;


import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Sprint;
import com.romankushmiruk.journal.services.CustomerServiceImpl;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Roman
 */
@Controller
public class CustomerController {
        private CustomerServiceImpl customerServiceImpl;
    
     @RequestMapping(value ={"/customer"},method = RequestMethod.GET)
        public String customerPage(Model model,Principal principal){
            List<Project> projects = customerServiceImpl.showAllCustomerProjects(principal.getName());
            model.addAttribute("projects", projects);
            return "customer/customerPage";
      }
      
        @RequestMapping(value = {"/customer"},method = RequestMethod.POST)
        public String showProject(@Valid @ModelAttribute("project") String project,BindingResult result,Model model){
            List<Sprint> allSprints = customerServiceImpl.showAllSprintForProject(project);
            List<Sprint> finishedSprints = customerServiceImpl.showAllFinishedSprints(project);
            List<Project> projects = customerServiceImpl.findProjectByTitle(project);
            String message ="";
            
            if(allSprints.isEmpty() && projects.get(0).getFinishDate()!=null){
                message = "finished";
            }
            else if(allSprints.isEmpty()){
                message = "This Project dont start";
                
            }
            else{
               int percent = (finishedSprints.size()*100)/(allSprints.size());
               message = percent+"%";
            }
            
            model.addAttribute("message", message);
            return "customer/projectForm";
        }
        
        
    @Autowired
    public void setCustomerService(CustomerServiceImpl customerServiceImpl){
            Locale.setDefault(Locale.ENGLISH);
            this.customerServiceImpl = customerServiceImpl;
    }
  
}
