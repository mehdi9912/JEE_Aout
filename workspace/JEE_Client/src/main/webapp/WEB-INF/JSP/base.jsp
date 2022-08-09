<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>
<%@page import="be.project.javabeans.User" %>
<%@page import="be.project.javabeans.Admin" %>
<%@page import="be.project.javabeans.Collector" %>
<%@page import="be.project.javabeans.Policeman" %>
<%@page import="be.project.javabeans.Chief" %>
<%@page import="be.project.javabeans.PoliceArea" %>
<%@page import="be.project.javabeans.VehiculeType" %>
<%@page import="be.project.javabeans.InfractionType" %>
<%@page import="be.project.javabeans.Fine" %>
<%@page import="be.project.enumerations.FineStatus" %>
<jsp:useBean id="admin" class="be.project.javabeans.Admin" scope="session"></jsp:useBean>
<jsp:useBean id="collector" class="be.project.javabeans.Collector" scope="session"></jsp:useBean>
<jsp:useBean id="policeman" class="be.project.javabeans.Policeman" scope="session"></jsp:useBean>
<jsp:useBean id="chief" class="be.project.javabeans.Chief" scope="session"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link href="css/style.css" rel="stylesheet" type="text/css">	
</head>
<body>
		<%
		int URLLength = (int)request.getRequestURL().length();
		int servPathLength = (int)request.getServletPath().length();
		String chaine = request.getRequestURL() + request.getServletPath();
		String str = chaine.substring(0, URLLength-servPathLength);
		User user = (User)session.getAttribute("connectedUser");
		%>
		<div class="container">
			<header class="d-flex justify-content-center py-3">
			<ul class="nav nav-pills">
			<% if(user != null && user.serialNumberIsValid()){ %>
		      	<li class="nav-item">
					<a href="<%=str%>/connexion" class="nav-link">Accueil</a>
				</li>
				<%if(user instanceof Chief){ %>	
					<li class="nav-item">
		      			<a class="nav-link" href="<%= str%>/infractions"><span class="glyphicon glyphicon-log-off"></span>Gérer les montants des infractions</a>
		      		</li>
				<%} %>
	      		<li class="nav-item">
	      			<a class="nav-link" href="<%= str%>/logout"><span class="glyphicon glyphicon-log-off"></span>Se déconnecter</a>
	      		</li>
		   	<%} %>		
			</ul>
			</header>
		</div>
		<hr>
</body>
</html>