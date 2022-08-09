<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="../base.jsp" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Encodage d'une nouvelle amende</title>
</head>
<body>
<% ArrayList<InfractionType> infractions = (ArrayList<InfractionType>)request.getAttribute("infractions"); %>
	<div class="text-center">
	<h3>Encodage d'une nouvelle amende</h3>
		<form method="post" action="addfine">
			<label for="infractionType-select" class="form-label">Sélectionner le type d'infraction à signaler:</label><br>
			<select id="infractionType-select" name ="infractionType">
			<%for(InfractionType infraction : infractions){ %>
				<option value="<%=infraction.getId()%>"><%=infraction.getType() %></option>
			<%} %>
			</select><br>
			<button type="submit" class="btn btn-primary">Suivant</button>
			<a href="policeman" class="btn btn-primary">Retour</a>
		</form>
	</div>
</body>
</html>