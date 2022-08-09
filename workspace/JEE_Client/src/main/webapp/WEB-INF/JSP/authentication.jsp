<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestion d'amendes de roulage</title>
</head>
<body>
<%@ include file="base.jsp" %>
		<div class="container" >
			<div class="row justify-content-center">
			    <div class="text-center">
			      <h2>Connexion</h2>
			    </div>
			 </div>
			<% if(request.getAttribute("error")!=null){%>
				<div class="alert alert-danger" role="alert">
	  				<%= request.getAttribute("error") %>
				</div>
			<% } %>
			<form action="authentication" method="POST">
			  <div class="mb-3">
			    <label for="inputSerialNumber" class="form-label">Matricule</label>
			    <input type="text" class="form-control" id="inputSerialNumber" name="serialNumber" placeholder="Entrez votre matricule" required>
				<div id="serialNumber" class="form-text" class="invalid-feedback">Exemple : po571526</div>		 
			  </div>
			  <div class="mb-3">
			    <label for="inputPassword" class="form-label">Mot de passe</label>
			    <input type="password" class="form-control" id="inputPassword" name="password" placeholder="**********" required>
			  </div>
			  <div class="text-center">
			  	<button type="submit" class="btn btn-primary" >Se connecter</button>
			  </div>
			</form>
		</div>
</body>
</html>