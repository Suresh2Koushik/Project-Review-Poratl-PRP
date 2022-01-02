<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
 <%
 String task = request.getParameter("review");
 String data = (String) session.getAttribute("users"); 
String row = "task"+task;
 %>
<html>
   <head>
      <title>Project Review Portal</title>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
   <link rel="stylesheet" href="styles1.css">
   </head>

   <body>
      <sql:setDataSource var = "snapshot" driver = "com.mysql.jdbc.Driver"
         url = "jdbc:mysql://localhost:3306/18mis1016"
         user = "root"  password = "root"/>
 
      <sql:query dataSource = "${snapshot}" var = "result">
         SELECT * from submission where idsubmission="<%= row%>";
      </sql:query>
 <nav class="navbar bg-white navbar-expand-lg navbar-light bg-light" id="navs">
  <div class="container-fluid">
    <a class="navbar-brand" href="#"><img width="40" src="https://lms.vit.ac.in/pluginfile.php/1/core_admin/logocompact/300x300/1604370961/1200px-Vellore_Institute_of_Technology_seal_2017.svg.png">&nbsp;&nbsp;VITCC</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item icon">
          <i class="fa fa-bell" aria-hidden="true"></i>
        </li>
        <li class="nav-item icon">
          <i class="fa fa-comment" aria-hidden="true"></i>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"><%=data%>&nbsp;&nbsp;<img width="35" height="35" class="userpicture defaultuserpic" aria-hidden="true" style="border-radius: 17px" src="https://secure.gravatar.com/avatar/d68964ca498622d705a09f7b52330df8?s=35&amp;d=mm"></a>
        </li>
      </ul>
    </div>
  </div>
</nav>
        <center>
      <table class="table">
         <tr>
            <th scope="col" class="task">Submissions</th>
         </tr>
         <table class="table">
         <c:forEach var = "row" items = "${result.rows}">
<tr>
    <form action="readsubdoc" method="POST">
                <input type="hidden" name="subfile" value="${row.idsubmission}">
            <input type="submit" class="btn btn-secondary btn-sm" value="open submission">
            </form>
            </tr>
            <tr>
       <form action="feedback" method="POST">
                <input type="hidden" name="row" value="${row.idsubmission}">
                <input type="hidden" name="id" value="${row.stuid}">
                <input type="text" class="form-control" style="width:20%;" name="mark">
            <input type="submit" class="btn btn-secondary btn-sm" value="Submit mark"><br>
            </form>
    </tr>
            <tr>
                <br><th>Student<th>
                <td>${row.stuname}</td>
            </tr>
            <tr>
                <th>id<th>
                <td>${row.stuid}</td>
            </tr>
            <tr>
                <th>text<th>
                <td>${row.text}</td>
            </tr>
            <tr>
                <th>Marks given<th>
                <td>${row.marks}</td>
            </tr>
         </c:forEach>
         </table>
   </body>
</html>


