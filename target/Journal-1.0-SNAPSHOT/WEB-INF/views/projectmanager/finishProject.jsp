<%@ page language="java" contentType="text/html;"
pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    </head>
    <body>
        <h1>Finish Sprint :</h1>
        <table>
           <tr><td colspan="2"><h1>${message}</h1></td></tr>
           <tr><td colspan="2"> <input type ="button" name ="Back->" value ="Back->" onClick ="history.back()"><br/></td></tr>
        </table>
    </body>
</html>