<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modification d'un type de vehicule</title>
</head>
<body>
<% 	VehiculeType vehicule = (VehiculeType)request.getAttribute("vehicule") ;%>
	<div class="text-center">
		<h3>Modification d'un type de vehicule</h3>
		<form method="post" action="modifyvehicule">
			<input type="hidden" id="id" name="id" value="<%=vehicule.getId()%>">
			<label for="type" class="form-label">Type : </label>
			<input type="text" id="type" name="type" value="<%=vehicule.getType() %>"><br>
			<button type="submit" class="btn btn-primary">Valider</button>
			<a href="vehicules" class="btn btn-primary">Retour</a>
		</form>
	</div>
</body>
</html>