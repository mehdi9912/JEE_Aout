<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Suppression d'un utilisateur</title>
</head>
<body>
<% User userToDelete = (User)request.getAttribute("user") ;%>
<%!
	  public static String userType(User userToTest){
		   if(userToTest instanceof Collector){
			   return "Percepteur d'amendes";
		   }
		   if(userToTest instanceof Chief){
			   return "Chef de bridage";
		   }
		   if(userToTest instanceof Policeman){
			   return "Policier";
		   }
		   return "Aucun type";
	   }
	
	public static String getPoliceAreaName(User userToTest){
	if(userToTest instanceof Collector){
		Collector collector = (Collector)userToTest;
		return collector.getPoliceArea().getAreaName();
   }
   if(userToTest instanceof Chief){
	   Chief chief = (Chief)userToTest;
		return chief.getPoliceArea().getAreaName();
   }
   if(userToTest instanceof Policeman){
	   Policeman policeman = (Policeman)userToTest;
		return policeman.getPoliceArea().getAreaName();
   }
   return "Aucune zone";
		
	}
	%>
	<div class="text-center">
	<h3>Supprimer cette utilisateur ?</h3>
	<table class="table table-bordered">
	 		<thead>
                    <tr>
                    	<th>Type d'utilisateur</th>
                    	<th>Matricule</th>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Zone de police</th>
                    </tr>
	 		</thead>
	 		<tbody>
			   	 <tr>
				     <td><%= userType(userToDelete)%></td>
				     <td><%= userToDelete.getSerialNumber() %></td>
				     <td><%= userToDelete.getLastname()%></td>
				     <td><%= userToDelete.getFirstname()%></td>
				     <td><%= getPoliceAreaName(userToDelete)%></td>	
			    </tr>
	 		</tbody>
	</table>
	
		<form method="post" action="deleteuser">
			<input type="hidden" value="true" name="confirm">
			<input type="hidden" value="<%=userToDelete.getSerialNumber()  %>" name="serialNumber">
			<button type="submit" class="btn btn-danger">Supprimer</button>
			<a href="users" class="btn btn-primary">Retour</a>
		</form>
	</div>

</body>
</html>