package com.romankushmiruk.journal.controller;

import java.security.Principal;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author Roman
 */

@Controller
public class MainController {
   
  
    
    @RequestMapping(value ={"/","/welcome"},method = RequestMethod.GET)
    public String welcomePage(Model model){
        model.addAttribute("title","Welcome");
        model.addAttribute("message","This is welcome page");
        
        return "main/welcomePage";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model){
         Locale.setDefault(Locale.ENGLISH);
        return "main/loginPage";
    }
    
    @RequestMapping(value ={"/logoutSuccessfulPage"},method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model){
        model.addAttribute("title","Logout");
        return "main/logoutSuccessfulPage";
    }
    
     @RequestMapping(value ={"/user"},method = RequestMethod.GET)
     public String user(Model model,Principal principal){
         String userName = principal.getName();
         
         System.out.println("User Name: "+ userName);
         
         return "main/userPage";
     }
     
     
      @RequestMapping(value ={"/403"},method = RequestMethod.GET)
      public String accessDenied(Model model, Principal principal){
          if (principal !=null){
              model.addAttribute("message","Hi "+principal.getName()+" You dont have permission to access this page!");
          } else{
              model.addAttribute("msg", "You dont have permission to access this page!");
          }
          
          return "main/403Page";
      }     
    
      
     
}
