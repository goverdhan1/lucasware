<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Admin</title>
</head>
<body>
<h1>
	Welcome Admin
</h1>

<P>  ${message}. </P>

	<table border="1">
		<tr>
			<th>User id</th>
			<th>First name</th>
		</tr>
		<c:forEach items="${adminUsersList}" var="adminUser">    
		  <tr>
		    <td>${adminUser.userId}</td>
		    <td>${adminUser.firstName}</td> 
		  </tr>		 
		</c:forEach>			
	</table>
</body> 

</html>
