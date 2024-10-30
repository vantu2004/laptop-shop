<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Văn Tú - Dự án laptopshop" />
    <meta name="author" content="Văn Tú" />
    <title>Delete</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
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
                            <h3>Delete a products</h3>
                            <hr />
							<div class="alert alert-danger" role="alert">
								Are you sure to delete this product!
							</div>

							<form:form method="post" action="/admin/product/delete" modelAttribute="newProduct">
							
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
            </main>
            
            <jsp:include page="../layout/Footer.jsp" />
            
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>

</body>

</html>