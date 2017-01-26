/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.services;

import com.romankushmiruk.journal.DAO.AdminDAO;
import com.romankushmiruk.journal.model.Contact;
import com.romankushmiruk.journal.model.Customer;
import com.romankushmiruk.journal.model.Employee;
import com.romankushmiruk.journal.model.Post;
import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


/**
 *
 * @author Roman
 */
@Service("adminService")
public class AdminServiceImpl implements AdminDAO, InitializingBean{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
   
    
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if(dataSource==null){
            throw new BeanCreationException("Must set dataSource on AdminDAO");
        }
        if(jdbcTemplate == null){
            throw new BeanCreationException("Null JdbcTemplate on AdminDAO");
        }
        if(namedParameterJdbcTemplate==null){
            throw new BeanCreationException("Null NamedParameterJdbcTemplate on AdminDAO");
        }
    }

   
    @Override
    public void createUser(Users user) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("u_surname", user.getSurname());
        paramMap.put("u_name", user.getName());
        paramMap.put("u_post", user.getPost().toString());
        paramMap.put("u_login",user.getSurname()+user.getName());
        paramMap.put("u_pass", user.getPass());
        paramMap.put("u_enable","1");
        paramMap.put("u_email",user.getEmail());
        paramMap.put("u_phone",user.getPhone());
        
        
        String sqlEmp = "INSERT INTO users (U_SURNAME,U_NAME,U_POST,U_LOGIN,U_PASS,U_ENABLE,U_EMAIL,U_PHONE) VALUES (?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sqlEmp,new Object[]{
           paramMap.get("u_surname"),
           paramMap.get("u_name"),
           paramMap.get("u_post"),
           paramMap.get("u_login"),
           paramMap.get("u_pass"),
           Integer.parseInt(paramMap.get("u_enable")),
           paramMap.get("u_email"),
           paramMap.get("u_phone")
        });
    }

    @Override
    public void createProject(Project project) {        
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("p_title", project.getTitle());
        paramMap.put("p_customer_Login",project.getCustomer());
        String sql = "INSERT INTO project (P_TITLE,P_CUSTOMER_ID,P_BEGIN,P_END) VALUES(?,?,?,?)";
        
        String sqlId = "SELECT u_id FROM users WHERE  u_login="+"'"+paramMap.get("p_customer_Login")+"'";
        int id = namedParameterJdbcTemplate.queryForObject(sqlId, paramMap, Integer.class);

        jdbcTemplate.update(sql, new Object[]{
            paramMap.get("p_title"),
            id,
            project.getBeginDate(),
            project.getEndDate()
        });

     
    
        
    }
    
    @Override
    public void appointPM(String projectName,String userLogin) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("u_login", userLogin);
        
        String sqlProject = "UPDATE project SET p_manager_id=? where p_title = ?";
      
       try{
        String sqlId = "Select u_id from users where u_login ="+"'"+paramMap.get("u_login")+"'";
        int id =  namedParameterJdbcTemplate.queryForObject(sqlId,paramMap,Integer.class);
         
        jdbcTemplate.update(sqlProject,new Object[]{
            id,
            projectName,
        });
        
        String sqlEmployee = "UPDATE users SET u_post=? where u_id = ?";
         
        jdbcTemplate.update(sqlEmployee,new Object[]{
           
            Post.ProjectManager.toString(),
            id
            
        });
       
    }catch(DataAccessException e){
           System.out.println("Такого сотрудника нет!");
       }
    }

    @Override
    public void enablePM(String projectName) {
         Map<String,String> paramMap = new HashMap<>();
            paramMap.put("p_title", projectName);
        
        String sqlProject = "UPDATE project SET p_manager_id=? where p_title = ?";
        
        
       try{
        String sqlId = "Select u_id from users INNER JOIN PROJECT on users.u_id = project.p_manager_id where p_title ="+"'"+paramMap.get("p_title")+"'";
        int id =  namedParameterJdbcTemplate.queryForObject(sqlId,paramMap,Integer.class);

        jdbcTemplate.update(sqlProject,new Object[]{
            null,
            projectName
        });
        
        List<Users>  pmList = findUserById(id);
        List<Project> pmProjects = showAllPmProjects(pmList.get(0).getLogin());
        if(pmProjects.size()>1){ // Validate if pm have more than 1 project
           return;
        }else{
        String sqlUser = "UPDATE users SET u_post=? where u_id = ?";
         
        jdbcTemplate.update(sqlUser,new Object[]{
           
            Post.Employee.toString(),
            id
        });
      }
    }catch(Exception e){
           System.out.println("Такого сотрудника нет!");
       }
    }
    
    @Override
    public void updateUser(Users user) {
        Map<String,String> paramMap = new HashMap<>();
        
        paramMap.put("u_surname", user.getSurname());
        paramMap.put("u_name", user.getName());
        paramMap.put("u_login",user.getSurname()+user.getName());
        paramMap.put("u_pass", user.getPass());
        paramMap.put("u_enable","1");
        paramMap.put("u_email",user.getEmail());
        paramMap.put("u_phone",user.getPhone());
        
        String sql = "Update users set u_surname=?,u_name=?,u_login=?,u_pass=?,u_email=?,u_phone=? where u_id=?";
        jdbcTemplate.update(sql, new Object[]{
            paramMap.get("u_surname"),
            paramMap.get("u_name"),
            paramMap.get("u_login"),
            paramMap.get("u_pass"),
            paramMap.get("u_email"),
            paramMap.get("u_phone"),
            user.getId()
        });
    }

    @Override
    public void updateProject(Project project) {
        Map<String,String> paramMap = new HashMap<>();
        
        paramMap.put("p_title", project.getTitle());
        paramMap.put("p_begin", project.getBeginDate().toString());
        paramMap.put("p_end",project.getEndDate().toString());
      
        String sql = "Update project set p_title=?,p_begin=?,p_end=? where p_id=?";
        jdbcTemplate.update(sql, new Object[]{
            paramMap.get("p_title"),
            project.getBeginDate(),
            project.getEndDate(),
            project.getId()
            
        });
        
    }
    
    
    
    @Override
    public void deleteUser(String userLogin) {
        try{
        String sql = "DELETE FROM users WHERE u_login= ?";    
        jdbcTemplate.update(sql,userLogin);
        }catch(Exception e){
            System.out.println("Этот работник PM");
        }
    }

    @Override
    public void deleteProject(String projectTitle) {
         Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("p_title", projectTitle);
        
        String sql = "DELETE FROM project WHERE p_title = ?";
        String sqlId = "Select u_id from users INNER JOIN PROJECT on users.u_id = project.p_manager_id where p_title ="+"'"+paramMap.get("p_title")+"'";
        
        
         int id =  namedParameterJdbcTemplate.queryForObject(sqlId,paramMap,Integer.class);
         jdbcTemplate.update(sql,projectTitle);
         List<Users> pmList = findUserById(id);
         List<Project> pmProjects = showAllPmProjects(pmList.get(0).getLogin());
          if(pmProjects.size()>1){ // Validate if pm have more than 1 project
           return;
        }else{
        String sqlUser = "UPDATE users SET u_post=? where u_id = ?";
         
        jdbcTemplate.update(sqlUser,new Object[]{
           
            Post.Employee.toString(),
            id
        });
      }
         
    }

    
    
    

    @Override
    public List<Users> findUserByLogin(String login) {
        String sql = "Select * from users where u_login = ?";
       return jdbcTemplate.query(sql, rowMapperUsers,login);
    }

    
    
    @Override
    public List<Users> findUserBySurname(String surname) {
       String sql = "Select * from users where u_surname = ?";
       return jdbcTemplate.query(sql, rowMapperUsers,surname);
    }
    
    
    @Override
    public List<Users> findUserById(int id){
        String sql = "Select * from users where u_id=?";
        return jdbcTemplate.query(sql, rowMapperUsers, id);
    }

    @Override
    public List<Users> findUserByPost(String post) {
       String sql = "Select * from users where u_post=?";
        return jdbcTemplate.query(sql, rowMapperUsers, post);
    }
   
    
    @Override
    public List<Project> findProjectByTitle(String title) {
        String sql="Select * from project where p_title =?";
        return jdbcTemplate.query(sql, rowMapperProject,title);   
    }

    @Override
    public List<Project> findProjectById(int id) {
        String sql="Select * from project where p_id =?";
        return jdbcTemplate.query(sql, rowMapperProject,id);   
    }

    @Override
    public List<Project> findProjectByCustomer(String customerLogin) {
        try{    
          
            String sql = "Select p_id,p_title,p_manager_id,p_begin,p_end,p_finish,p_customer_id "
                    + "from project Inner Join users on project.p_customer_id = users.u_id where u_login =?";
            return jdbcTemplate.query(sql, rowMapperProject,customerLogin);
        
        }catch(Exception e){
            
            System.out.println("Такой компании нет!");
            return null;
        }
    }

    @Override
    public List<Project> findProjectByPM(String pmLogin) {
        try{    
          
            String sql = "Select p_id,p_title,p_manager_id,p_begin,p_end,p_finish,p_customer_id "
                    + "from project Inner Join users on project.p_manager_id = users.u_id where u_login =?";
            return jdbcTemplate.query(sql, rowMapperProject,pmLogin);
        
        }catch(Exception e){
            
            System.out.println("Такой компании нет!");
            return null;
        }
    }
    
    @Override 
    public List<Project> showAllPmProjects(String pmLogin){
     String sql ="Select p_id,p_title,p_manager_id,p_begin,p_end,p_customer_id from project INNER JOIN users on users.u_id = project.p_manager_id where u_login = ?";
     return jdbcTemplate.query(sql, rowMapperProject, pmLogin);
    }

    @Override
    public List<Users> showAllEmployee() {
     String sql = "SELECT * FROM users where u_post = ?";
     return jdbcTemplate.query(sql, rowMapperUsers,Post.Employee.toString());
    }

    @Override
    public List<Users> showAllEmployeeAndPM() {
       String sql = "SELECT * FROM users where u_post = ? or u_post= ?";
     return jdbcTemplate.query(sql, rowMapperUsers,new Object[]{
         Post.Employee.toString(),
         Post.ProjectManager.toString()
     });
    }

    @Override
    public List<Users> showAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, rowMapperUsers);
    }

    
    
    @Override
    public List<Users> showAllCustomer() {
      String sql = "SELECT * FROM users where u_post = ?";
     return jdbcTemplate.query(sql, rowMapperUsers,Post.Customer.toString());
    }

    @Override
    public List<Project> showAllProject() {
     String sql = "SELECT * FROM project";
     return jdbcTemplate.query(sql, rowMapperProject);
    }

    
    @Override
    public List<Users> showAllPM() {
       String sql = "SELECT * FROM users where u_post = ?";
     return jdbcTemplate.query(sql, rowMapperUsers,Post.ProjectManager.toString());
    }
    
    
     private RowMapper<Users> rowMapperUsers =  new RowMapper<Users>() {
        @Override
        public Users mapRow(ResultSet rs, int i) throws SQLException {
            Users user = new Users(rs.getString("u_surname"),rs.getString("u_name"),rs.getString("u_pass"),rs.getString("u_email"),rs.getString("u_phone"),rs.getString("u_post"));
            user.setLogin(rs.getString("u_Login"));
            user.setEnable(rs.getInt("u_enable"));
            user.setId(rs.getInt("u_id"));
            return user;
            
        }
    };
    
     private RowMapper<Project> rowMapperProject =  new RowMapper<Project>() {
         
         @Override
        public Project mapRow(ResultSet rs, int i) throws SQLException {
            Project project = new Project(rs.getString("p_title"),rs.getDate("p_begin"),rs.getDate("p_end"), rs.getInt("p_customer_id"));
            project.setId(rs.getInt("p_id"));
            project.setManager_id(rs.getInt("p_manager_id"));
            project.setFinishDate(rs.getDate("p_finish"));
            
            
            if (project.getManager_id() == 0){
                project.setManager("No PM");
            }else{
                project.setManager(findUserById(project.getManager_id()).get(0).getLogin());
            }
            if(project.getCustomer_id() == 0){
                project.setCustomer("No Customer");
            } else{
                project.setCustomer(findUserById(project.getCustomer_id()).get(0).getLogin());
            }
             
            
            
            return project;
        }
    };
     
  

  
    
    
}
