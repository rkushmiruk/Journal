

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
            <c:when test="${not empty jobs}">
                  <table>
                <thead>
                    <tr>
                        <th>User</th>
                        <th>Sprint</th>
                        <th>Task</th>
                        <th>isAccepted</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items = "${jobs}" var="job">
                        <tr>
                            <th>${job.user_login}</th>
                            <th>${job.sprint_title}</th>
                            <th>${job.task_title}</th>
                            <th>${job.isAccept}</th>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </c:when>
            <c:otherwise>
                <p>Jobs does not exist!</p>
            </c:otherwise>
        </c:choose>
      
    </body>
</html>
