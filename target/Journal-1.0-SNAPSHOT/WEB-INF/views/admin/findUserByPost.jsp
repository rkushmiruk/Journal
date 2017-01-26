

<%@ page language="java" contentType="text/html;"
pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        
        <title>Add Users using ajax</title>
        <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
        
       
        <script type="text/javascript">
            
        function doAjaxPost() {
        // get the form values
        var post = $('#post').val();
       
       
        $.ajax({
        type: "POST",
        url: "/Journal/findUserByPost",
        data: {
            
            post : post
            
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
        <h1>Find User :</h1>
        <table>
             <tr><td>Post : </td><td><select id="post">
                        <c:forEach items="${posts}" var="post">
                        <option>${post}</option>
                        </c:forEach>
            </select>
            <tr><td colspan="2"><input type="button" value="Find user" onclick="doAjaxPost()"><br/></td></tr>
            <tr><td colspan="2"> <input type ="button" name ="Back->" value ="Back->" onClick ="history.back()"><br/></td></tr>
           <tr><td colspan="2"><div id="info" style="color: green;"></div></td></tr>
        </table>
       
    </body>
</html>