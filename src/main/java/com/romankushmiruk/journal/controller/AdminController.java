/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.controller;


import com.romankushmiruk.journal.model.Post;
import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Users;
import com.romankushmiruk.journal.services.AdminServiceImpl;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Roman
 */
@Controller
public class AdminController {
    private AdminServiceImpl adminServiceImpl;
   

      @RequestMapping(value ={"/admin"},method = RequestMethod.GET)
        public String adminPage(Model model){
            return "admin/adminPage";
      }
     
      @RequestMapping(value = {"/addUser"}, method = RequestMethod.POST)
      public @ResponseBody String addUser(@Valid @ModelAttribute("users") Users user, BindingResult result){
         
          if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
          adminServiceImpl.createUser(user);
            return "User " +user.getSurname()+" "+user.getName()+" added" ;
      }
      
      @RequestMapping(value = {"/addUser"},method = RequestMethod.GET)
      public String showUserForm(){
          return "admin/addUser";
      }
      
      @RequestMapping(value = {"/addProject"},method = {RequestMethod.GET})
      public String showProjectForm(Model model){
          List<Users> customers = adminServiceImpl.showAllCustomer();
          model.addAttribute("customers",customers);
          return "admin/addProject";
      }
      
      @RequestMapping(value = {"/addProject"}, method = RequestMethod.POST)
      public @ResponseBody String addProject(@Valid @ModelAttribute("project") Project project, BindingResult result){
           List<Project> projects = adminServiceImpl.showAllProject();
          if(project.getBeginDate()==null || project.getEndDate()==null){
              return "Empty or Wrong Date";
          }
          
          if (result.hasErrors()){
             
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
          
          if(project.getBeginDate().getTime() >= project.getEndDate().getTime()){
              return "BeginDate can not be later than endDate";
          }
          for(int i =0;i<projects.size();i++){
               if(project.getTitle().equals(projects.get(i).getTitle()) ){
                   return "Project with this Title exist!";
               }
          }
         adminServiceImpl.createProject(project);
         return "Project "+project.getTitle()+ " added";
      }
      
      @RequestMapping(value = {"/appointPM"},method = RequestMethod.GET)
      public String appointPmForm(Model model){
         List<Project> projects = adminServiceImpl.showAllProject();
         List<Users> employees = adminServiceImpl.showAllEmployeeAndPM();
         model.addAttribute("projects", projects);
         model.addAttribute("employees",employees);
         return "admin/appointPm";
      }
      
      @RequestMapping(value = {"/appointPM"},method = RequestMethod.POST)
      public @ResponseBody String appointPm(@Valid @ModelAttribute("project") String projectName, 
              @Valid @ModelAttribute("employee") String userLogin, BindingResult result){
           
          if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
         
          List<Project> projects = adminServiceImpl.findProjectByTitle(projectName);
          if(projects.get(0).getManager_id() !=0){
              return "This project have pm. You can enable it "+
             "<input type =\"button\" name =\"enablePM\" value =\"enablePM\" onClick =\"location.href='/Journal/enablePM'\">";
          }
          
          
          adminServiceImpl.appointPM(projectName, userLogin);
          return "Pm "+userLogin+" appointed";
      }
      
       @RequestMapping(value = {"/enablePM"},method = RequestMethod.GET)
      public String enablePmForm(Model model){
         List<Project> projects = adminServiceImpl.showAllProject();
         model.addAttribute("projects", projects);
         return "admin/enablePm";
      }
      
      @RequestMapping(value = {"/enablePM"},method = RequestMethod.POST)
      public @ResponseBody String enablePm(@Valid @ModelAttribute("project") String projectName,BindingResult result){
          
          if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
           
          adminServiceImpl.enablePM(projectName);
          return "Pm of "+projectName+" enabled";
      }
      
      
      
      @RequestMapping(value = {"/deleteUser"},method = RequestMethod.GET)
      public String showDeleteUserForm(Model model){
          List<Users> users = adminServiceImpl.showAllUsers();
          model.addAttribute("users", users);
          return "admin/deleteUser";
      }   
         
       @RequestMapping(value = {"/deleteUser"},method = RequestMethod.POST)
      public @ResponseBody String deleteUser(@Valid @ModelAttribute("user") String userLogin,BindingResult result){
           if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
           List<Users> pmList = adminServiceImpl.showAllPM();
           List<Users> customerList = adminServiceImpl.showAllCustomer();
           for (int i = 0; i < pmList.size(); i++) {
               if(userLogin.equals(pmList.get(i).getLogin())){
                   return "This user is Project Manager "+
                           "<input type =\"button\" name =\"enablePM\" value =\"enablePM\" onClick =\"location.href='/Journal/enablePM'\">";
               }
               if(userLogin.equals(customerList.get(i).getLogin())){
                   return "This user is Customer which have projects";
               }
           }
          adminServiceImpl.deleteUser(userLogin);
          return "User:"+userLogin+" deleted!";
      }   
      
      @RequestMapping(value = {"/deleteProject"}, method = {RequestMethod.GET})
      public String showDeleteProjectForm(Model model){
          List<Project> projects = adminServiceImpl.showAllProject();
          model.addAttribute("projects",projects);
          return "admin/deleteProject";
      }
      
      @RequestMapping(value = {"/deleteProject"},method = RequestMethod.POST)
      public @ResponseBody String deleteProject(@Valid @ModelAttribute("project") String projectTitle,BindingResult result){
           
          if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
           adminServiceImpl.deleteProject(projectTitle);
          
          return "Project "+projectTitle+" deleted!";
      }
    
      
      @RequestMapping(value = {"/findUserBySurname"}, method = RequestMethod.GET)
      public String showFindUserBySurnameForm(){
          return "admin/findUserBySurname";
      }
      
      @RequestMapping(value = {"/findUserBySurname"}, method = RequestMethod.POST)
      public String findUserBySurname(@Valid @ModelAttribute("surname") String surname,BindingResult result,Model model){
          List<Users> users = adminServiceImpl.findUserBySurname(surname);
          model.addAttribute("users",users);
          return "admin/ListUsers";
      }
      
      @RequestMapping(value = {"/findUserByLogin"}, method = RequestMethod.GET)
      public String showFindUserByLoginForm(){
          return "admin/findUserByLogin";
      }
      
      @RequestMapping(value = {"/findUserByLogin"}, method = RequestMethod.POST)
      public String findUserByLogin(@Valid @ModelAttribute("login") String login,BindingResult result,Model model){
          List<Users> users = adminServiceImpl.findUserByLogin(login);
          model.addAttribute("users",users);
          return "admin/ListUsers";
      }
      
      @RequestMapping(value = {"/findUserByPost"}, method = RequestMethod.GET)
      public String showFindUserByPostForm(Model model){
          List<Post> posts = Arrays.asList(Post.values());
          model.addAttribute("posts", posts);
          return "admin/findUserByPost";
      }
      
      @RequestMapping(value = {"/findUserByPost"}, method = RequestMethod.POST)
      public String findUserByPost(@Valid @ModelAttribute("post") String post,BindingResult result,Model model){
          List<Users> users = adminServiceImpl.findUserByPost(post);
          model.addAttribute("users",users);
          return "admin/ListUsers";
      }
      
      @RequestMapping(value = {"/findProjectByTitle"}, method = RequestMethod.GET)
      public String showFindProjectByTitleForm(Model model){
          return "admin/findProjectByTitle";
      }
      
      @RequestMapping(value = {"/findProjectByTitle"}, method = RequestMethod.POST)
      public String findProjectByTitle(@Valid @ModelAttribute("title") String title,BindingResult result,Model model){
          List<Project> projects = adminServiceImpl.findProjectByTitle(title);
          model.addAttribute("projects",projects);
          return "admin/ListProjects";
      }
      
       @RequestMapping(value = {"/findProjectByCustomer"}, method = RequestMethod.GET)
      public String showFindProjectByCustomerForm(Model model){
          return "admin/findProjectByCustomer";
      }
      
      @RequestMapping(value = {"/findProjectByCustomer"}, method = RequestMethod.POST)
      public String findProjectByCustomer(@Valid @ModelAttribute("login") String login,BindingResult result,Model model){
          List<Project> projects = adminServiceImpl.findProjectByCustomer(login);
          model.addAttribute("projects",projects);
          return "admin/ListProjects";
      }
      
       @RequestMapping(value = {"/findProjectByPM"}, method = RequestMethod.GET)
      public String showFindProjectByPMForm(Model model){
          return "admin/findProjectByPM";
      }
      
      @RequestMapping(value = {"/findProjectByPM"}, method = RequestMethod.POST)
      public String findProjectByPM(@Valid @ModelAttribute("login") String login,BindingResult result,Model model){
          List<Project> projects = adminServiceImpl.findProjectByPM(login);
          model.addAttribute("projects",projects);
          return "admin/ListProjects";
      }

      @RequestMapping(value = {"/showAllUsers"}, method = RequestMethod.GET)
      public String showAllUsers(Model model){
          List<Users> users = adminServiceImpl.showAllUsers();
          model.addAttribute("users", users);
          return "admin/ListUsers";
      }
      @RequestMapping(value = {"/showAllEmployee"}, method = RequestMethod.GET)
      public String showAllEmployee(Model model){
          List<Users> users = adminServiceImpl.showAllEmployee();
          model.addAttribute("users", users);
          return "admin/ListUsers";
      }
      @RequestMapping(value = {"/showAllCustomer"}, method = RequestMethod.GET)
      public String showAllCustomer(Model model){
          List<Users> users = adminServiceImpl.showAllCustomer();
          model.addAttribute("users", users);
          return "admin/ListUsers";
      }
      @RequestMapping(value = {"/showAllPM"}, method = RequestMethod.GET)
      public String showAllPM(Model model){
          List<Users> users = adminServiceImpl.showAllPM();
          model.addAttribute("users", users);
          return "admin/ListUsers";
      }
       @RequestMapping(value = {"/showAllProject"}, method = RequestMethod.GET)
      public String showAllProject(Model model){
          List<Project> projects = adminServiceImpl.showAllProject();
          model.addAttribute("projects", projects);
          return "admin/ListProjects";
      }
      
      @RequestMapping(value = {"/updateUser"},method = RequestMethod.GET)
      public String showUpdateUserForm(Model model){
          return "admin/updateUser";
      }
      
      @RequestMapping(value = {"/updateUser"},method = RequestMethod.POST)
      public String findUserForUpdate(@Valid @ModelAttribute("login") String login,BindingResult result,Model model){
          List<Users> users = adminServiceImpl.findUserByLogin(login);
          if (!users.isEmpty()){
          model.addAttribute("users", users);
          model.addAttribute("u_id", users.get(0).getId());
          }
         
          return "admin/updateUserForm";
          
      }
     
      @RequestMapping(value = {"/updateUserForm"},method = RequestMethod.POST)
      public @ResponseBody String updateUser(@Valid @ModelAttribute("users") Users user, BindingResult result){
          
           if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
          
          adminServiceImpl.updateUser(user);
           
          return "User update";
      }
      
       @RequestMapping(value = {"/updateProject"},method = RequestMethod.GET)
      public String showUpdateProjectForm(Model model){
          return "admin/updateProject";
      }
      
      @RequestMapping(value = {"/updateProject"},method = RequestMethod.POST)
      public String findProjectForUpdate(@Valid @ModelAttribute("title") String title,BindingResult result,Model model){
          List<Project> projects = adminServiceImpl.findProjectByTitle(title);
          if(!projects.isEmpty()){
          model.addAttribute("projects", projects);
          model.addAttribute("p_id", projects.get(0).getId());
          }
          return "admin/updateProjectForm";
          
      }
     
      @RequestMapping(value = {"/updateProjectForm"},method = RequestMethod.POST)
      public @ResponseBody String updateProject(@Valid @ModelAttribute("project") Project project, BindingResult result,Model model){
        
//          if(project.getBeginDate()==null || project.getEndDate()==null){
//              return "Empty or Wrong Date";
//          }
//          
          if (result.hasErrors()){
             
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
          
          if(project.getBeginDate().getTime() >= project.getEndDate().getTime()){
              return "BeginDate can not be later than endDate";
          }
          
          
          adminServiceImpl.updateProject(project);
           
          return "Project update!";
      }
      
        
    @Autowired
    public void setAdminService(AdminServiceImpl adminServiceImpl){
            Locale.setDefault(Locale.ENGLISH);
            this.adminServiceImpl = adminServiceImpl;
    }
  

    
}
