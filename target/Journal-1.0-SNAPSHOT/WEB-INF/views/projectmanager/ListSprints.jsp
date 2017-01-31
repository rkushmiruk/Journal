<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <c:choose>
            <c:when test="${not empty sprints}">
                  <table>
                <thead>
                    <tr>
                        <th>Id </th>
                        <th>Title</th>
                        <th>Begin Date</th>
                        <th>End Date</th>
                        <th>Finish Date</th>
                        <th>Description</th>
                        <th>Stage</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items = "${sprints}" var="sprint">
                        <tr>
                            <th>${sprint.id}</th>
                            <th>${sprint.title}</th>
                            <th>${sprint.beginDate}</th>
                            <th>${sprint.endDate}</th>
                            <th>${sprint.finishDate}</th>
                            <th>${sprint.description}</th>
                            <th>${sprint.stage}</th>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </c:when>
            <c:otherwise>
                <p>Sprint does not exist!</p>
            </c:otherwise>
        </c:choose>
      
    </body>
</html>
