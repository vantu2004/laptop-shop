<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Detail</title>
	
			<!-- Latest compiled and minified CSS -->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
		<!-- Latest compiled JavaScript -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	</head>
	<body>
		<div class="container mt-5">
                 <div class="row">
                     <div class="col-md-6 col-12 mx-auto">
                         <h2>Detail user</h2>
                         <hr />
						<div class="card" style="width: 100%;">
						  <div class="card-header">
						    <b>User information</b>
						  </div>
							  <ul class="list-group list-group-flush">
								    <li class="list-group-item">ID: ${infoUser.id}</li>
								    <li class="list-group-item">Email: ${infoUser.email}</li>
								    <li class="list-group-item">Full name: ${infoUser.fullName}</li>
								    <li class="list-group-item">Address: ${infoUser.address}</li>
								    <li class="list-group-item">Phone: ${infoUser.phone}</li>
							  </ul>
						</div>
						<div class="d-flex justify-content-end mt-3">
			                <a href="/admin/user" class="btn btn-primary">Back</a>
			            </div>
                     </div>
                 </div>
             </div>
	</body>
</html>