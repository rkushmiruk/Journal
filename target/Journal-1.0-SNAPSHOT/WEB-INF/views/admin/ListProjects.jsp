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
            <c:when test="${not empty projects}">
                  <table>
                <thead>
                    <tr>
                        <th>Id </th>
                        <th>Title</th>
                        <th>Manager</th>
                        <th>Begin Date</th>
                        <th>End Date</th>
                        <th>Finish Date</th>
                        <th>Customer</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items = "${projects}" var="project">
                        <tr>
                            <th>${project.id}</th>
                            <th>${project.title}</th>
                            <th>${project.manager}</th>
                            <th>${project.beginDate}</th>
                            <th>${project.endDate}</th>
                            <th>${project.finishDate}</th>
                            <th>${project.customer}</th>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </c:when>
            <c:otherwise>
                <p>Project does not exist!</p>
            </c:otherwise>
        </c:choose>
    </body>
</html>
