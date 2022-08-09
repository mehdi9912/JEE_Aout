<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Suppression d'un type d'infraction</title>
</head>
<body>
<% 	InfractionType infraction = (InfractionType)request.getAttribute("infraction") ;%>

<div class="text-center">
	<h3>Supprimer ce type d'infraction ?</h3>
	<table class="table table-bordered">
	 		<thead>
                    <tr>
                    	<th>Type d'infraction</th>
                    	<th>Montant de l'infraction</th>
                    </tr>
	 		</thead>
	 		<tbody>
			   	 <tr>
				     <td><%= infraction.getType()%></td>
				     <td><%= infraction.getAmount()%></td>
			    </tr>
	 		</tbody>
	</table>
	
		<form method="post" action="deleteinfraction">
			<input type="hidden" value="true" name="confirm">
			<input type="hidden" value="<%=infraction.getId()%>" name="id">
			<button type="submit" class="btn btn-danger">Supprimer</button>
			<a href="infractions" class="btn btn-primary">Retour</a>
		</form>
	</div>
</body>
</html>