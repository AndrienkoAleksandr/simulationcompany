<%@ taglib prefix="for" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 14.04.2014
  Time: 1:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/resources/css/default.css"  rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/resources/css/run.css"  rel="stylesheet" type="text/css" />
    <title>Run Simulation</title>
</head>
<body background="${pageContext.request.contextPath}/resources/img/background.jpg">
<%@include file="/header.jsp" %>
<div class = "text">
    <p>Company earned:</p>
    ${earned_money}
    <p>Company pay salary to staff:</p>
</div>
<div id = "ScrollBlock">
    <table width="400" cellspacing="2" border="1" bgcolor="olive">
        <c:forEach items="${company.getEmployees()}"  var = "employee">
            <tr>
                <td>
                        ${employee.getFirstName()} ${employee.getSecondName()}
                </td>
                <td>
                        ${employee.getSalary()}
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class = "text">
    <p>Company total profit:</p>
    ${company.getProfit()}
</div>
</body>
</html>
