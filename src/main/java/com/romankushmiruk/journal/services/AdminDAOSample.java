/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romankushmiruk.journal.services;

import com.romankushmiruk.journal.model.Contact;
import com.romankushmiruk.journal.model.Customer;
import com.romankushmiruk.journal.model.Employee;
import com.romankushmiruk.journal.model.Post;
import com.romankushmiruk.journal.model.Project;
import com.romankushmiruk.journal.model.Users;
import java.util.Arrays;
import java.util.Date;

import java.util.List;
import java.util.Locale;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author Roman
 */

public class AdminDAOSample {
    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("dataSource.xml");
        ctx.refresh();
        
        AdminServiceImpl admindao = ctx.getBean("AdminServiceImpl",AdminServiceImpl.class);
        
        Locale.setDefault(Locale.ENGLISH);
        
       
        
        Employee employee = new Employee("Tsymbal", "Denis", Post.Employee, "Lolipopssss");
        Customer customer = new Customer("Net", "Ivan");
        Contact contact = new Contact("Ramon_2@meta.ua",null);
        
        
        List<Users> customers = admindao.showAllUsers();
       
       for (Users c : customers){
           System.out.println(c.getName());
       }
////        
////        Date date = new Date();
////        System.out.println(date);
//            
      
//        admindao.enablePM("Project-1");
        
//         List<Post> posts = Arrays.asList(Post.values());
//        
//       for(int i=0; i<posts.size();i++){
//           System.out.println(posts.get(i));
//       }
    
      admindao.updateUser(customers.get(1));
    
}
}
