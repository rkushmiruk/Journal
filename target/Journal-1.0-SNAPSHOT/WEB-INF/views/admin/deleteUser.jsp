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
        var user = $('#user').val();
       
        $.ajax({
        type: "POST",
        url: "/Journal/admin/deleteUser",
        data: {
            user : user
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
        <h1>Delete User :</h1>
        <table>
              <tr><td>Users : </td><td><select id="user">
                        <c:forEach items="${users}" var="user">
                        <option>${user.login}</option>
                        </c:forEach>
            </select>
            <tr><td colspan="2"><input type="button" value="Delete User" onclick="doAjaxPost()"><br/></td></tr>
            <tr><td colspan="2"><div id="info" style="color: green;"></div></td></tr>
           <tr><td colspan="2"> <input type ="button" name ="Back->" value ="Back->" onClick ="history.back()"><br/></td></tr>
        </table>
       
    </body>
</html>