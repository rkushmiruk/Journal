

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="border: 1px solid #ccc;padding:5px;margin-bottom: 10px;">
    <a href="${pageContext.request.contextPath}/welcome">Home</a>
    | &nbsp;
     <a href="${pageContext.request.contextPath}/employee">Employee</a>
    | &nbsp;
     <a href="${pageContext.request.contextPath}/admin">Admin</a>
    | &nbsp;
     <a href="${pageContext.request.contextPath}/pm">PM</a>
      | &nbsp;
     <a href="${pageContext.request.contextPath}/customer">Customer</a>
     
     
     
     <c:if test="${pageContext.request.userPrincipal.name !=null}">
         | &nbsp;
         <a href="${pageContext.request.contextPath}/logout">Logout</a>
     </c:if>
</div>