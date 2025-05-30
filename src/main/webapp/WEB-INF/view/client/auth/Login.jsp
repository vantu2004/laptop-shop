<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Login</title>
<link href="css/styles.css" rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
	crossorigin="anonymous"></script>
</head>
<body class="bg-primary">
	<div id="layoutAuthentication">
		<div id="layoutAuthentication_content">
			<main>
				<div class="container">
					<div class="row justify-content-center">
						<div class="col-lg-5">
							<div class="card shadow-lg border-0 rounded-lg mt-5">
								<div class="card-header">
									<h3 class="text-center font-weight-light my-4">Login</h3>
								</div>
								<div class="card-body">
									<!-- khi dùng spring security thì mặc định dùng action=/login -->
									<form method="post" action="/login">
										<div class="form-floating mb-3">
											<!-- mặc định dùng name=username, nếu muốn dùng khác thì phải đổi cấu hình bên trong -->
											<input class="form-control" id="inputEmail" type="email"
												placeholder="name@example.com" name="username" /> <label
												for="inputEmail">Email address</label>
										</div>
										<div class="form-floating mb-3">
											<!-- mặc định dùng name=password, nếu muốn dùng khác thì phải đổi cấu hình bên trong -->
											<input class="form-control" id="inputPassword"
												type="password" placeholder="Password" name="password" /> <label
												for="inputPassword">Password</label>
										</div>

										<div>
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
										</div>
										<c:if test="${param.error != null}">
											<div class="my-2" style="color: red;">Invalid email or
												password.</div>
										</c:if>
										<c:if test="${param.logout != null}">
											<div class="my-2" style="color: green;">Logout success.</div>
										</c:if>
										<div class="mt-4 mb-0">
											<div class="d-grid">
												<button class="btn btn-primary btn-block" type="submit">Login
													Account</button>
											</div>
										</div>
									</form>
								</div>
								<div>
									<div style="text-align: center">
										<span>Hoặc</span>
									</div>
									<div
										class="d-flex justify-content-center align-items-center my-style="gap: 20px">
										<a href="/oauth2/authorization/google"
											title="Đăng nhập với Google" style="margin: 10px"> <img
											height="40" width="40"
											src="/client/img/provider/default-google.png" />
										</a> <a href="/oauth2/authorization/github"
											title="Đăng nhập với Github" style="margin: 10px"> <img
											height="40" width="40"
											src="/client/img/provider/default-github.png" />
										</a>
									</div>
								</div>
								<div class="card-footer text-center py-3">
									<div class="small">
										<a href="/register">Need an account? Sign up!</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="/js/scripts.js"></script>
</body>
</html>
