

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${not empty users}">
                  <table>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name </th>
                        <th>Surname</th>
                        <th>Post</th>
                        <th>Login</th>
                        <th>Pass</th>
                        <th>Email</th>
                        <th>Phone</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items = "${users}" var="user">
                        <tr>
                            <th>${user.id}</th>
                            <th>${user.name}</th>
                            <th>${user.surname}</th>
                            <th>${user.post}</th>
                            <th>${user.login}</th>
                            <th>${user.pass}</th>
                            <th>${user.email}</th>
                            <th>${user.phone}</th>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </c:when>
            <c:otherwise>
                <p>User does not exist!</p>
            </c:otherwise>
        </c:choose>
      
    </body>
</html>
