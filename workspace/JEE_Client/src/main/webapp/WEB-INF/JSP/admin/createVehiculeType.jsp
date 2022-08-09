<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Création d'un nouveau type de vehicule</title>
</head>
<body>
<% 	VehiculeType vehicule = (VehiculeType)request.getAttribute("vehicule") ;%>
	<div class="text-center">
	<h3>Création d'un nouveau type de vehicule</h3>
		<form method="post" action="addvehicule">
			<label for="type" class="form-label">Entrer le nom du type de véhicule à ajouter :</label><br>
			<input type="text" id="type" name="type"><br>
			<button type="submit" class="btn btn-primary">Valider</button>
			<a href="vehicules" class="btn btn-primary">Retour</a>
		</form>
	</div>
</body>
</html>