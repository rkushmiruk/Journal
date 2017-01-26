/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.validation;

import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.services.AdminServiceImpl;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author Roman
 */
@Component
public class ProjectValidator implements Validator{
  private AdminServiceImpl adminServiceImpl;
    
    @Override
    public boolean supports(Class<?> type) {
         return Project.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
           Project project = (Project) o;
            List<Project> projects = adminServiceImpl.showAllProject();
           if(project.getBeginDate() == null || project.getEndDate() == null){
               errors.rejectValue("beginDate", "Empty or Wrong Date");
                    }
          if(project.getBeginDate().getTime() >= project.getEndDate().getTime()){
              errors.rejectValue("beginDate", "BeginDate can not be later than endDate");
          }
          for(int i =0;i<projects.size();i++){
               if(project.getTitle().equals(projects.get(i).getTitle()) ){
                   errors.rejectValue("title", "Project with this Title exist!");
               }
          }
    }
    
        @Autowired
        public void setAdminService(AdminServiceImpl adminServiceImpl){
            Locale.setDefault(Locale.ENGLISH);
            this.adminServiceImpl = adminServiceImpl;
    }
}

