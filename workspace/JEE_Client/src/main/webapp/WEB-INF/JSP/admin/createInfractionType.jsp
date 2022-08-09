<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Création d'un nouveau type d'infraction</title>
</head>
<body>
	<div class="text-center">
	<h3>Création d'un nouveau type d'infraction</h3>
		<form method="post" action="addinfraction">
			<label for="type" class="form-label">Entrer le type d'infraction à ajouter :</label><br>
			<input type="text" id="type" name="type"><br>
			<label for="amount" class="form-label">Montant de l'infraction:</label><br>
			<input type="number" min="0.00" max="10000.00" step="0.01" id="amount" name="amount"><br>
			<button type="submit" class="btn btn-primary">Valider</button>
			<a href="infractions" class="btn btn-primary">Retour</a>
		</form>
	</div>
</body>
</html>