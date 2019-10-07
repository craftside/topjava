<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.time.format.DateTimeFormatter" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<br><br>
<table border="1">
    <tr>
        <td>DateTime</td>
        <td>Description</td>
        <td>Calories</td>
        <td>Excess</td>
    </tr>
    <c:forEach items="${mealsTo}" var="tr">
        <tr <c:if test="${tr.excess}">bgcolor="#00FA9A"</c:if>
            <c:if test="${!tr.excess}">bgcolor="#F08080"</c:if>
        >
            <td>${tr.dateTime.format(formatter)}</td>
            <td>${tr.description}</td>
            <td>${tr.calories}</td>
            <td>${tr.excess}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>