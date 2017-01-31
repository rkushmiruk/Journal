<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session = "false" %>
<html>
    <head>
        <title>Access Denied</title>
    </head>
    <body>
        <jsp:include page = "../_menu.jsp" />
        <h3 style ="color : red;">${message}</h3> 
    </body>
</html>