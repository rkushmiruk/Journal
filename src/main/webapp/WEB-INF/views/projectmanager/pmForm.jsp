

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
    <h2>Project is : ${projectTitle} ${isFinished}</h2>
    <input type ="button" value ="selectAnotherProject" onClick ="location.href='/Journal/pm'">
    <p><p>

<table>
    <tr>
        <td><p>Create :</p></td>
        <td><input  type ="button" name ="createSprint" value ="createSprint" onClick ="location.href='/Journal/pm/createSprint'" ${disabled}></td>
        <td><input  type ="button" name ="createTask" value ="createTask" onClick ="location.href='/Journal/pm/createTask'" ${disabled}></td>
    </tr> 
    <tr>
        <td><p>Search :</p></td>
         <td><input type ="button" name ="findSprintByTitle" value ="findSprintByTitle" onClick ="location.href='/Journal/pm/findSprintByTitle'"></td>
         <td><input type ="button" name ="findTask" value ="findTask" onClick ="location.href='/Journal/pm/findTask'"></td>
    </tr>
    <tr>
        <td><p>Update :</p></td>
        <td><input type ="button" name ="updateSprint" value ="updateSprint" onClick ="location.href='/Journal/pm/updateSprint'"  ${disabled}></td>
        <td><input type ="button" name ="updateTask" value ="updateTask" onClick ="location.href='/Journal/pm/updateTask'"  ${disabled}></td>
        <td><input type ="button" name ="finishSprint" value ="finishSprint" onClick ="location.href='/Journal/pm/finishSprint'"  ${disabled}></td>
       
    </tr>
    <tr>
        <td><p>Delete :</p></td>
        <td><input type ="button" name ="deleteSprint" value ="deleteSprint" onClick ="location.href='/Journal/pm/deleteSprint'"  ${disabled}></td>
        <td><input type ="button" name ="deleteTask" value ="deleteTask" onClick ="location.href='/Journal/pm/deleteTask'"  ${disabled}></td>
    </tr>
    <tr>
        <td>Show :</td>
        <td><input type ="button" name ="showAllSprints" value ="showAllSprints" onClick ="location.href='/Journal/pm/showAllSprints'"></td>
        <td><input type ="button" name ="showAllFinishedSprints" value ="showAllFinishedSprints" onClick ="location.href='/Journal/pm/showAllFinishedSprints'"></td>
        <td><input type ="button" name ="showAllNotFinishedSprints" value ="showAllNotFinishedSprints" onClick ="location.href='/Journal/pm/showAllNotFinishedSprints'"  ${disabled}></td>
        <td><input type ="button" name ="showAllTasks" value ="showAllTasks" onClick ="location.href='/Journal/pm/showAllTasks'"></td>
        <td><input type ="button" name ="showAllFinishedTasks" value ="showAllFinishedTasks" onClick ="location.href='/Journal/pm/showAllFinishedTasks'"></td>
        <td><input type ="button" name ="showAllNotFinishedTasks" value ="showAllNotFinishedTasks" onClick ="location.href='/Journal/pm/showAllNotFinishedTasks'"  ${disabled}></td>
    </tr>
    <tr>
        <td>Manipulate with Task:</td>
        <td><input type ="button" name ="appointTask" value ="appointTask" onClick ="location.href='/Journal/pm/appointTask'"  ${disabled}></td>
        <td><input type ="button" name ="enableTask" value ="enableTask" onClick ="location.href='/Journal/pm/enableTask'"  ${disabled}></td>
        <td><input type ="button" name ="showAllJob" value ="showAllJob" onClick ="location.href='/Journal/pm/showAllJob'"></td>
        <td><input type ="button" name ="showAllAcceptTasks" value ="showAllAcceptTasks" onClick ="location.href='/Journal/pm/showAllAcceptTask'"></td>
        <td><input type ="button" name ="showAllNotAcceptTasks" value ="showAllNotAcceptTasks" onClick ="location.href='/Journal/pm/showAllNotAcceptTask'"></td>
        <td></td>
    </tr>
</table>
     <p></p>
    <input type ="button" value ="finishProject" onClick ="location.href='/Journal/pm/finishProject'" ${disabled}>
</body>
</html> 