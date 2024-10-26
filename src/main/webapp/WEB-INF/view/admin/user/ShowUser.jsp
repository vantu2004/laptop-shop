<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="Văn Tú - Dự án laptopshop" />
<meta name="author" content="Văn Tú" />
<title>Detail</title>
<link
	href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css"
	rel="stylesheet" />
<link href="/css/styles.css" rel="stylesheet" />

</head>

<body class="sb-nav-fixed">

	<jsp:include page="../layout/Header.jsp" />

	<div id="layoutSidenav">

		<jsp:include page="../layout/Sidebar.jsp" />

		<div id="layoutSidenav_content">
			<main>

				<div class="container-fluid px-4">
					<h1 class="mt-4">Manage users</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
						<li class="breadcrumb-item active">Users</li>
					</ol>

					<div class="row">
						<div class="col-md-6 col-12 mx-auto">
							<h3>Detail user</h3>
							<hr />
							<div class="card" style="width: 100%; position: relative;">
								<div class="card-header">
									<b>User information</b>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">ID: ${infoUser.id}</li>
									<li class="list-group-item">Email: ${infoUser.email}</li>
									<li class="list-group-item">Full name: ${infoUser.fullName}</li>
									<li class="list-group-item">Role: ${infoUser.role.name}</li>
									<li class="list-group-item">Address: ${infoUser.address}</li>
									<li class="list-group-item">Phone: ${infoUser.phone}</li>
									<li class="list-group-item">
										<c:if test="${not empty infoUser.avatar}">
											<!-- Image on the right side -->
											<img src="/avatar/${infoUser.avatar}" alt="User Image"
												class="img-thumbnail"
												style="object-fit: cover; width: 100%;">
										</c:if>
									</li>
								</ul>
							</div>
							<div class="d-flex justify-content-end mt-3">
								<a href="/admin/user" class="btn btn-primary">Back</a>
							</div>
						</div>
					</div>
				</div>
			</main>

			<jsp:include page="../layout/Footer.jsp" />

		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="js/scripts.js"></script>

</body>

</html>