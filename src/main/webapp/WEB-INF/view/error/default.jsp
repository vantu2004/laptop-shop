<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>

    <div class="site">
        <div class="sketch">
            <div class="bee-sketch red"></div>
            <div class="bee-sketch blue"></div>
        </div>

        <h1>
            Error:
            <small>
                <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "Sorry, an unexpected error has occurred." %>
            </small>
        </h1>
    </div>

</body>
</html>
