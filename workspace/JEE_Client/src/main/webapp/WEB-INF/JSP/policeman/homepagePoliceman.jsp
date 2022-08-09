<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Page d'accueil policier</title>
</head>
<body>
<% if(request.getAttribute("message")!=null){%>
	<div class="alert alert-success" style='text-align: center' role="alert">
				<%= request.getAttribute("message") %>
	</div>
	<% } %>
<div class="text-center">
	<a href="<%=str%>/createfine" class="btn btn-primary btn-lg">Encoder une nouvelle amende</a>
</div>

</body>
</html>