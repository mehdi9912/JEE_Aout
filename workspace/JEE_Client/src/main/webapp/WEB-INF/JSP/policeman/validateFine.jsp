<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="../base.jsp" %>
<%@page import="java.util.ArrayList" %>
	<%@page import="java.text.NumberFormat" %>
	<%@page import="java.util.Locale" %>
	<%@page import="java.time.format.DateTimeFormatter" %>
	<%@page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Récapitulatif</title>
</head>
<body>
	<% 
		ArrayList<Fine> fines = (ArrayList<Fine>)session.getAttribute("fines");
		boolean inOrder = (boolean)request.getAttribute("inOrder");
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
	%>	
	<%!
		public static String getCivilian(Fine fine){
			if(fine.getCivilianFirstname()!=null && !fine.getCivilianFirstname().isEmpty() && fine.getCivilianLastname()!=null &&
					!fine.getCivilianLastname().isEmpty()){
				return fine.getCivilianLastname() + " " + fine.getCivilianFirstname();
			}
			return "Non renseigné";
		}
		public static String getLicensePlate(Fine fine){
			if(fine.getLicensePlate()!=null && !fine.getLicensePlate().isEmpty()){
				return fine.getLicensePlate();
			}
			return "Non renseigné";
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
	
	%>
	<div class="text-center">
	<% if(request.getAttribute("message")!=null){%>
		<h3>Récapitulatif</h3>
		<div class="alert alert-success"  role="alert">
					<%= request.getAttribute("message")%>
		</div>
		<% } %>
		
		<table class="table table-bordered" style="text-align: center">
	 		<thead class="table-active">
	            <tr>
	            	<th>Type d'infraction</th>
	         		<th>Véhicule concernée</th>
	             	<th>Personne concernée</th>
	             	<th>Date et heure</th>
	             	<th>Plaque d'immatriculation</th>
	             	<th>Commentaire</th>
	             	<th>Montant de l'amende</th>
	           </tr>
	 		</thead>
	 		<tbody>
		 		<% for(Fine fine : fines){ 		
	  				%>
			   	 <tr>
				     <td><%= fine.getInfractionType().getType() %></td>
				     <td><%= fine.getVehiculeType().getType()%></td>
				     <td><%= getCivilian(fine)%></td>
				     <td><%= convertDateTimeToString(fine.getTimestamp(), format) %></td>
				     <td><%= getLicensePlate(fine)%></td>
				     <td><%= fine.getComment()%></td>
				     <td><%=String.format("%.2f", fine.getTotalPrice())%> euros</td>
			    </tr>
			    <%} %>
	 		</tbody>
		</table>
		<table>
			<tr><th>Total à payer</th></tr>
			<tr><td><%=String.format("%.2f", Fine.calculTotalPrice(fines))%> euros</td></tr>
		</table>
		<%if(!inOrder){%>
			<strong style='color : red'>Le système a reconnu le véhicule comme étant en défaut d'assurance</strong>
			<form method="post" action="addfine">
				<input type="hidden" name="infractiontypeid" value="6">
				<input type="submit" class="btn btn-red" value="Encoder le défaut d'assurance">
			</form>
		<%} %>
		<a class="btn btn-red" href="<%=str%>/addfine">Ajouter une nouvelle amende à ce véhicule</a>
	</div>
</body>