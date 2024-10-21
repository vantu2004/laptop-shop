<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Delete</title>
	
		<!-- Latest compiled and minified CSS -->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
		<!-- Latest compiled JavaScript -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
					<div class="container mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h2>Delete a user</h2>
                            <hr />
							<div class="alert alert-danger" role="alert">
								Are you sure to delete this user!
							</div>

							<form:form method="post" action="/admin/user/delete" modelAttribute="newUser">
							
								<div class="mb-3"  style="display: none">
                                    <label class="form-label">Id:</label>
                                    <form:input type="text" class="form-control" value="${id}" path="id"/>
                                </div>
                                
                                <div class="d-flex justify-content-end mt-3">
									<button type="submit" class="btn btn-primary">Delete</button>
								</div>
								
                           	</form:form>
			                
                        </div>
                    </div>
                </div>
	</body>
</html>