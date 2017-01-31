/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.DAO;


import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Users;
import java.util.List;

/**
 *
 * @author Roman
 */
public interface AdminDAO {

//   void createEmployee(Employee employee,Contact contact);
   void createUser(Users user);
   void createProject(Project project);
   
   void appointPM(String projectName,String userLogin);
   void enablePM(String projectName);
   void updateUser(Users user);
   void updateProject(Project project);
   
   void deleteUser(String userLogin);
   void deleteProject(String projectTitle);
   
   
  
   List<Users> findUserById(int id);
   List<Users> findUserBySurname(String surname);
   List<Users> findUserByLogin(String login);
   List<Users> findUserByPost(String post);
   List<Project> findProjectByTitle(String title);
   List<Project> findProjectById(int id);
   List<Project> findProjectByCustomer(String customerLogin);
   List<Project> findProjectByPM(String pmLogin);
   
   List<Users> showAllUsers();
   List<Users> showAllEmployee();
   List<Users> showAllEmployeeAndPM();
   List<Users> showAllCustomer();
   List<Users> showAllPM();
   List<Project> showAllProject();
   List<Project> showAllPmProjects(String pmLogin);
   List<Project> showAllFinishedProject();
   List<Project> showAllNotFinishedProject();
  
           
   
}

