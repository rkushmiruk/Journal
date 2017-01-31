
<%@ page language="java" contentType="text/html;"
pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
        <script type="text/javascript">
        function doAjaxPost() {
        // get the form values
        var project = $('#project').val();
        var employee = $('#employee').val();
       
        $.ajax({
        type: "POST",
        url: "/Journal/admin/appointPM",
        data: {
            project : project,
            employee : employee
        },
        success: function(response){
        $('#info').html(response);
        },
        error: function(e){
        console.log('Error: ' + e);
        }
        });
        }
        </script>
    </head>
    <body>
        <h1>Appoint Project Manager :</h1>
        <table>
              <tr><td>Projects : </td><td><select id="project">
                        <c:forEach items="${projects}" var="project">
                        <option>${project.title}</option>
                        </c:forEach>
            </select><input type ="button" name ="addProject" value ="addNewProject" onClick ="location.href='/Journal/addProject'"><br/></td></tr>
            <tr><td>Employee : </td><td><select id="employee">
                        <c:forEach items="${employees}" var="employee">
                        <option>${employee.login}</option>
                        </c:forEach>
            </select><input type ="button" name ="addEmployee" value ="addNewEmployee" onClick ="location.href='/Journal/addUser'"><br/></td></tr>
            <tr><td colspan="2"><input type="button" value="Appoint Project Manager" onclick="doAjaxPost()"><br/></td></tr>
            <tr><td colspan="2"><div id="info" style="color: green;"></div></td></tr>
           <tr><td colspan="2"> <input type ="button" name ="Back->" value ="Back->" onClick ="history.back()"><br/></td></tr>
        </table>
       
    </body>
</html>