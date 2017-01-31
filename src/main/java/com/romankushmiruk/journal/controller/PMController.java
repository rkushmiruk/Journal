package com.romankushmiruk.journal.controller;

import com.romankushmiruk.journal.model.Job;
import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Sprint;
import com.romankushmiruk.journal.model.Task;
import com.romankushmiruk.journal.model.Users;

import com.romankushmiruk.journal.services.ProjectManagerServiceImpl;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class PMController {
    private ProjectManagerServiceImpl projectManagerServiceImpl;
    private String projectTitle;
    
      @RequestMapping(value ={"/pm"},method = RequestMethod.GET)
        public String pmPage(Model model,Principal principal){
            String pmName = principal.getName();
            List<Project> pmProjects = projectManagerServiceImpl.showAllPmProjects(pmName);
            model.addAttribute("pmName", pmName);
            model.addAttribute("pmProjects", pmProjects);
            
            return "projectmanager/pmPage";
      }
        
        @RequestMapping(value = {"/pm"},method = RequestMethod.POST)
        public String getProjectTitle(@Valid @ModelAttribute("title") String title, BindingResult result){
            projectTitle = title;
            return "redirect:pm/pmForm";
        }
        
        @RequestMapping(value = {"/pm/pmForm"},method = RequestMethod.GET)
        public String pmPageForm(Model model){
             String disabled="";
             String isFinished="";
             if(projectTitle == null){
                return "projectmanager/error";
            }
            List<Project> finishedProjects = projectManagerServiceImpl.showAllFinishedProject();
          
            for(int i=0;i<finishedProjects.size();i++){
                if(projectManagerServiceImpl.findProjectId(projectTitle)==finishedProjects.get(i).getId()){
                    isFinished = "Project Finished!";
                    disabled = "disabled";
                }
            }
            
            model.addAttribute("disabled", disabled);
            model.addAttribute("isFinished", isFinished);
            model.addAttribute("projectTitle",projectTitle);
            
            return "projectmanager/pmForm";
        }
        
        @RequestMapping(value = {"/pm/createSprint"},method = RequestMethod.GET)
        public String showCreateSprintForm(Model model){
            if(projectTitle==null){
            return "projectmanager/error";
             }
            return "projectmanager/createSprint";
        }
        
        @RequestMapping(value ={"/pm/createSprint"},method = RequestMethod.POST)
        public @ResponseBody String createSprint(@Valid @ModelAttribute("sprint") Sprint sprint, BindingResult result,Principal principal){
        List<Sprint> sprints = projectManagerServiceImpl.showAllSprintForProject(projectTitle);
        List<Project> pmProjects = projectManagerServiceImpl.showAllPmProjects(principal.getName());
        Project project =  null;
            if(projectTitle==null){
            return "redirect:/projectmanager/error";
             }
          for(int i=0;i<pmProjects.size();i++){
              if(pmProjects.get(i).getTitle().equals(projectTitle)){
                  project = pmProjects.get(i);
              }
          }
           
          if(sprint.getBeginDate().getTime()< project.getBeginDate().getTime()){
              return "Sprint can not start earlier than Project start("+project.getBeginDate()+")";
          }  
          
          if(sprint.getBeginDate()==null || sprint.getEndDate()==null){
              return "Empty or Wrong Date";
          }
          if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
          if(sprint.getBeginDate().getTime()>sprint.getEndDate().getTime()){
              return "BeginDate can not be later than endDate";
          }
            for(int i=0;i<sprints.size();i++){
             if(sprint.getTitle().equals(sprints.get(i).getTitle())){
                 return "Sprint with this Title exist for this Project";
             }
             if(sprint.getStage()== sprints.get(i).getStage()){
                 return "Stage with this number exist";
             }
             if(sprint.getStage()> sprints.get(i).getStage() && sprint.getBeginDate().getTime()<sprints.get(i).getEndDate().getTime()){
                    return "Begin Date of this Sprint can not be earlier then endDate of sprints before this!";
                }
         } 
            
          
          projectManagerServiceImpl.createSprint(sprint,projectTitle);

            return "Sprint "+sprint.getTitle()+" created! ";
        }
        
      @RequestMapping(value = {"/pm/updateSprint"},method = RequestMethod.GET)
      public String showUpdateSprintForm(Model model){
          return "projectmanager/updateSprint";
      }
      
      @RequestMapping(value = {"/pm/updateSprint"},method = RequestMethod.POST)
      public String findSprintForUpdate(@Valid @ModelAttribute("sprintTitle") String sprintTitle,BindingResult result,Model model){
          if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Sprint> sprints = projectManagerServiceImpl.findSprintsByTitle(sprintTitle, projectTitle);
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
          List<String> beginDates = new ArrayList<>();
          List<String> endDates = new ArrayList<>();
            
          for (int i=0;i<sprints.size();i++){
                beginDates.add(sdf.format(sprints.get(i).getBeginDate()));
            }
          for (int i=0;i<sprints.size();i++){
                endDates.add(sdf.format(sprints.get(i).getEndDate()));
            }
            
          if(!sprints.isEmpty()){
          model.addAttribute("sprints", sprints);
          model.addAttribute("begindates", beginDates);
          model.addAttribute("enddates", endDates);
          model.addAttribute("s_id", sprints.get(0).getId());
          }
          return "projectmanager/updateSprintForm";
          
      }
//     
      @RequestMapping(value = {"/pm/updateSprintForm"},method = RequestMethod.POST)
      public @ResponseBody String updateSprint(@Valid @ModelAttribute("sprint") Sprint sprint, BindingResult result,Model model){
         List<Sprint> sprints = projectManagerServiceImpl.showAllSprintForProject(projectTitle);
         
          if(sprint.getBeginDate()==null || sprint.getEndDate()==null){
              return "Empty or Wrong Date(Format of Data: MM/dd/yyyy)";
          }
         
          if (result.hasErrors()){
             
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
          
          if(sprint.getBeginDate().getTime() >= sprint.getEndDate().getTime()){
              return "BeginDate can not be later than endDate";
          }
            
            for(int i=0;i<sprints.size();i++){
                if(sprint.getStage()> sprints.get(i).getStage() && sprint.getBeginDate().getTime()<sprints.get(i).getEndDate().getTime()){
                    return "Begin Date of this Sprint can not be earlier then endDate of sprints before this!";
                }
            }
            for(int i=0;i<sprints.size();i++){
                if(sprints.get(i).getId()==sprint.getId()){
                sprints.remove(i);
               }
            }
            
            System.out.println(sprint.getTitle());
             for(int i =0;i<sprints.size();i++){
               if(sprint.getTitle().equals(sprints.get(i).getTitle()) ){
                   return "Sprint with this Title exist for this Project!";
               }
               if(sprint.getStage()== sprints.get(i).getStage()){
                 return "Stage with this number exist";
             }
          }
          
          projectManagerServiceImpl.updateSprint(sprint);
           
          return "Project update!";
      }
      
      @RequestMapping(value = {"/pm/findSprintByTitle"}, method = RequestMethod.GET)
      public String showFindProjectByTitleForm(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          return "projectmanager/findSprintByTitle";
      }
      
      @RequestMapping(value = {"/pm/findSprintByTitle"}, method = RequestMethod.POST)
      public String findProjectByTitle(@Valid @ModelAttribute("title") String sprintTitle,BindingResult result,Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Sprint> sprints = projectManagerServiceImpl.findSprintsByTitle(sprintTitle, projectTitle);
          model.addAttribute("sprints",sprints);
          return "projectmanager/ListSprints";
      }
       
      @RequestMapping(value = {"/pm/showAllSprints"}, method = RequestMethod.GET)
        public String showAllSprints(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Sprint> sprints = projectManagerServiceImpl.showAllSprintForProject(projectTitle);
          model.addAttribute("sprints", sprints);
          return "projectmanager/ListSprints";
      }
      
       @RequestMapping(value = {"/pm/showAllFinishedSprints"}, method = RequestMethod.GET)
        public String showAllFinishedSprints(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Sprint> sprints = projectManagerServiceImpl.showAllFinishedSprints(projectTitle);
          model.addAttribute("sprints", sprints);
          return "projectmanager/ListSprints";
      }
      
       @RequestMapping(value = {"/pm/showAllNotFinishedSprints"}, method = RequestMethod.GET)
        public String showAllNotFinishedSprints(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          model.addAttribute("sprints", sprints);
          return "projectmanager/ListSprints";
      }
      
      @RequestMapping(value = {"/pm/deleteSprint"}, method = {RequestMethod.GET})
      public String showDeleteSprintForm(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Sprint> sprints = projectManagerServiceImpl.showAllSprintForProject(projectTitle);
          model.addAttribute("sprints",sprints);
          return "projectmanager/deleteSprint";
      }
      
      @RequestMapping(value = {"/pm/deleteSprint"},method = RequestMethod.POST)
      public @ResponseBody String deleteProject(@Valid @ModelAttribute("sprint") String sprintTitle,BindingResult result){
          if(projectTitle==null){
              return "redirect:/projectmanager/error";
          } 
          
          if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
           projectManagerServiceImpl.deleteSprint(sprintTitle, projectTitle);
          
          return "Sprint "+sprintTitle+" deleted!";
      }
      
      @RequestMapping(value = {"/pm/finishSprint"}, method = RequestMethod.GET)
      public String showFinishSprintForm(Model model){
          if(projectTitle==null){
              return "projectmanager/error";
          } 
          List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
        
          model.addAttribute("sprints", sprints);
          return "projectmanager/finishSprint";
      }
    
      @RequestMapping(value = {"/pm/finishSprint"}, method = RequestMethod.POST)
      public @ResponseBody String finishSprint(@Valid @ModelAttribute("sprintTitle") String sprintTitle,BindingResult result){
          List<Sprint> sprints = projectManagerServiceImpl.findSprintsByTitle(sprintTitle, projectTitle);
          List<Sprint> notFinishedSprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          List<Task> notFinishedTasks = projectManagerServiceImpl.showAllNotFinishedTasks();
          long curTime = System.currentTimeMillis(); 
          String curStringDate = new SimpleDateFormat("MM/dd/yyyy").format(curTime); 

          if(projectTitle==null){
              return "redirect:/projectmanager/error";
          }
           if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
         if(sprints.get(0).getBeginDate().getTime()> curTime){
             return "Begin date can not be later then finish date!";
         }
         for(int i=0; i<notFinishedSprints.size();i++){
             if(sprints.get(0).getStage()>notFinishedSprints.get(i).getStage()){
                 return "Sprint before this not finished yet";
             }
         }
         if(!notFinishedTasks.isEmpty()){
             return "Some tasks for this Sprint dont finished";
         }
         
           
          projectManagerServiceImpl.finishSprint(sprintTitle, projectTitle);

          return "Sprint finished in "+curStringDate;
      }
      @RequestMapping(value ={"/pm/finishProject"},method = {RequestMethod.GET})
      public String finishProject(Model model){
          if(projectTitle==null){
              return "projectmanager/error";
          }
          String message;
          List<Sprint> notFinishedSprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          if(notFinishedSprints.isEmpty()){
            message="Project finished!";
            model.addAttribute("message", message);
            projectManagerServiceImpl.finishProject(projectTitle);
          }else{
              message ="Some Sprints not finished! You can not Finished Project";
              model.addAttribute("message", message);
          }
          return "projectmanager/finishProject";
      }
      
       @RequestMapping(value = {"/pm/createTask"},method = RequestMethod.GET)
        public String showCreateTaskForm(Model model){
            if(projectTitle==null){
            return "projectmanager/error";
             }
            List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
            model.addAttribute("sprints", sprints);
            return "projectmanager/createTask";
        }
        
        @RequestMapping(value ={"/pm/createTask"},method = RequestMethod.POST)
        public @ResponseBody String createTask(@Valid @ModelAttribute("task") Task task, BindingResult result){
          if(projectTitle==null){
              return "redirect:/projectmanager/error";
          }
          List<Task> tasks = projectManagerServiceImpl.showAllTasksForSprint(task.getSprint());
          List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          Sprint sprint =  null;
         
          for(int i=0;i<sprints.size();i++){
              if(sprints.get(i).getTitle().equals(task.getSprint())){
                  sprint = sprints.get(i);
              }
          }
           
          if(task.getBeginDate().getTime()<sprint.getBeginDate().getTime()){
              return "Task can not start earlier than Sprint start("+sprint.getBeginDate()+")";
          }  
         
          if(task.getBeginDate()==null || task.getEndDate()==null){
              return "Empty or Wrong Date";
          }
          if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
          if(task.getBeginDate().getTime()>task.getEndDate().getTime()){
              return "BeginDate can not be later than endDate";
          }
            for(int i=0;i<tasks.size();i++){
             if(task.getTitle().equals(tasks.get(i).getTitle())){
                 return "Task with this Title exist for this Sprint";
             }
            }
           
            projectManagerServiceImpl.createTask(task);
           return "Task created "+task.getTitle();
        }
        
      @RequestMapping(value = {"/pm/updateTask"},method = RequestMethod.GET)
      public String showUpdateTaskForm(Model model){
         if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          model.addAttribute("sprints", sprints);
          return "projectmanager/updateTask";
      }
      
     @RequestMapping(value = {"/pm/updateTask"},method = RequestMethod.POST)
      public String findTaskForUpdate(@Valid @ModelAttribute("taskTitle") String taskTitle,@Valid @ModelAttribute("sprint") String sprint,
              BindingResult result,Model model){
          if(projectTitle==null){
              return "projectmanager/error";
          }
          Task task=null;
          List<Task> tasks = projectManagerServiceImpl.showAllTasksForSprint(sprint);
          SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
          List<String> beginDates = new ArrayList<>();
          List<String> endDates = new ArrayList<>();
            
          for (int i=0;i<tasks.size();i++){
                beginDates.add(sdf.format(tasks.get(i).getBeginDate()));
            }
          for (int i=0;i<tasks.size();i++){
                endDates.add(sdf.format(tasks.get(i).getEndDate()));
            }
          for(int i=0;i<tasks.size();i++){
              if(taskTitle.equals(tasks.get(i).getTitle())){
                  task = tasks.get(i);
              }
          }
            
          if(task !=null){
          model.addAttribute("task", task);
          model.addAttribute("begindates", beginDates);
          model.addAttribute("enddates", endDates);
          model.addAttribute("t_id", task.getId());
          model.addAttribute("sprint", sprint);
          }
          return "projectmanager/updateTaskForm";
          
      }
     
     
      @RequestMapping(value = {"/pm/updateTaskForm"},method = RequestMethod.POST)
      public @ResponseBody String updateTask(@Valid @ModelAttribute("task") Task task, BindingResult result){
          List<Task> tasks = projectManagerServiceImpl.showAllTasksForSprint(task.getSprint());
          List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          Sprint sprint =  null;
          
           if(projectTitle==null){
              return "redirect:/projectmanager/error";
          }
         
          for(int i=0;i<sprints.size();i++){
              if(sprints.get(i).getTitle().equals(task.getSprint())){
                  sprint = sprints.get(i);
              }
          }
           
          if(task.getBeginDate().getTime()<sprint.getBeginDate().getTime()){
              return "Task can not start earlier than Sprint start("+sprint.getBeginDate()+")";
          }  
          if(task.getBeginDate()==null || task.getEndDate()==null){
              return "Empty or Wrong Date(Format of Data: MM/dd/yyyy)";
          }
         
          if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
          
          if(task.getBeginDate().getTime() >= task.getEndDate().getTime()){
              return "BeginDate can not be later than endDate";
          }
           
            for(int i=0;i<tasks.size();i++){
                if(tasks.get(i).getId()==task.getId()){
                tasks.remove(i);
               }
            }
           
             for(int i =0;i<tasks.size();i++){
               if(task.getTitle().equals(tasks.get(i).getTitle()) ){
                   return "Sprint with this Title exist for this Project!";
               }
          }
             
          return "Task "+" update!";
      }
      
      @RequestMapping(value = {"/pm/findTask"}, method = RequestMethod.GET)
      public String showFindTaskForm(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          model.addAttribute("sprints", sprints);
          return "projectmanager/findTask";
      }
      
      @RequestMapping(value = {"/pm/findTask"}, method = RequestMethod.POST)
      public String findTask(@Valid @ModelAttribute("sprintTitle") String sprintTitle,@Valid @ModelAttribute("taskTitle") String taskTitle,
              BindingResult result,Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Task> tasks = projectManagerServiceImpl.findTask(sprintTitle, taskTitle);
          model.addAttribute("tasks",tasks);
          return "projectmanager/ListTasks";
      }
      
       @RequestMapping(value = {"/pm/deleteTask"}, method = {RequestMethod.GET})
      public String showDeleteTaskForm(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Sprint> sprints = projectManagerServiceImpl.showAllSprintForProject(projectTitle);
          model.addAttribute("sprints",sprints);
          return "projectmanager/deleteTask";
      }
      
      @RequestMapping(value = {"/pm/deleteTask"},method = RequestMethod.POST)
      public @ResponseBody String deleteTask(@Valid @ModelAttribute("sprintTitle") String sprintTitle,@Valid @ModelAttribute("taskTitle") String taskTitle,
              BindingResult result){
          if(projectTitle==null){
              return "redirect:/projectmanager/error";
          } 
          
          if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
           projectManagerServiceImpl.deleteTask(sprintTitle, taskTitle);
          
           
          return "Sprint "+taskTitle+" deleted!";
      }
     
      @RequestMapping(value = {"/pm/showAllTasks"}, method = RequestMethod.GET)
        public String showAllTasks(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Task> tasks = projectManagerServiceImpl.showAllTasks();
          model.addAttribute("tasks", tasks);
          return "projectmanager/ListTasks";
      }
      
       @RequestMapping(value = {"/pm/showAllFinishedTasks"}, method = RequestMethod.GET)
        public String showAllFinishedTasks(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Task> tasks = projectManagerServiceImpl.showAllFinishedTasks();
          model.addAttribute("tasks", tasks);
          return "projectmanager/ListTasks";
      }
      
       @RequestMapping(value = {"/pm/showAllNotFinishedTasks"}, method = RequestMethod.GET)
        public String showAllNotFinishedTasks(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Task> tasks = projectManagerServiceImpl.showAllNotFinishedTasks();
          model.addAttribute("tasks", tasks);
          return "projectmanager/ListTasks";
      }
        
        @RequestMapping(value = {"/pm/finishTask"}, method = RequestMethod.GET)
      public String showFinishTaskForm(Model model){
          if(projectTitle==null){
              return "projectmanager/error";
          } 
          List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          model.addAttribute("sprints", sprints);
          return "projectmanager/finishTask";
      }
    
      @RequestMapping(value = {"/pm/finishTask"}, method = RequestMethod.POST)
      public @ResponseBody String finishTask(@Valid @ModelAttribute("taskTitle") String taskTitle,@Valid @ModelAttribute("sprintTitle") String sprintTitle,
              BindingResult result){
          List<Task> tasks = projectManagerServiceImpl.showAllTasksForSprint(sprintTitle);
          List<Task> task = projectManagerServiceImpl.findTask(sprintTitle, taskTitle);
          List<Task> finishedTask =projectManagerServiceImpl.showAllFinishedTasks();
          long curTime = System.currentTimeMillis(); 
          String curStringDate = new SimpleDateFormat("MM/dd/yyyy").format(curTime); 

          if(projectTitle==null){
              return "redirect:/projectmanager/error";
          }
           if (result.hasErrors()){
              String error = result.getFieldError().getDefaultMessage();
              return error;
          }
         if(tasks.get(0).getBeginDate().getTime()> curTime){
             return "Begin date can not be later then finish date!";
         }
        if (task.isEmpty()){
            return "Task for this Sprint dont exist!";
        }
        for(int i=0;i<finishedTask.size();i++){
            if(finishedTask.get(i).getTitle().equals(task.get(0).getTitle())){
                return "This task is finished yet!";
            }
        }
        projectManagerServiceImpl.finishTask(taskTitle, sprintTitle);

          return "Task finished in "+curStringDate;
      }
      
      @RequestMapping(value = {"/pm/appointTask"},method = RequestMethod.GET)
      public String showAppointTaskForm(Model model){
          if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Users> employees = projectManagerServiceImpl.showAllEmployees();
          List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          model.addAttribute("sprints", sprints);
          model.addAttribute("employees", employees);
          
          return "projectmanager/appointTask";
      }
      
      @RequestMapping(value={"/pm/appointTask"},method = RequestMethod.POST)
      public @ResponseBody String appointTask(@Valid @ModelAttribute("taskTitle") String taskTitle,@Valid @ModelAttribute("employeeLogin") String employeeLogin,
              @Valid @ModelAttribute("sprintTitle") String sprintTitle,BindingResult result){
           List<Task> task = projectManagerServiceImpl.findTask(sprintTitle, taskTitle);
            if (task.isEmpty()){
            return "Task for this Sprint dont exist!";
        }
           List<Task> finishedTask =projectManagerServiceImpl.showAllFinishedTasks();
           List<Job> job = projectManagerServiceImpl.showAllJob();
           
           for(int i=0;i<job.size();i++){
               if(job.get(i).getUser_id() == projectManagerServiceImpl.findUserId(employeeLogin)&&
               job.get(i).getTask_id()==projectManagerServiceImpl.findTaskId(taskTitle) &&
               job.get(i).getSprint_id() ==projectManagerServiceImpl.findProjectId(projectTitle)){
                   return "This user work with this task already";
               }
           }
           
           
          if(projectTitle==null){
              return "redirect:/projectmanager/error";
          }
         
           for(int i=0;i<finishedTask.size();i++){
            if(finishedTask.get(i).getTitle().equals(task.get(0).getTitle())){
                return "This task is finished yet.Its dont need employee!";
            }
        }
          projectManagerServiceImpl.appointTask(taskTitle, employeeLogin,sprintTitle);
          return "Task: "+taskTitle+ "appoint to User: "+employeeLogin;
      }
      
       @RequestMapping(value = {"/pm/enableTask"},method = RequestMethod.GET)
      public String showEnableTaskForm(Model model){
          if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Users> employees = projectManagerServiceImpl.showAllEmployees();
          List<Sprint> sprints = projectManagerServiceImpl.showAllNotFinishedSprints(projectTitle);
          model.addAttribute("sprints", sprints);
          model.addAttribute("employees", employees);
          
          return "projectmanager/enableTask";
      }
      
      @RequestMapping(value={"/pm/enableTask"},method = RequestMethod.POST)
      public @ResponseBody String enableTask(@Valid @ModelAttribute("taskTitle") String taskTitle,@Valid @ModelAttribute("employeeLogin") String employeeLogin,
              @Valid @ModelAttribute("sprintTitle") String sprintTitle,BindingResult result){
           List<Task> task = projectManagerServiceImpl.findTask(sprintTitle, taskTitle);
            if (task.isEmpty()){
            return "Task for this Sprint dont exist!";
           }
           List<Job> job = projectManagerServiceImpl.showAllJob();
           
           for(int i=0;i<job.size();i++){
               if(job.get(i).getUser_id() == projectManagerServiceImpl.findUserId(employeeLogin)&&
               job.get(i).getTask_id()==projectManagerServiceImpl.findTaskId(taskTitle) &&
               job.get(i).getSprint_id() ==projectManagerServiceImpl.findProjectId(projectTitle)){
                    projectManagerServiceImpl.appointTask(taskTitle, employeeLogin,sprintTitle);
                    return "Task: "+taskTitle+ "enable from User: "+employeeLogin;
               }
           }
           return "This user dont work for this task";
      }
      
      @RequestMapping(value = {"/pm/showAllNotAcceptTask"}, method = RequestMethod.GET)
        public String showAllNotAcceptTask(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Task> tasks = projectManagerServiceImpl.showAllNotAcceptTask();
          model.addAttribute("tasks", tasks);
          return "projectmanager/ListTasks";
      }
        
        @RequestMapping(value = {"/pm/showAllAcceptTask"}, method = RequestMethod.GET)
        public String showAllAcceptTask(Model model){
           if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Task> tasks = projectManagerServiceImpl.showAllAcceptTask();
          model.addAttribute("tasks", tasks);
          return "projectmanager/ListTasks";
      }
        
        @RequestMapping(value = {"/pm/showAllJob"},method = RequestMethod.GET)
        public String showAlljob(Model model){
            if(projectTitle==null){
              return "projectmanager/error";
          }
          List<Job> jobs = projectManagerServiceImpl.showAllJobWithValue();
          model.addAttribute("jobs", jobs);
          return "projectmanager/ListJob";
        }
      
    
    @Autowired
    public void setPMService(ProjectManagerServiceImpl projectManagerServiceImpl){
            Locale.setDefault(Locale.ENGLISH);
            this.projectManagerServiceImpl = projectManagerServiceImpl;
    }
   
}
