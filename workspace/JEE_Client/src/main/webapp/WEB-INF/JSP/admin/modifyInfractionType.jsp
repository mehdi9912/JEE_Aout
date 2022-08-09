<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modification d'un type d'infraction</title>
</head>
<body>
<% 	InfractionType infraction = (InfractionType)request.getAttribute("infraction") ;%>
	<div class="text-center">
	<h3>Modification d'un type d'infraction</h3>
		<form method="post" action="modifyinfraction">
			<input type="hidden" id="id" name="id" value="<%=infraction.getId()%>">
			<label for="type" class="form-label">Type : </label>
			<%if(user instanceof Admin){ %>
				<input type="text" id="type" name="type" value="<%=infraction.getType() %>"><br>
			<%} %>
			<%if(user instanceof Chief){ %>
				<input type="text" id="type" name="type" value="<%=infraction.getType() %>" readonly="readonly"><br>
			<%} %>
			<label for="amount" class="form-label">Montant de l'infraction : </label>
			<input type="number" min="0.00" max="10000.00" step="0.01" id="amount" name="amount" value="<%=infraction.getAmount() %>"><br>
			<button type="submit" class="btn btn-primary">Valider</button>
			<a href="infractions" class="btn btn-primary">Retour</a>
		</form>
	</div>
</body>
</html>