<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Création d'un utilisateur</title>
</head>
<body>
<div class="text-center">
		<form method="post" action="adduser">
		<h3>Création d'un utilisateur</h3>
			<label for="userType-select" class="form-label">Type d'utilisateur : </label>
			<select id="userType-select" name ="userType">
				<option value="collector">Percepteur d'amendes</option>
				<option value="chief">Chef de bridage</option>
				<option value="policeman">Policier</option>
			</select><br>
			<button type="submit" class="btn btn-primary">Valider</button>
			<a href="users" class="btn btn-primary">Retour</a>
		</form>
	</div>

</body>
</html>