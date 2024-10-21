<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Users</title>
		
		<!-- Latest compiled and minified CSS -->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
		<!-- Latest compiled JavaScript -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
		<div class="container mt-5">
				<div class="d-flex justify-content-between mb-4">
					<h2>Table users</h2>
					<a href="/admin/user/create" class="btn btn-primary d-flex justify-content-center align-items-center">Create a user</a>
				</div>
				<hr/>
				<table class="table table-striped table-hover">
				  <thead>
				    <tr>
				      <th scope="col">ID</th>
				      <th scope="col">Email</th>
				      <th scope="col">Full name</th>
				      <th scope="col">Action</th>
				    </tr>
				  </thead>
				  <tbody>
						<c:forEach var="user"  items="${users}">
						   <tr>
						      <th scope="row">${user.id}</th>
						      <td>${user.email}</td>
						      <td>${user.fullName}</td>
						      <td>
						      		<a href="/admin/user/${user.id}" class="btn btn-success me-2">View</a>
						      		<a href="/admin/user/update/${user.id}" class="btn btn-warning me-2">Update</a>
						      		<a href="/admin/user/delete/${user.id}" class="btn btn-danger me-2">Delete</a>
						      </td>
						   </tr>
						</c:forEach>
				  </tbody>
				</table>
		</div>
	</body>
</html>