

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<jsp:directive.page contentType="text/html;charset=UTF-8"/> 


<html>
<head>
<script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
        <script type="text/javascript">
        function doAjax() {
        // get the form values
        var project = $('#project').val();
        $.ajax({
        type: "POST",
        url: "/Journal/customer",
        data: {
            project : project
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
    <jsp:include page="../_menu.jsp" />
    <link href="${pageContext.request.contextPath}/resources//Form.css" rel="stylesheet">
    <h2>Welcome : ${pageContext.request.userPrincipal.name}</h2>
        <table>
            <tr><td>Select Project:</td><td><select id="project">
                  <c:forEach items="${projects}" var="project">
                  <option>${project.title}</option>
                   </c:forEach>
            </select><br/></td></tr>
            <tr><td colspan="2"> <input type="button" value="Show Project result" onclick="doAjax()"><br/></td></tr>
           <tr><td colspan="2"><div id="info" ></div></td></tr>
        </table>
</body>
</html> 