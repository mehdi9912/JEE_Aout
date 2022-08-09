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

<%  InfractionType infraction = (InfractionType)request.getAttribute("infraction"); 
	ArrayList<VehiculeType> vehicules = (ArrayList<VehiculeType>)request.getAttribute("vehicules");
	Fine fine =(Fine)request.getAttribute("preinfos");
%>
<div class="text-center">
	<h3>Encodage d'une nouvelle amender</h3>
	<%if(fine==null){ %>
		<form method="post" action="addfine">
			<input type="hidden" name="infractionTypeReceive" value="<%=infraction.getId() %>">
			<label for="infractionTypeReceive" class="form-label">Type d'infraction : </label>
			<input type="text" id="infractionTypeReceive" value="<%=infraction.getType() %>" disabled="disabled"><br>
			<label for="vehiculeType-select" class="form-label">Type de véhicule : </label>
			<select id="vehiculeType-select" name="vehiculeType" required="required">
			<%for(VehiculeType vehicule : vehicules){ %>
				<option value="<%= vehicule.getId()%>"><%=vehicule.getType() %></option>
			<%}%>
			</select><br>
			<%if(!infraction.getType().equals("Pas d assurance")){ %>
				<label for="licensePlate" class="form-label">Plaque d'immatriculation : </label>
				<input type="text" id="licensePlate" name="licensePlate" pattern="^[0-9]{1}-?[A-Z]{3}-?[0-9]{3}$"
				title="Veuillez respecter ce format : 1-AAA-123"><br>
				<div id="licensePlate" class="form-text" class="invalid-feedback">S'il y a en</div>
			<%} %>
			
			<label for="timestamp" class="form-label">Date et heure du délit : </label>
			<input type="datetime-local" id="timestamp" name="timestamp" required="required"><br>
			
			<label for="lastname" class="form-label">Nom de l'usager : </label>
			<input type="text" id="lastname" name="lastname"> <br>
			<label for="firstname" class="form-label">Prénom de l'usager : </label>
			<input type="text" id="firstname" name="firstname"> <br>
			<div id="firstname" class="form-text" class="invalid-feedback">Remplissez le nom et prénom uniquement si la personne est présente sur les lieux</div>	
			
			<label for="comment" class="form-label">Commentaire de l'officier de police : </label><br>
			<textarea id="comment" name="comment"  maxlength="300"  rows="5" cols="50" required></textarea><br>

			<button type="submit" class="btn btn-primary">Suivant</button>
			<a href="addfine" class="btn btn-primary">Retour</a>
		</form>
		<%}else{%>
			<!-- Prérempli les infos si amende successive sur un même véhicule -->
			<form method="post" action="addfine">
				<input type="hidden" name="infractionTypeReceive" value="<%=infraction.getId() %>">
				<label for="infractionTypeReceive" class="form-label">Type d'infraction : </label>
				<input type="text" id="infractionTypeReceive" value="<%=infraction.getType() %>" disabled="disabled"><br>
				<input type="hidden" name="vehiculeType" value="<%=fine.getVehiculeType().getId() %>">
				<label for="vehiculeType-select" class="form-label">Type de véhicule : </label>
				<input id="vehiculeType" name="vehiculeTypeName" value="<%=fine.getVehiculeType().getType() %>" disabled="disabled"><br>
				
				<%if(!fine.getInfractionType().getType().equals("Pas d assurance") && !fine.getLicensePlate().isEmpty()){ %>			
					<label for="licensePlate">Plaque d immatriculation : </label>
					<input type="text" id="licensePlate" name="licensePlate" readonly="readonly" value="<%= fine.getLicensePlate()%>"><br>
				<%} %>
				
				<label for="timestamp" class="form-label">Date et heure du délit : </label>
				<input type="datetime-local" id="timestamp" name="timestamp" required="required"><br>
				
				<%if(!fine.getCivilianLastname().isEmpty() && !fine.getCivilianFirstname().isEmpty()){ %>
				<label for="lastname" class="form-label">Nom de l'usager : </label>
				<input type="text" id="lastname" name="lastname" readonly="readonly" value="<%=fine.getCivilianLastname()%>"> <br>
				<label for="firstname" class="form-label">Prénom de l'usager : </label>
				<input type="text" id="firstname" name="firstname" readonly="readonly" value="<%=fine.getCivilianFirstname()%>"> <br>	
				<%} %>
				<label for="comment" class="form-label">Commentaire de l'officier de police : </label><br>
				<textarea id="comment" name="comment"  maxlength="300"  rows="5" cols="50" required></textarea><br>
	
				<button type="submit" class="btn btn-primary">Suivant</button>
				<a href="addfine" class="btn btn-primary">Retour</a>
			</form>
		<%} %>
		
	</div>

</body>
</html>