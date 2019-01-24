<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://topjava/functions"%>
<html>
<head>
    <title>Meals</title>
</head>
<style>
    .exceeded{
        color: red;
    }
    .normal{
        color: green;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<a href="meals?action=new">Add meal</a>
<form id="MealViewForm" action="meals" method="post">
<table border="1" cellspacing="0" cellpadding="6">

    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">

            <td>
                ${f:formatLocalDateTime(meal.dateTime)}
            </td>
            <td>
                ${meal.description}
            </td>
            <td>
                ${meal.calories}
            </td>
            <td>
                <a href="meals?action=update&id=${meal.id}">Update</a>
            </td>
            <td>
                <a href="meals?action=remove&id=${meal.id}">Remove</a>
            </td>
        </tr>
    </c:forEach>
</table>
</form>
</body>
</html>

