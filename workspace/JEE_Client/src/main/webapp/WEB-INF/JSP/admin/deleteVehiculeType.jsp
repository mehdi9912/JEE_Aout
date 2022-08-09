<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Suppression d'un type de vehicule</title>
</head>
<body>
<% 	VehiculeType vehicule = (VehiculeType)request.getAttribute("vehicule") ;%>

<div class="text-center">
	<h3>Supprimer ce type de vehicule ?</h3>
	<table class="table table-bordered">
	 		<thead>
                    <tr>
                    	<th>Type de vehicule</th>
                    </tr>
	 		</thead>
	 		<tbody>
			   	 <tr>
				     <td><%= vehicule.getType()%></td>
			    </tr>
	 		</tbody>
	</table>
	
		<form method="post" action="deletevehicule">
			<input type="hidden" value="true" name="confirm">
			<input type="hidden" value="<%=vehicule.getId()%>" name="id">
			<button type="submit" class="btn btn-danger">Supprimer</button>
			<a href="vehicules" class="btn btn-primary">Retour</a>
		</form>
	</div>
</body>
</html>