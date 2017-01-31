<%@ page language="java" contentType="text/html;"
pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
       <link href="${pageContext.request.contextPath}/resources//calendar/tcal.css" rel="stylesheet">
        <script type="text/javascript" src ="${pageContext.request.contextPath}/resources/calendar/tcal_en.js"></script>
        <script type="text/javascript">
            
        function doAjax() {
  
        var id =${p_id};
        var title = $('#title').val();
        var beginDate = $('#begin').val();
        var endDate = $('#end').val();
        
        $.ajax({
        type: "POST",
        url: "/Journal/admin/updateProjectForm",
        data: {
            id:id,
            title : title,
            beginDate : beginDate,
            endDate : endDate
        },
        success: function(response){
        $('#info').html(response);
        },
        error: function(e){
        console.log('Error: ' + e);
        }
        });
        }
        </script>
    </head>
    <body>
        <table>
            <c:choose>
                <c:when test="${not empty projects}">
                    <tr><td>Enter your title : </td><td> <input type="text" id="title" value="${projects.get(0).title}"><br/></td></tr>
            <tr><td>Begin Date : </td><td> <input type="text" name="date" id="begin" class="tcal" value="${begindates.get(0)}"><br/></td></tr>
            <tr><td>End Date :  </td><td> <input type="text" name="date" id="end" class="tcal" value="${enddates.get(0)}"><br/></td></tr>
            <tr><td colspan="2"><input type="button" value="Update Project" onclick="doAjax()"><br/></td></tr>
            <tr><td colspan="2"><div id="info" style="color: green;"></div></td></tr>
           <tr><td colspan="2"> 
        </table>
                </c:when>
                <c:otherwise>
                    <p>Project does not exist!</p>
                </c:otherwise>
            </c:choose>
        
    </body>
</html>