<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true" %>
    <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Page d'accueil administrateur</title>
</head>
<body>
<div class="text-center">
	<a href="<%=str%>/users" class="btn btn-primary btn-lg">Gérer les comptes utilisateurs</a>
	<a href="<%=str%>/infractions" class="btn btn-primary btn-lg">Gérer les types d'infractions</a>
	<a href="<%=str%>/vehicules" class="btn btn-primary btn-lg">Gérer les types de véhicules</a>
</div>

</body>
</html>