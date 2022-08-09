<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../base.jsp" %>
    <%@page import="java.util.ArrayList" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modification d'un utilisateur</title>
</head>
<body>
	<% User userToModify = (User)request.getAttribute("user") ;
	ArrayList<Chief> chiefs = (ArrayList<Chief>)request.getAttribute("chiefs");
	ArrayList<PoliceArea> policeAreas = (ArrayList<PoliceArea>)request.getAttribute("policeareas");
	Policeman userIsPoliceMan=null;
	Chief userIsChief=null;
	Collector userIsCollector=null;
	if(userToModify instanceof Policeman) {
		userIsPoliceMan = (Policeman) userToModify;
	}
	if(userToModify instanceof Chief) {
		userIsChief = (Chief) userToModify;
	}
	if(userToModify instanceof Collector) {
		userIsCollector = (Collector) userToModify;
	}
	
	%>
	<div class="text-center">
	<h3>Modification d'un utilisateur</h3>
		<form method="post" action="modifyuser">
			<label for="serialNumber" class="form-label">Matricule : </label>
			<input type="text" id="serialNumber" name="serialNumber" value="<%=userToModify.getSerialNumber() %>" readonly="readonly"><br>
			<label for="lastname" class="form-label">Nom : </label>
			<input type="text" id="lastname" name="lastname" value="<%=userToModify.getLastname() %>"><br>
			<label for="firstname" class="form-label">Prenom : </label>
			<input type="text" id="firstname" name="firstname" value="<%=userToModify.getFirstname() %>"><br>
			<label for="check_password" class="form-label">Modifier le mot de passe ?</label>
			<input type="checkbox" id="check_password" name="check_password"><br>
			<label for="password" class="form-label">Mot de passe : </label>
			<input type="text" id="password" name="password"><br>
			<label for="policearea-select" class="form-label">Zone de police : </label>
			<select id="policearea-select" name="policearea">
			<%for(PoliceArea area : policeAreas){ %>
			
			<%if(userIsPoliceMan != null){ %>
				<%if(area.getAreaName().equals(userIsPoliceMan.getPoliceArea().getAreaName())){ %>
				<option value="<%= area.getId()%>" selected="selected"><%=area.getAreaName() %></option>
				<%}else{%>
				<option value="<%= area.getId()%>" ><%=area.getAreaName() %></option>
				<%}}%>
			<%if(userIsChief != null){ %>
				<%if(area.getAreaName().equals(userIsChief.getPoliceArea().getAreaName())){ %>
				<option value="<%= area.getId()%>" selected="selected"><%=area.getAreaName() %></option>
				<%}else{%>
				<option value="<%= area.getId()%>" ><%=area.getAreaName() %></option>
				<%}}%>
			<%if(userIsCollector != null){ %>
				<%if(area.getAreaName().equals(userIsCollector.getPoliceArea().getAreaName())){ %>
				<option value="<%= area.getId()%>" selected="selected"><%=area.getAreaName() %></option>
				<%}else{%>
				<option value="<%= area.getId()%>" ><%=area.getAreaName() %></option>
				<%}}}%>
			</select><br>
			<%if(userIsPoliceMan != null){%>
				<label for="chief-select" class="form-label">Chef de bridage : </label>
				<select id="chief-select" name="chief">
				<%for(Chief chiefIn : chiefs){ %>
					<%if(chiefIn.getSerialNumber().equals(userIsPoliceMan.getChief().getSerialNumber())){ %>
					<option value="<%= chiefIn.getSerialNumber()%>" selected="selected"><%=chiefIn.getLastname()%>  <%=chiefIn.getFirstname()%></option>
					<%}else{%>
					<option value="<%= chiefIn.getSerialNumber()%>" ><%=chiefIn.getLastname()%>  <%=chiefIn.getFirstname()%></option>
					<%}}%>
				
				</select><br>
			<%} %>
				<button type="submit" class="btn btn-primary">Valider</button>
				<a href="users" class="btn btn-primary">Retour</a>
		</form>
	</div>
</body>
</html>