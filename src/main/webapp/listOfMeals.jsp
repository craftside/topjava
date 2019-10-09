<%--
  Created by IntelliJ IDEA.
  User: TolstenkovPS
  Date: 09.10.2019
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table border=1>
    <thead>
    <tr>
        <th>Meal Id</th>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealsTo}" var="tr">
        <tr bgcolor=<c:out value="${tr.excess ? 'red' : 'green'}"/>>
            <td><c:out value="${tr.id}" /></td>
            <td><c:out value="${tr.dateTime.format(formatter)}" /></td>
            <td><c:out value="${tr.description}" /></td>
            <td><c:out value="${tr.calories}" /></td>
            <td><a href="mealServlet?action=edit&id=<c:out value="${tr.id}"/>">Update</a></td>
            <td><a href="mealServlet?action=delete&id=<c:out value="${tr.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="mealServlet?action=insert">Add Meal</a></p>
</body>
</html>