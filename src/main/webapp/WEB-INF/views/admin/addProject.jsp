
<%@ page language="java" contentType="text/html;"
pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        
        <title>Add Users using ajax</title>
        <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
        
        <link href="${pageContext.request.contextPath}/resources//calendar/tcal.css" rel="stylesheet">
        <script type="text/javascript" src ="${pageContext.request.contextPath}/resources/calendar/tcal_en.js"></script>
        <script type="text/javascript">
            
        function doAjaxPost() {
        // get the form values
        var title = $('#title').val();
        var beginDate = $('#begin').val();
        var endDate = $('#end').val();
        var customer = $('#customer').val();    
        $.ajax({
        type: "POST",
        url: "/Journal/addProject",
        data: {
            
            title : title,
            beginDate : beginDate,
            endDate : endDate,
            customer :customer
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
        <h1>Add Project :</h1>
        <table>
            <tr><td>Enter your title : </td><td> <input type="text" id="title"><br/></td></tr>
            <tr><td>Begin Date : </td><td> <input type="date" name="date" id="begin" class="tcal" value=""><br/></td></tr>
            <tr><td>Finish Date :  </td><td> <input type="date" name="date" id="end" class="tcal" value=""><br/></td></tr>
            <tr><td>Customer : </td><td><select id="customer">
                        <c:forEach items="${customers}" var="customer">
                        <option>${customer.login}</option>
                        </c:forEach>
            </select><input type ="button" name ="addCustomer" value ="addNewCustomer" onClick ="location.href='/Journal/addUser'"><br/></td></tr>
          
            <tr><td colspan="2"><input type="button" value="Add Project" onclick="doAjaxPost()"><br/></td></tr>
            <tr><td colspan="2"><div id="info" style="color: green;"></div></td></tr>
           <tr><td colspan="2"> <input type ="button" name ="Back->" value ="Back->" onClick ="history.back()"><br/></td></tr>
        </table>
       
    </body>
</html>