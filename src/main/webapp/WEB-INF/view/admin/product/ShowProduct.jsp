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
	href="/css/style.min.css"
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
					<h1 class="mt-4">Manage products</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
						<li class="breadcrumb-item active">Products</li>
					</ol>

					<div class="row">
						<div class="col-md-6 col-12 mx-auto">
							<h3>Detail product</h3>
							<hr />
							<div class="card" style="width: 100%; position: relative;">
								<div class="card-header">
									<b>Product information</b>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item"><b>ID:</b> ${infoProduct.id}</li>
									<li class="list-group-item"><b>Product name:</b> ${infoProduct.name}</li>
									<li class="list-group-item"><b>Price:</b> ${infoProduct.price}</li>
									<li class="list-group-item"><b>Short description:</b> ${infoProduct.shortDesc}</li>
									<li class="list-group-item"><b>Detail description:</b> ${infoProduct.detailDesc}</li>
									<li class="list-group-item"><b>Quantity:</b> ${infoProduct.quantity}</li>
									<li class="list-group-item"><b>Factory:</b> ${infoProduct.factory}</li>
									<li class="list-group-item"><b>Target:</b> ${infoProduct.target}</li>
									<li class="list-group-item">
										<c:if test="${not empty infoProduct.image}">
											<img src="<c:url value='/productImage/${infoProduct.image}'/>" alt="Product Image"
												class="img-thumbnail"
												style="object-fit: cover; width: 100%;">
										</c:if>
									</li>
								</ul>
							</div>
							<div class="d-flex justify-content-end mt-3">
								<a href="/admin/product" class="btn btn-primary">Back</a>
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