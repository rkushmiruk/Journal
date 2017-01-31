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
      
        var projectTitle = $('#projectTitle').val();
       
        $.ajax({
        type: "POST",
        url: "/Journal/admin/updateProject",
        data: {
            projectTitle : projectTitle
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
        <h1>Find Project for Update :</h1>
        <table>
             <tr><td>Enter project title: </td><td> <input type="text" id="projectTitle"><br/></td></tr>
            <tr><td colspan="2"><input type="button" value="Find project" onclick="doAjaxPost()"><br/></td></tr>
            <tr><td colspan="2"> <input type ="button" name ="Back->" value ="Back->" onClick ="history.back()"><br/></td></tr>
           <tr><td colspan="2"><span id="info"></span></td></tr>
        </table>
       
    </body>
</html>