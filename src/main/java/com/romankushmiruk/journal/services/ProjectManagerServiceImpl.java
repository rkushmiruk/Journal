package com.romankushmiruk.journal.services;

import com.romankushmiruk.journal.DAO.ProjectManagerDAO;
import com.romankushmiruk.journal.model.Job;
import com.romankushmiruk.journal.model.Post;
import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Sprint;
import com.romankushmiruk.journal.model.Task;
import com.romankushmiruk.journal.model.Users;
import com.romankushmiruk.journal.model.Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author Roman
 */
public class ProjectManagerServiceImpl implements ProjectManagerDAO ,InitializingBean{
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
    public List<Project> showAllPmProjects(String pmLogin){
     String sql ="Select p_id,p_title,p_manager_id,p_begin,p_end,p_finish,p_customer_id from project INNER JOIN users on users.u_id = project.p_manager_id where u_login = ?";
     return jdbcTemplate.query(sql, rowMapperProject, pmLogin);
    }

    @Override
    public int findProjectId(String projectTitle) {
        Map<String,String> paramMap = new HashMap<>();
        String sql = "Select p_id from project where p_title="+"'"+projectTitle+"'";
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    @Override
    public List<Task> findTask(String sprintTitle, String taskTitle) {
        String sql= "Select * from task where t_title = ? and t_sprint=?";
        return jdbcTemplate.query(sql, rowMapperTask,new Object[]{
            taskTitle,
            findSprintId(sprintTitle)
        });   
    }
    
    

    @Override
    public List<Sprint> showAllSprintForProject(String projectTitle) {
     String sql = "SELECT * FROM sprint where s_project = ?";
     return jdbcTemplate.query(sql, rowMapperSprint,findProjectId(projectTitle));
    }

    @Override
    public List<Sprint> showAllFinishedSprints(String projectTitle) {
     String sql = "SELECT * FROM sprint where s_project = ? and s_finish is not null";
     return jdbcTemplate.query(sql, rowMapperSprint,findProjectId(projectTitle));
    }

    @Override
    public List<Sprint> showAllNotFinishedSprints(String projectTitle) {
          String sql = "SELECT * FROM sprint where s_project = ? and s_finish is null";
          return jdbcTemplate.query(sql, rowMapperSprint,findProjectId(projectTitle));
    }

    @Override
    public List<Project> showAllFinishedProject() {
        String sql = "SELECT * FROM project where p_finish is not null";
        return jdbcTemplate.query(sql, rowMapperProject);
    }

    @Override
    public List<Project> showAllNotFinishedProject() {
         String sql = "SELECT * FROM project where p_finish is null";
        return jdbcTemplate.query(sql, rowMapperProject);
    }

    @Override
    public List<Task> showAllTasksForSprint(String sprintTitle) {
         String sql = "SELECT * FROM task where t_sprint = ?";
     return jdbcTemplate.query(sql, rowMapperTask,findSprintId(sprintTitle));
    }

    @Override
    public List<Task> showAllTasks() {
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, rowMapperTask);
    }

    @Override
    public List<Task> showAllFinishedTasks() {
        String sql = "SELECT * FROM task where t_finish is not null";
        return jdbcTemplate.query(sql, rowMapperTask);
    }

    @Override
    public List<Task> showAllNotFinishedTasks() {
       String sql = "SELECT * FROM task where t_finish is null";
        return jdbcTemplate.query(sql, rowMapperTask);
    }
    
    
    
    
    

    @Override
    public void createSprint(Sprint sprint,String projectTitle) {
        String sql = "INSERT INTO sprint (S_PROJECT,S_TITLE,S_BEGIN,S_END,S_DESCRIPTION,S_STAGE) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{
            findProjectId(projectTitle),
            sprint.getTitle(),
            sprint.getBeginDate(),
            sprint.getEndDate(),
            sprint.getDescription(),
            sprint.getStage()
           
        });
    }

    @Override
    public void updateSprint(Sprint sprint) {
        String sql = "Update sprint set s_title=?,s_begin=?,s_end=?,s_description=?,s_stage=? where s_id=?";
        jdbcTemplate.update(sql, new Object[]{
            sprint.getTitle(),
            sprint.getBeginDate(),
            sprint.getEndDate(),
            sprint.getDescription(),
            sprint.getStage(),
            sprint.getId()
            
        });
    }

    @Override
    public void finishSprint(String sprintTitle, String projectTitle) {
        long curTime = System.currentTimeMillis(); 
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String curStringDate = formatter.format(curTime);
        
        try {
            Date date = formatter.parse(curStringDate);
            String sql = "Update sprint set s_finish=? where s_title=? and s_project=?";
            jdbcTemplate.update(sql, new Object[]{
            date,
            sprintTitle,
            findProjectId(projectTitle)
            
        });
        } catch (ParseException ex) {
            Logger.getLogger(ProjectManagerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void finishTask(String taskTitle,String sprintTitle) {
        long curTime = System.currentTimeMillis(); 
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String curStringDate = formatter.format(curTime);
        
        try {
            Date date = formatter.parse(curStringDate);
            String sql = "Update task set t_finish=?,t_status=? where t_title=? and t_sprint=?";
            jdbcTemplate.update(sql, new Object[]{
            date,
            Status.Closed.toString(),
            taskTitle,
            findSprintId(sprintTitle)
            
        });
        } catch (ParseException ex) {
            Logger.getLogger(ProjectManagerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public void finishProject(String projectTitle) {
        long curTime = System.currentTimeMillis(); 
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String curStringDate = formatter.format(curTime);
        
        try {
            Date date = formatter.parse(curStringDate);
            String sql = "Update project set p_finish=? where p_id=?";
            jdbcTemplate.update(sql, new Object[]{
            date,
            findProjectId(projectTitle)
            
        });
        } catch (ParseException ex) {
            Logger.getLogger(ProjectManagerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    

    @Override
    public List<Sprint> findSprintsByTitle(String sprintTitle,String projectTitle) {
       String sql= "Select * from sprint where s_title = ? and s_project=?";
        return jdbcTemplate.query(sql, rowMapperSprint,new Object[]{
            sprintTitle,
            findProjectId(projectTitle)
        });   
    }

    @Override
    public void deleteSprint(String sprintTitle,String projectTitle) {
       
        String sql = "DELETE FROM sprint WHERE s_title= ? and s_project=?";    
        jdbcTemplate.update(sql,new Object[]{
            sprintTitle,
            findProjectId(projectTitle)
        });
       
    }

    @Override
    public void deleteTask(String sprintTitle, String taskTitle) {
        String sql = "DELETE FROM task WHERE t_title= ? and t_sprint=?";    
        jdbcTemplate.update(sql,new Object[]{
            taskTitle,
            findSprintId(sprintTitle)
        });
    }
    
    

    @Override
    public int findSprintId(String sprintTitle) {
        Map<String,String> paramMap = new HashMap<>();
        String sql = "Select s_id from sprint where s_title="+"'"+sprintTitle+"'";
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    
    
    @Override
    public void createTask(Task task) {
      String sql = "INSERT INTO task (T_SPRINT,T_TITLE,T_BEGIN,T_END,T_DESCRIPTION,T_ESTIMATE) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{
            findSprintId(task.getSprint()),
            task.getTitle(),
            task.getBeginDate(),
            task.getEndDate(),
            task.getDescription(),
            task.getEstimate()
           
        });
    }

    @Override
    public void appointTask(String taskTitle, String employeeLogin,String sprintTitle) {
        String sql = "Insert INTO JOB(J_TASK,J_EMP,J_SPRINT,J_ACCEPT) VALUES(?,?,?,?)";
        jdbcTemplate.update(sql,new Object[]{
            findTaskId(taskTitle),
            findUserId(employeeLogin),
            findSprintId(sprintTitle),
            "N"
        });
    }

    @Override
    public void enableTask(String taskTitle, String employeeLogin,String sprintTitle) {
       String sql = "Delete from JOB where J_TASK=? and J_EMP=? and J_SPRINT=?";
        jdbcTemplate.update(sql,new Object[]{
            findTaskId(taskTitle),
            findUserId(employeeLogin),
            findSprintId(sprintTitle)
        });
    }

    @Override
    public int findTaskId(String taskTitle) {
        Map<String,String> paramMap = new HashMap<>();
        String sql = "Select t_id from task where t_title="+"'"+taskTitle+"'";
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    @Override
    public int findUserId(String userLogin) {
        Map<String,String> paramMap = new HashMap<>();
        String sql = "Select u_id from users where u_login="+"'"+userLogin+"'";
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    @Override
    public List<Users> showAllEmployees() {
     String sql = "SELECT * FROM users where u_post = ?";
     return jdbcTemplate.query(sql, rowMapperUsers,Post.Employee.toString());
    }

    @Override
    public List<Job> showAllJob() {
        String sql = "SELECT * FROM job";
     return jdbcTemplate.query(sql, rowMapperJob);
    }

    @Override
    public List<Task> showAllNotAcceptTask() {
        String sql = "Select T_SPRINT,T_ID,T_TITLE,T_BEGIN,T_END,T_FINISH,T_DESCRIPTION,T_STATUS,T_ESTIMATE from task "
                + "Inner Join job on task.t_id=job.J_task where J_ACCEPT =?";
      
        return jdbcTemplate.query(sql, rowMapperTask,"N");
    }

    @Override
    public List<Task> showAllAcceptTask() {
        String sql = "Select T_SPRINT,T_ID,T_TITLE,T_BEGIN,T_END,T_FINISH,T_DESCRIPTION,T_ESTIMATE from task "
                + "Inner Join job on task.t_id=job.J_task where J_ACCEPT =?";
      
        return jdbcTemplate.query(sql, rowMapperTask,"Y");
    }

    @Override
    public List<Job> showAllJobWithValue() {
       String sql = "Select u_login,t_title,s_title,j_accept from job Inner Join users on job.j_emp=users.u_id\n" +
                        "inner join task on job.j_task=task.t_id\n" +
                        "inner join sprint on job.j_sprint=sprint.s_id";
      return jdbcTemplate.query(sql, rowMapperJobWithValue);
    }
    
    
    
    
    
    private RowMapper<Project> rowMapperProject =  new RowMapper<Project>() {
         
        @Override
        public Project mapRow(ResultSet rs, int i) throws SQLException {
            Project project = new Project(rs.getString("p_title"),rs.getDate("p_begin"),rs.getDate("p_end"), rs.getInt("p_customer_id"));
            project.setId(rs.getInt("p_id"));
            project.setManager_id(rs.getInt("p_manager_id"));
            project.setFinishDate(rs.getDate("p_finish"));
         
            return project;
        }
    };
    private RowMapper<Sprint> rowMapperSprint =  new RowMapper<Sprint>() {
        @Override
        public Sprint mapRow(ResultSet rs, int i) throws SQLException {
            Sprint sprint = new Sprint();
            sprint.setTitle(rs.getString("s_title"));
            sprint.setId(rs.getInt("s_id"));
            sprint.setProject_id(rs.getInt("s_project"));
            sprint.setBeginDate(rs.getDate("s_begin"));
            sprint.setEndDate(rs.getDate("s_end"));
            sprint.setFinishDate(rs.getDate("s_finish"));
            sprint.setDescription(rs.getString("s_description"));
            sprint.setStage(rs.getInt("s_stage"));
            return sprint;
            
        }
    };

     private RowMapper<Task> rowMapperTask =  new RowMapper<Task>() {
        @Override
        public Task mapRow(ResultSet rs, int i) throws SQLException {
            Task task = new Task();
            task.setTitle(rs.getString("t_title"));
            task.setId(rs.getInt("t_id"));
            task.setSprint_id(rs.getInt("t_sprint"));
            task.setBeginDate(rs.getDate("t_begin"));
            task.setEndDate(rs.getDate("t_end"));
            task.setFinishDate(rs.getDate("t_finish"));
            task.setDescription(rs.getString("t_description"));
            task.setEstimate(rs.getInt("t_estimate"));
            
            return task;
        }
    };
     
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
       
       private RowMapper<Job> rowMapperJob =  new RowMapper<Job>() {
        @Override
        public Job mapRow(ResultSet rs, int i) throws SQLException {
            Job job = new Job();
            job.setUser_id(rs.getInt("J_EMP"));
            job.setTask_id(rs.getInt("J_TASK"));
            job.setSprint_id(rs.getInt("J_SPRINT"));
            if (rs.getString("J_ACCEPT").equals("N")){
                job.setIsAccept(false);
            }else{
                job.setIsAccept(true);
            }
            return job;
            
        }
    };
       
       private RowMapper<Job> rowMapperJobWithValue =  new RowMapper<Job>() {
        @Override
        public Job mapRow(ResultSet rs, int i) throws SQLException {
            Job job = new Job();
            job.setUser_login(rs.getString("u_login"));
            job.setTask_title(rs.getString("t_title"));
            job.setSprint_title(rs.getString("s_title"));
           
            if (rs.getString("J_ACCEPT").equals("N")){
                job.setIsAccept(false);
            }else{
                job.setIsAccept(true);
            }
            return job;
            
        }
    };


}
