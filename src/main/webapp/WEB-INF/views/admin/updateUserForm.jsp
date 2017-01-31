

<%@ page language="java" contentType="text/html;"
pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
        <script type="text/javascript">
        function doAjax() {
        // get the form values
        var id =${u_id};
        var name = $('#name').val();
        var surname = $('#surname').val();
        var pass = $('#pass').val();
        var email = $('#email').val();
        var phone = $('#phone').val();
        
        $.ajax({
        type: "POST",
        url: "/Journal/admin/updateUserForm",
        data: {
            id:id,
            name : name,
            surname : surname,
            pass : pass,
            email : email,
            phone : phone
        },
        success: function(response){
        $('#info').html(response);
        },
        error: function(e){
        console.log(e);
        }
        });
        }
        </script>
    </head>
    <body>
        <c:choose>
            <c:when test="${not empty users}">
        <table>
            <tr><td>Enter your name : </td><td> <input type="text" id="name" value="${users.get(0).name}"><br/></td></tr>
            <tr><td>Surname : </td><td> <input type="text" id="surname" value="${users.get(0).surname}"><br/></td></tr>
            <tr><td>Pass :  </td><td> <input type="text" id="pass" value="${users.get(0).pass}"><br/></td></tr>
            <tr><td>Email : </td><td> <input type="email" id="email" value="${users.get(0).email}"><br/></td></tr>
            <tr><td>Phone : </td><td> <input type="text" id="phone" value="${users.get(0).phone}"><br/></td></tr>
            
            <tr><td colspan="2"><input type="button" value="Update User" onclick="doAjax()"><br/></td></tr>
            <tr><td colspan="2"><div id="info"></div></td></tr>
            <tr><td colspan="2"> 
        </table>
                </c:when>
            <c:otherwise>
                <p>User does not exist!</p>
            </c:otherwise>
        </c:choose>
             
    </body>
</html>