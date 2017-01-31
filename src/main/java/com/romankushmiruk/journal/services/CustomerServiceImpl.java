package com.romankushmiruk.journal.services;


import com.romankushmiruk.journal.DAO.CustomerDAO;
import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Sprint;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class CustomerServiceImpl  implements CustomerDAO, InitializingBean{
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
    public List<Project> showAllCustomerProjects(String customerLogin) {
         String sql = "SELECT * FROM project where p_customer_id=?";
         
        return jdbcTemplate.query(sql, rowMapperProject,findCustomerId(customerLogin));
    }

    @Override
    public int findCustomerId(String customerLogin) {
        Map<String,String> paramMap = new HashMap<>();
        String sql = "Select u_id from users where u_login="+"'"+customerLogin+"'";
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    @Override
    public List<Sprint> showAllSprintForProject(String projectTitle) {
        String sql = "SELECT * FROM sprint where s_project = ?";
     return jdbcTemplate.query(sql, rowMapperSprint,findProjectId(projectTitle));
    }
    
     
    @Override
    public int findProjectId(String projectTitle) {
        Map<String,String> paramMap = new HashMap<>();
        String sql = "Select p_id from project where p_title="+"'"+projectTitle+"'";
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }
     @Override
    public List<Sprint> showAllFinishedSprints(String projectTitle) {
     String sql = "SELECT * FROM sprint where s_project = ? and s_finish is not null";
     return jdbcTemplate.query(sql, rowMapperSprint,findProjectId(projectTitle));
    }
    
     @Override
    public List<Project> findProjectByTitle(String title) {
        String sql="Select * from project where p_title =?";
        return jdbcTemplate.query(sql, rowMapperProject,title);   
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
    
}
