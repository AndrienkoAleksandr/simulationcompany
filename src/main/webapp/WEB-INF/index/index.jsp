<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/resources/css/default.css"  rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/css/index.css"  rel="stylesheet" type="text/css" />
    <title>Index page</title>
</head>
<body background="${pageContext.request.contextPath}/resources/img/background.jpg">
<%@include file="/header.jsp" %>
<h1>Hello, this is a simple simulator the company</h1>
<p>
    We can  simulate work company during one month. Simulator creates company.
    Company earns some money and pays the salaries to staff. Interesting that the
    company can earn money less than the total salary of workers!
    This would mean that the company incurred a loss this month.
</p>
<form action = "${pageContext.request.contextPath}/run" method="get">
    <p>
        <label><b>Save date with:</b></label>
        <Br>
        <input class = "storage" type="radio" name="storage" value="Hibernate" > Hibernate <Br>
        <input class = "storage" type="radio" name="storage" value="JDBC" checked> JDBC <Br>
        <input class = "storage" type="radio" name="storage" value="Files"> Files <Br>
    </p>
    <p>
        Enter name company<input class = "name" type="text" name="company name" value="Adidas">
    </p>
    <div id = "label_start">
        <label>
            Enter "Run" if you want to start simulation.
        </label>
    </div>
    <div id = "div_start_button">
        <button type="submit">
            <img id = "start_button" src="${pageContext.request.contextPath}/resources/img/run.jpg" alt="Sorry, image not found">
        </button>
    </div>
</form>
</body>
</html>