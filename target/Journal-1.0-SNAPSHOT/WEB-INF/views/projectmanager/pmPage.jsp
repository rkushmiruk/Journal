<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<jsp:directive.page contentType="text/html;charset=UTF-8"/> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

 <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            
        function doAjaxPost() {
        // get the form values
        var title = $('#title').val();
        $.ajax({
        type: "POST",
        url: "/Journal/pm",
        data: {
            title : title
        },
        success: function(response){
        window.location = "/Journal/pm/pmForm";
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
    <h2>Welcome : ${pmName}</h2>
<table>
<tr><td>Select Project for  : </td><td><select id="title">
                        <c:forEach items="${pmProjects}" var="project">
                        <option>${project.title}</option>
                        </c:forEach>
            </select>
          <tr><td colspan="2"><input type="button" value="Edit project" onclick="doAjaxPost()" onclick=""><br/></td></tr>
</table>    
</body>
</html> 