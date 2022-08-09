<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../base.jsp" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listing des types de vehicules</title>
</head>
<body>
	<% 
		ArrayList<VehiculeType> vehicules = (ArrayList<VehiculeType>)request.getAttribute("vehicules");
	%>
	<% if(request.getAttribute("message")!=null){%>
	<div class="alert alert-success" style='text-align: center' role="alert">
				<%= request.getAttribute("message") %>
	</div>
	<% } %>
	<div class="text-center">
		<table class="table table-bordered">
	 		<thead class="table-active">
	            <tr>
	            	<th>Type de vehicule</th>
	             	<th>Actions</th>
	           </tr>
	 		</thead>
	 		<tbody>
		 		<% for(VehiculeType vehicule : vehicules){ 		
	  				%>
			   	 <tr>
				     <td><%=vehicule.getType() %></td>
			    	<td>
				    	<a  href="<%=str%>/modifyvehicule?id=<%=vehicule.getId()%>">Modifier</a>
			    		<a  href="<%=str%>/deletevehicule?id=<%=vehicule.getId()%>">Supprimer</a>
			    	</td>
			    </tr>
				    <%}%>
	 		</tbody>
		</table>
		<a class="btn btn-red" href="<%=str%>/addvehicule">Ajouter un type de vehicule</a>
	</div>

</body>
</html>