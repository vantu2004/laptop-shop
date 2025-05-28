<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
					<h1 class="mt-4">Manage orders</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
						<li class="breadcrumb-item active">Orders</li>
					</ol>

					<div class="row">
						<div class="col-12 mx-auto">
							<h3>Detail order</h3>
							<hr />
							<div class="card" style="width: 100%; position: relative;">
								<div class="card-header">
									<b>Orders information</b>
								</div>
								<div class="table-responsive">
									<table class="table">
										<thead>
											<tr>
												<th scope="col">Sản phẩm</th>
												<th scope="col">Tên</th>
												<th scope="col">Giá cả</th>
												<th scope="col">Số lượng</th>
												<th scope="col">Thành tiền</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${ empty orderDetails}">
												<tr>
													<td colspan="6">Không có sản phẩm trong giỏ hàng</td>
												</tr>
											</c:if>
											<c:forEach var="orderDetail" items="${orderDetails}">

												<tr>
													<th scope="row">
														<div class="d-flex align-items-center">
															<img src="/productImage/${orderDetail.product.image}"
																class="img-fluid me-5 rounded-circle"
																style="width: 80px; height: 80px;" alt="">
														</div>
													</th>
													<td>
														<p class="mb-0 mt-4">
															<a href="/product/${orderDetail.product.id}"
																target="_blank"> ${orderDetail.product.name} </a>
														</p>
													</td>
													<td>
														<p class="mb-0 mt-4">
															<fmt:formatNumber type="number"
																value="${orderDetail.price}" />
															VND
														</p>
													</td>
													<td>
														<div class="input-group quantity mt-4"
															style="width: 100px;">
															<input type="text"
																class="form-control form-control-sm text-center border-0"
																value="${orderDetail.quantity}">
														</div>
													</td>
													<td>
														<p class="mb-0 mt-4"
															data-cart-detail-id="${orderDetail.id}">
															<fmt:formatNumber type="number"
																value="${orderDetail.price * orderDetail.quantity}" />
															VND
														</p>
													</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
								</div>
							</div>
							<div class="d-flex justify-content-end" style="margin: 10px 0px">
								<a href="/admin/order" class="btn btn-primary">Back</a>
							</div>
						</div>
					</div>
				</div>
			</main>

			<jsp:include page="../layout/Footer.jsp" />

		</div>
	</div>
	<script
		src="/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="js/scripts.js"></script>

</body>

</html>