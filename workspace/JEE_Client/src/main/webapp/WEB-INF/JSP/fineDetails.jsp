<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
         <%@ include file="base.jsp" %>
    <%@page import="java.text.NumberFormat" %>
	<%@page import="java.util.Locale" %>
	<%@page import="java.time.format.DateTimeFormatter" %>
	<%@page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>D�tails de l'amende</title>
</head>
<body>
<%	Fine fine = (Fine) request.getAttribute("fine"); 
	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
%>
	<%!
		public static String fineStatusString(FineStatus status){
			switch(status){
				case waitingForValidation:
					return "En attente de validation";
				case validated:
					return "Valid�e";
				case sent:
					return "Envoy�e";
				default: 
					return "Status inconnu";
			}
		}
		public String numberFormat(double nbr){
			Locale belgium = new Locale("fr", "BE");
			NumberFormat eurosFormat = NumberFormat.getCurrencyInstance(belgium);
			return String.valueOf(eurosFormat.format(nbr));
			}
		public static String convertDateTimeToString(LocalDateTime datetime,DateTimeFormatter format ){
				String formatDateTime = datetime.format(format);
				String dateTime =formatDateTime.replace("T", " ");
				return dateTime;
		}
		public static String getCivilian(Fine fine){
			if(fine.getCivilianFirstname()!=null && !fine.getCivilianFirstname().isEmpty() && fine.getCivilianLastname()!=null &&
					!fine.getCivilianLastname().isEmpty()){
				return fine.getCivilianLastname() + " " + fine.getCivilianFirstname();
			}
			return "Non renseign�";
		}
		public static String getLicensePlate(Fine fine){
			if(fine.getLicensePlate()!=null && !fine.getLicensePlate().isEmpty()){
				return fine.getLicensePlate();
			}
			return "Non renseign�";
		}
	%>
	<div class="text-center">
	<h3>D�tails de l'amende num�ro <%= fine.getFineId() %></h3>
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
	         		<th>V�hicule concern�</th>
	         		<th>Date et heure</th>
					<th>Nom pr�nom du pr�venu</th>
					<th>Plaque d'immatriculation</th>
					<th>Montant de l'amende</th>
	             	<th>Policier concern�</th>
	             	<th>Commentaire du policier</th>
	             	<th>Status de l'amende</th>
	           </tr>
	 		</thead>
	 		<tbody>
			   	 <tr>
				     <td><%= fine.getFineId() %></td>
				     <td><%= fine.getInfractionType().getType()%></td>
				     <td><%= fine.getVehiculeType().getType()%></td>
				     <td><%= convertDateTimeToString(fine.getTimestamp(), format) %></td>
				     <td><%= getCivilian(fine)%></td>
				     <td><%= getLicensePlate(fine)%></td>
				     <td><%= String.format("%.2f", fine.getTotalPrice())%> euros</td>
				     <td><%= fine.getPoliceman().getLastname()%>  <%= fine.getPoliceman().getFirstname()%></td>
				     <td><%= fine.getComment() %></td>
				     <td><%= fineStatusString(fine.getStatus()) %></td>
			    </tr>
	 		</tbody>
		</table>
		  <%if(fine.getStatus().equals(FineStatus.waitingForValidation) && user instanceof Chief){ %>
		     <form action="fine" method="POST">
		     	<input type="hidden" name="action" value="valide">
				<button type="submit" class="btn btn-success" name="fineid" value="<%=fine.getFineId()%>">Valider cette amende</button>
			</form>
			<form action="fine" method="POST">
				<input type="hidden" name="action" value="delete">
				<button type="submit" class="btn btn-red" name="fineid" value="<%=fine.getFineId()%>">Supprimer cette amende</button>
			</form>
	     <%} %>
     </div>
</body>
</html>