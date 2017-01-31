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
            <c:when test="${not empty tasks}">
                  <table>
                <thead>
                    <tr>
                        <th>Id </th>
                        <th>Sprint Id</th>
                        <th>Title</th>
                        <th>Begin Date</th>
                        <th>End Date</th>
                        <th>Finish Date</th>
                        <th>Description</th>
                        <th>Estimate</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items = "${tasks}" var="task">
                        <tr>
                            <th>${task.id}</th>
                            <th>${task.sprint_id}</th>
                            <th>${task.title}</th>
                            <th>${task.beginDate}</th>
                            <th>${task.endDate}</th>
                            <th>${task.finishDate}</th>
                            <th>${task.description}</th>
                            <th>${task.estimate}</th>
                          
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </c:when>
            <c:otherwise>
                <p>Task does not exist!</p>
            </c:otherwise>
        </c:choose>
    </body>
</html>
