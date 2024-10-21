<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Update</title>
		
		<!-- Latest compiled and minified CSS -->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
		<!-- Latest compiled JavaScript -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
			<div class="container mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h2>Update a user</h2>
                            <hr />
                            <form:form method="post" action="/admin/user/update" modelAttribute="newUser">
                            	
                            	<div class="mb-3" style="display: none">
                                    <label class="form-label">Id:</label>
                                    <form:input type="text" class="form-control" path="id"/>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Email:</label>
                                    <form:input type="email" class="form-control" path="email" disabled="true"/>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Phone number:</label>
                                    <form:input type="text" class="form-control" path="phone" />
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Full Name:</label>
                                    <form:input type="text" class="form-control" path="fullName" />
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Address:</label>
                                    <form:input type="text" class="form-control" path="address" />
                                </div>

                                <div class="d-flex justify-content-end mt-3">
					                <button type="submit" class="btn btn-primary">Update</button>
					            </div>
                            </form:form>
                        </div>
                    </div>
                </div>
	</body>
</html>