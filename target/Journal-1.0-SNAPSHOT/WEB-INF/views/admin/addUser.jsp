<%@ page language="java" contentType="text/html;"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
        <script type="text/javascript">
        function doAjaxPost() {
   
        var name = $('#name').val();
        var surname = $('#surname').val();
        var pass = $('#pass').val();
        var email = $('#email').val();
        var phone = $('#phone').val();
        var post = $('#post').val();
        
        $.ajax({
        type: "POST",
        url: "/Journal/admin/addUser",
        data: {
            name : name,
            surname : surname,
            pass : pass,
            email : email,
            phone : phone,
            post: post
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
        <h1>Add User :</h1>
        <table>
            <tr><td>Enter your name : </td><td> <input type="text" id="name"><br/></td></tr>
            <tr><td>Surname : </td><td> <input type="text" id="surname"><br/></td></tr>
            <tr><td>Pass :  </td><td> <input type="text" id="pass"><br/></td></tr>
            <tr><td>Email : </td><td> <input type="email" id="email"><br/></td></tr>
            <tr><td>Phone : </td><td> <input type="text" id="phone"><br/></td></tr>
            <tr><td>Post : </td><td><select id="post">
                        <option>Employee</option>
                        <option>Customer</option>
                        <option>Admin</option>
            </select><br/></td></tr>
            <tr><td colspan="2"><input type="button" value="Add User" onclick="doAjaxPost()"><br/></td></tr>
            <tr><td colspan="2"><div id="info" style="color: green;"></div></td></tr>
            <tr><td colspan="2"> <input type ="button" name ="Back->" value ="Back->" onClick ="history.back()"><br/></td></tr>
        </table>
       
    </body>
</html>