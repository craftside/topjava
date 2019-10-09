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
<h2>Add meal</h2>

<form method="POST" action='listOfMeals' name="addMeal">
    Meal ID : <input type="text" readonly="readonly" name="id"
                     value="<c:out value="${meal.id}" />" /> <br />
    Year : <input
        type="text" name="year"
        value="<c:out value="${meal.year}" />" /> <br />
    Month : <input
        type="text" name="month"
        value="<c:out value="${meal.month}" />" /> <br />
    Day : <input
        type="text" name="firstName"
        value="<c:out value="${meal.day}" />" /> <br />
    Hour : <input
        type="text" name="firstName"
        value="<c:out value="${meal.hour}" />" /> <br />
    Min : <input
        type="text" name="firstName"
        value="<c:out value="${meal.min}" />" /> <br />



    Description : <input
        type="text" name="desc"
        value="<c:out value="${meal.desc}" />" /> <br />
    Calories : <input
        type="text" name="desc"
        value="<c:out value="${meal.calories}" />" /> <br />
    <input type="submit" value="Submit" />
</form>

</body>
</html>