<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ include file="../base.jsp" %>
           <%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listing des amendes validées</title>
</head>
<body>
	<% 
		ArrayList<Fine> fines = (ArrayList<Fine>)request.getAttribute("fines");
	%>
		<%!
		public static String fineStatusString(FineStatus status){
			switch(status){
				case waitingForValidation:
					return "En attente de validation";
				case validated:
					return "Validée";
				case sent:
					return "Envoyée";
				default: 
					return "Status inconnu";
			}
		}
	%>
<% if(request.getAttribute("message")!=null){%>
	<div class="alert alert-success" style='text-align: center' role="alert">
			<%= request.getAttribute("message") %>
	</div>
<% } %>


<table class="table table-bordered" style="text-align: center">
 		<thead class="table-active">
            <tr>
            	<th>Identifiant de l'amende</th>
            	<th>Type d'infraction</th>
         		<th>Véhicule concerné</th>
				<th>Status de l'amende</th>
             	<th>Policier concerné</th>
             	<th>Actions<th>
           </tr>
 		</thead>
 		<tbody>
	 		<% for(Fine fine : fines){ %>
	 			<%if(fine.getStatus().equals(FineStatus.validated)){ %>
			   	 <tr>
				     <td><%= fine.getFineId() %></td>
				     <td><%= fine.getInfractionType().getType()%></td>
				     <td><%= fine.getVehiculeType().getType()%></td>
				     <td><%= fineStatusString(fine.getStatus()) %></td>
				     <td><%= fine.getPoliceman().getLastname()%>  <%= fine.getPoliceman().getFirstname()%></td>
			    	<td>
				    	<a  href="<%=str%>/fine?id=<%=fine.getFineId()%>">Consulter</a>
			    	</td>
			    </tr>
			<%}}%>
 		</tbody>
	</table>

</body>
</html>