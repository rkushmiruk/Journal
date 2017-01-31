
<%@ page language="java" contentType="text/html;"
pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        
        
        <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
        
        <link href="${pageContext.request.contextPath}/resources//calendar/tcal.css" rel="stylesheet">
        <script type="text/javascript" src ="${pageContext.request.contextPath}/resources/calendar/tcal_en.js"></script>
        <script type="text/javascript">
            
        function doAjaxPost() {
        // get the form values
        
        var sprint = $('#sprint').val();
        var title = $('#title').val();
        var beginDate = $('#begin').val();
        var endDate = $('#end').val();
        var description = $('#description').val();
        var estimate = $('#estimate').val();
     
        $.ajax({
        type: "POST",
        url: "/Journal/pm/createTask",
        data: {
            sprint: sprint,
            title : title,
            beginDate : beginDate,
            endDate : endDate,
            description: description,
            estimate: estimate
            
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
        <h1>Add Task :</h1>
        <table>
            <tr><td>Select Sprint for  : </td><td><select id="sprint">
                        <c:forEach items="${sprints}" var="sprint">
                        <option>${sprint.title}</option>
                        </c:forEach>
            </select>
            <tr><td>Enter your title : </td><td> <input type="text" id="title"><br/></td></tr>
            <tr><td>Begin Date : </td><td> <input type="text" name="date" id="begin" class="tcal" value=""><br/></td></tr>
            <tr><td>End Date :  </td><td> <input type="text" name="date" id="end" class="tcal" value=""><br/></td></tr>
            <tr><td>Description : </td><td> <textarea id="description" ></textarea><br/></td></tr>
            <tr><td>Estimate(Number of Hours):  </td><td> <input type="number" min="1" value="1"  id="estimate"><br/></td></tr>
            <tr><td colspan="2"><input type="button" value="Add Task" onclick="doAjaxPost()"><br/></td></tr>
            <tr><td colspan="2"><div id="info" style="color: green;"></div></td></tr>
            <tr><td colspan="2"> <input type ="button" name ="Back->" value ="Back->" onClick ="history.back()"><br/></td></tr>
        </table>
       
    </body>
</html>