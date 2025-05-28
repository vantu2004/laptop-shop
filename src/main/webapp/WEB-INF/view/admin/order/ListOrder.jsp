<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="Văn Tú - Dự án laptopshop" />
<meta name="author" content="Văn Tú" />
<title>Orders</title>
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
					<h1 class="mt-4">Manage orders</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
						<li class="breadcrumb-item active">Orders</li>
					</ol>
					<div class="d-flex justify-content-between mb-4">
						<h3 style="margin: 0">Table orders</h3>
					</div>

					<hr />
					<table class="table table-striped table-hover">
						<thead>
							<tr>
								<th scope="col">ID</th>
								<th scope="col">Total price</th>
								<th scope="col">User</th>
								<th scope="col">Status</th>
								<th scope="col">Payment</th>
								<th scope="col">Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr>
									<th scope="row">${order.id}</th>

									<td><fmt:formatNumber type="number"
											value="${order.totalPrice}" /> VND</td>
									<td>${order.user.fullName}</td>
									<td>${order.status}</td>
									<td>
										<div>Status: ${order.paymentStatus}</div>
										<div>Status: ${order.paymentRef}</div>
										<div>Status: ${order.paymentMethod}</div>
									</td>
									<td><a href="/admin/order/${order.id}"
										class="btn btn-success me-2">View</a> <a
										href="/admin/order/update/${order.id}"
										class="btn btn-warning me-2">Update</a> <a
										href="/admin/order/delete/${order.id}"
										class="btn btn-danger me-2">Delete</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</main>

			<nav aria-label="Pagination">
				<ul class="pagination justify-content-center">
					<!-- Previous button -->
					<li class="page-item ${currentPage == 1 ? 'disabled' : ''}"><a
						class="page-link" href="/admin/order?page=${currentPage - 1}"
						tabindex="-1">Previous</a></li>

					<!-- Dynamic page links -->
					<c:forEach begin="${startPage}" end="${endPage}" var="page">
						<li class="page-item ${page == currentPage ? 'active' : ''}">
							<a class="page-link" href="/admin/order?page=${page}">${page}</a>
						</li>
					</c:forEach>

					<!-- Next button -->
					<li class="page-item ${currentPage == endPage ? 'disabled' : ''}">
						<a class="page-link" href="/admin/order?page=${currentPage + 1}">Next</a>
					</li>
				</ul>
			</nav>

			<jsp:include page="../layout/Footer.jsp" />

		</div>
	</div>
	<script
		src="/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="js/scripts.js"></script>

</body>

</html>