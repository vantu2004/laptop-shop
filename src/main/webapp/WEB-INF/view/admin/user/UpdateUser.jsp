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
<title>Update</title>
<link
	href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css"
	rel="stylesheet" />
<link href="/css/styles.css" rel="stylesheet" />

<!-- dùng jquery để preview upload file -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>

    $(document).ready(() => {
        const avatarFile = $("#avatarFile");
        avatarFile.change(function (e) {
            const imgURL = URL.createObjectURL(e.target.files[0]);
            $("#avatarPreview").attr("src", imgURL);
            $("#avatarPreview").css({ "display": "block" }); // Hiện ảnh preview
            
            // Ẩn ảnh người dùng nếu preview hiển thị
            $("#userAvatar").css({ "display": "none" });
        });
    });
    
</script>
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
							<h3>Update a user</h3>
							<hr />
							<form:form method="post" action="/admin/user/update"
								modelAttribute="newUser" enctype="multipart/form-data">

								<div class="mb-3" style="display: none">
									<label class="form-label">Id:</label>
									<form:input type="text" class="form-control" path="id" />
								</div>
								<div class="mb-3">
									<label class="form-label">Email:</label>
									<form:input type="email" class="form-control" path="email"
										disabled="true" />
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

								<div class="mb-3">
									<label for="avatarFile" class="form-label">Avatar:</label> <input
										class="form-control" type="file" id="avatarFile"
										accept=".png, .jpg, .jpeg" name="imageFile" />
								</div>

								<div class="mb-3 col-md-12">
									<img style="display: none; width: 100%;" alt="avatar preview"
										id="avatarPreview" />
								</div>

								<c:if test="${not empty avatar}">
									<div class="mb-3 col-md-12">
										<img src="/avatar/${avatar}" alt="User Image"
											class="img-thumbnail" style="object-fit: cover; width: 100%;"
											id="userAvatar">
									</div>
								</c:if>

								<div class="d-flex justify-content-end mt-3">
									<button type="submit" class="btn btn-primary">Update</button>
								</div>
							</form:form>
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