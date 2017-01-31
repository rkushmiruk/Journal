

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

        var login = $('#login').val();
       
        $.ajax({
        type: "POST",
        url: "/Journal/admin/updateUser",
        data: {
            login : login
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
        <h1>Update User :</h1>
        <table>
             <tr><td>Enter login(Surname + Name without space) : </td><td> <input type="text" id="login"><br/></td></tr>
            <tr><td colspan="2"><input type="button" value="Find user for update" onclick="doAjaxPost()"><br/></td></tr>
            <tr><td colspan="2"> <input type ="button" name ="Back->" value ="Back->" onClick ="history.back()"><br/></td></tr>
           <tr><td colspan="2"><div id="info" style="color: green;"></div></td></tr>
        </table>
       
    </body>
</html>