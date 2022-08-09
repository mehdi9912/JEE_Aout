<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="../base.jsp" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Création d'un utilisateur</title>
</head>
<body>

<%  String userType = (String)request.getAttribute("userType"); 
	ArrayList<PoliceArea> policeAreas = (ArrayList<PoliceArea>)request.getAttribute("policeareas");
	ArrayList<Chief> chiefs=null;
	if(request.getAttribute("chiefs")!= null){
		chiefs = (ArrayList<Chief>)request.getAttribute("chiefs");
	}
%>
<%! public static String getUserTypeInFrench(String userType){
	String type="Aucun";	
		if(userType.equals("policeman")){
			type="Policier";
		}
		if(userType.equals("chief")){
			type="Chef de bridage";
		}
		if(userType.equals("collector")){
			type="Percepteur d'amendes";
		}
	return type;
	}

	%>
}
<div class="text-center">
	<h3>Création d'un utilisateur</h3>
		<form method="post" action="adduser">
			<input type="hidden" id="userTypeReceive" name="userTypeReceive" value="<%=userType%>" ><br>
			<label for="userType" class="form-label">Type d'utilisateur : </label>
			<input type="text" id="userType" name="userType" value="<%=getUserTypeInFrench(userType) %>" disabled="disabled"><br>
			<label for="lastname" class="form-label">Nom : </label>
			<input type="text" id="lastname" name="lastname" required="required"><br>
			<label for="firstname" class="form-label">Prenom : </label>
			<input type="text" id="firstname" name="firstname" required="required"><br>
			<label for="password" class="form-label">Mot de passe : </label>
			<input type="text" id="password" name="password"><br>
			<label for="policearea-select" class="form-label">Zone de police : </label>
			<select id="policearea-select" name="policearea" required="required">
			<%for(PoliceArea area : policeAreas){ %>
				<option value="<%= area.getId()%>"><%=area.getAreaName() %></option>
				<%}%>
			</select><br>
			<%if(userType.equals("policeman")){%>
				<label for="chief-select" class="form-label">Chef de bridage : </label>
				<select id="chief-select" name="chief" required="required">
				<%for(Chief chiefIn : chiefs){ %>
					<option value="<%= chiefIn.getSerialNumber()%>"><%=chiefIn.getLastname()%>  <%=chiefIn.getFirstname()%></option>
			<%}}%>
			</select><br>
				<button type="submit" class="btn btn-primary">Valider</button>
				<a href="adduser" class="btn btn-primary">Retour</a>
		</form>
	</div>

</body>
</html>