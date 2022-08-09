<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ include file="../base.jsp" %>
<%@page import="java.util.ArrayList" %>
	<%@page import="java.text.NumberFormat" %>
	<%@page import="java.util.Locale" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listing des types d'infractions</title>
</head>
<body>
	<% 
		ArrayList<InfractionType> infractions = (ArrayList<InfractionType>)request.getAttribute("infractions");
	%>
	
	<%! public String numberFormat(double nbr){
			Locale belgium = new Locale("fr", "BE");
			NumberFormat eurosFormat = NumberFormat.getCurrencyInstance(belgium);
			return String.valueOf(eurosFormat.format(nbr));
			}
		%>
	<% if(request.getAttribute("message")!=null){%>
	<div class="alert alert-success" style='text-align: center' role="alert">
				<%= request.getAttribute("message") %>
	</div>
	<% } %>
	<div class="text-center">
		<table class="table table-bordered">
	 		<thead class="table-active">
	            <tr>
	            	<th>Type d'infraction</th>
	            	<th>Montant</th>
	             	<th>Actions</th>
	           </tr>
	 		</thead>
	 		<tbody>
		 		<% for(InfractionType infraction : infractions){ 		
	  				%>
			   	 <tr>
				     <td><%=infraction.getType() %></td>
				     <td><%=String.format("%.2f", infraction.getAmount()) %> euros</td>
			    	<td>
			    	<% if(user instanceof Admin){ %>
				    	<a  href="<%=str%>/modifyinfraction?id=<%=infraction.getId()%>">Modifier</a>
			    		<a  href="<%=str%>/deleteinfraction?id=<%=infraction.getId()%>">Supprimer</a>
			    	<%} %>
			    	<%if(user instanceof Chief){ %>
			    		<a  href="<%=str%>/modifyinfraction?id=<%=infraction.getId()%>">Modifier le montant</a>
			    	<%} %>
			    	</td>
			    </tr>
				    <%}%>
	 		</tbody>
		</table>
		<% if(user instanceof Admin){ %>
			<a class="btn btn-red" href="<%=str%>/addinfraction">Ajouter un nouveau type d'infraction</a>
		<%} %>
	</div>

</body>
</html>