

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<jsp:directive.page contentType="text/html;charset=UTF-8"/> 


<html>
<head>
<title>${title}</title>

</head>
<body>
    <jsp:include page="../_menu.jsp" />
    <link href="${pageContext.request.contextPath}/resources//Form.css" rel="stylesheet">
    <h2>Welcome : ${pageContext.request.userPrincipal.name}</h2>

<table>
    <tr>
        <td><p>Create :</p></td>
        <td><input type ="button" name ="addUser" value ="addUser" onClick ="location.href='/Journal/admin/addUser'"></td>
        <td><input type ="button" name ="addProject" value ="addProject" onClick ="location.href='/Journal/admin/addProject'"></td>
    </tr> 
    <tr>
        <td><p>Search :</p></td>
        <td><input type ="button" name ="findUserBySurname" value ="findUserBySurname" onClick ="location.href='/Journal/admin/findUserBySurname'"></td>
        <td><input type ="button" name ="findUserByLogin" value ="findUserByLogin" onClick ="location.href='/Journal/admin/findUserByLogin'"></td>
        <td><input type ="button" name ="findUserByPost" value ="findUserByPost" onClick ="location.href='/Journal/admin/findUserByPost'"></td>
        <td><input type ="button" name ="findProjectByTitle" value ="findProjectByTitle" onClick ="location.href='/Journal/admin/findProjectByTitle'"></td>
        <td><input type ="button" name ="findProjectByCustomer" value ="findProjectByCustomer" onClick ="location.href='/Journal/admin/findProjectByCustomer'"></td>
        <td><input type ="button" name ="findProjectByPM" value ="findProjectByPM" onClick ="location.href='/Journal/admin/findProjectByPM'"></td>
    </tr>
    <tr>
        <td><p>Update :</p></td>
        <td><input type ="button" name ="appointPM" value ="appointPM" onClick ="location.href='/Journal/admin/appointPM'"></td>
        <td><input type ="button" name ="enablePM" value ="enablePM" onClick ="location.href='/Journal/admin/enablePM'"></td>
        <td><input type ="button" name ="updateUser" value ="updateUser" onClick ="location.href='/Journal/admin/updateUser'"></td>
        <td><input type ="button" name ="updateProject" value ="updateProject" onClick ="location.href='/Journal/admin/updateProject'"></td>
    </tr>
    <tr>
        <td><p>Delete :</p></td>
        <td><input type ="button" name ="deleteUser" value ="deleteUser" onClick ="location.href='/Journal/admin/deleteUser'"></td>
        <td><input type ="button" name ="deleteProject" value ="deleteProject" onClick ="location.href='/Journal/admin/deleteProject'"></td>
    </tr>
    <tr>
        <td>Show :</td>
        <td><input type ="button" name ="showAllUsers" value ="showAllUsers" onClick ="location.href='/Journal/admin/showAllUsers'"></td>
        <td><input type ="button" name ="showAllEmployee" value ="showAllEmployee" onClick ="location.href='/Journal/admin/showAllEmployee'"></td>
        <td><input type ="button" name ="showAllCustomer" value ="showAllCustomer" onClick ="location.href='/Journal/admin/showAllCustomer'"></td>
        <td><input type ="button" name ="showAllPM" value ="showAllPM" onClick ="location.href='/Journal/admin/showAllPM'"></td>
        <td><input type ="button" name ="showAllProject" value ="showAllProject" onClick ="location.href='/Journal/admin/showAllProject'"></td>
        <td><input type ="button" name ="showAllFinishedProject" value ="showAllFinishedProject" onClick ="location.href='/Journal/admin/showAllFinishedProject'"></td>
        <td><input type ="button" name ="showAllNotFinishedProject" value ="showAllNotFinishedProject" onClick ="location.href='/Journal/admin/showAllNotFinishedProject'"></td>
    </tr>
   
</table>
</body>
</html> 