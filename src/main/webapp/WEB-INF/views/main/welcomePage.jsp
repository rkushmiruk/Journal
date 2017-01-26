<%-- 
    Document   : welcomePage
    Created on : Jan 15, 2017, 3:50:34 PM
    Author     : Roman
--%>

<%@page session="false"%>
<html>
<head>
<title>${title}</title>
</head>
<body>
    <jsp:include page="../_menu.jsp" />
 
 
    <h1>Message : ${message}</h1>
</body>
</html>