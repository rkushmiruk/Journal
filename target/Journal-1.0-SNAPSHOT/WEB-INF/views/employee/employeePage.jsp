<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<jsp:directive.page contentType="text/html;charset=UTF-8"/> 

<html>
<head>
</head>
<body>
    <jsp:include page="../_menu.jsp" />
    <link href="${pageContext.request.contextPath}/resources//Form.css" rel="stylesheet">
    <h2>Welcome : ${pageContext.request.userPrincipal.name}</h2>
<table>
    <tr>
      <input type ="button" value ="showListofNominations" onClick ="location.href='/Journal/employee/listOfNominations'" > 
      <input type ="button" value ="AcceptTask" onClick ="location.href='/Journal/employee/acceptTask'" > 
    </tr> 
</table>
</body>
</html> 