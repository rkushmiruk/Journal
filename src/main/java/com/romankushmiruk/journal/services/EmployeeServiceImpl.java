package com.romankushmiruk.journal.services;

import com.romankushmiruk.journal.DAO.EmployeeDAO;
import com.romankushmiruk.journal.model.Status;
import com.romankushmiruk.journal.model.Task;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author Roman
 */
public class EmployeeServiceImpl implements EmployeeDAO ,InitializingBean{
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
    public int findUserId(String userLogin) {
        Map<String,String> paramMap = new HashMap<>();
        String sql = "Select u_id from users where u_login="+"'"+userLogin+"'";
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }
    
    
    @Override
    public List<Task> showListOfNominations(String employeeLogin) {
        String sql = "Select T_SPRINT,T_ID,T_TITLE,T_BEGIN,T_END,T_FINISH,T_DESCRIPTION,T_STATUS,T_ESTIMATE from task "
                + "Inner Join job on task.t_id=job.J_task where J_ACCEPT =? and j_emp=? ";
      
        return jdbcTemplate.query(sql, rowMapperTask,new Object[]{
            "N",
            findUserId(employeeLogin)
        });
    }

    @Override
    public void acceptTask(String employeeLogin,String taskTitle) {
        String sql = "Update job set j_accept=? where j_emp=? and j_task=?";
            jdbcTemplate.update(sql, new Object[]{
                "Y",
                findUserId(employeeLogin),
                findTaskId(taskTitle)    
                    
        });
    }
    
     @Override
    public void finishTask(String taskTitle,int sprintId) {
        long curTime = System.currentTimeMillis(); 
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String curStringDate = formatter.format(curTime);
        
        try {
            Date date = formatter.parse(curStringDate);
            String sql = "Update task set t_finish=?,t_status=? where t_title=? and t_sprint=?";
            jdbcTemplate.update(sql, new Object[]{
            date,
            Status.Closed.toString(),
            taskTitle,
            sprintId
            
        });
        } catch (ParseException ex) {
            Logger.getLogger(ProjectManagerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public int findTaskId(String taskTitle) {
        Map<String,String> paramMap = new HashMap<>();
        String sql = "Select t_id from task where t_title="+"'"+taskTitle+"'";
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }
    
    
   
    
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
    
    
}
