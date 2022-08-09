<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="../base.jsp" %>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listing des utilisateurs</title>
</head>
<body>
	<% 
		ArrayList<User> users = (ArrayList<User>)request.getAttribute("users");
	%>
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
	
	<% if(request.getAttribute("message")!=null){%>
	<div class="alert alert-success" style='text-align: center' role="alert">
				<%= request.getAttribute("message") %>
	</div>
	<% } %>
	<table class="table table-bordered" style="text-align: center">
 		<thead class="table-active">
            <tr>
            	<th>Type d'utilisateur</th>
         		<th>Matricule</th>
         		<th>Nom</th>
             	<th>Prénom</th>
             	<th>Zone de police</th>
             	<th>Actions</th>
           </tr>
 		</thead>
 		<tbody>
	 		<% for(User userIn : users){ 		
  				%>
		   	 <tr>
			     <td><%= userType(userIn) %></td>
			     <td><%= userIn.getSerialNumber() %></td>
			     <td><%= userIn.getLastname() %></td>
			     <td><%= userIn.getFirstname() %></td>
			     <td><%= getPoliceAreaName(userIn)%></td>
		    	<td>
			    	<a  href="<%=str%>/modifyuser?serialnumber=<%=userIn.getSerialNumber()%>">Modifier</a>
		    		<a  href="<%=str%>/deleteuser?serialnumber=<%=userIn.getSerialNumber()%>">Supprimer</a>
		    	</td>
		    </tr>
			    <%}%>
 		</tbody>
	</table>
	<div class="text-center">
		<a class="btn btn-red" href="<%=str%>/adduser">Ajouter un utilisateur</a>
	</div>
</body>
</html>