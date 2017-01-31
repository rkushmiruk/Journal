package com.romankushmiruk.journal.DAO;

import com.romankushmiruk.journal.model.Job;
import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Sprint;
import com.romankushmiruk.journal.model.Task;
import com.romankushmiruk.journal.model.Users;
import java.util.List;

/**
 *
 * @author Roman
 */
public interface ProjectManagerDAO {
    void createSprint(Sprint sprint,String projectTitle);
    void createTask(Task task);

    int findProjectId(String projectTitle);
    int findSprintId(String sprintTitle);
    int findTaskId(String taskTitle);
    int findUserId(String userLogin);
    List<Sprint> findSprintsByTitle(String sprintTitle,String projectTitle);
    List<Task> findTask(String sprintTitle,String taskTitle);
    
    void updateSprint(Sprint sprint);
    void finishSprint(String sprintTitle,String projectTitle);
    void finishProject(String projectTitle);
    void finishTask(String taskTitle,String sprintTitle);
    
    void deleteSprint(String sprintTitle,String projectTitle);
    void deleteTask(String sprintTitle,String taskTitle);
   
    void appointTask(String taskTitle,String employeeLogin,String sprintTitle);
    void enableTask(String taskTitle,String employeeLogin,String sprintTitle);
    

    List<Project> showAllPmProjects(String pmLogin);
    List<Sprint> showAllSprintForProject(String projectTitle);
    List<Sprint> showAllFinishedSprints(String projectTitle);
    List<Sprint> showAllNotFinishedSprints(String projectTitle);
    List<Project> showAllFinishedProject();
    List<Project> showAllNotFinishedProject();
    List<Task> showAllTasksForSprint(String sprintTitle);
    List<Task> showAllTasks();
    List<Task> showAllFinishedTasks();
    List<Task> showAllNotFinishedTasks();
    List<Users> showAllEmployees();
    
    List<Job> showAllJob();
    List<Job> showAllJobWithValue();
    List<Task> showAllNotAcceptTask();
    List<Task> showAllAcceptTask();
    
 
}
