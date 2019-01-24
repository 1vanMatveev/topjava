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
<form id="MealViewForm" action="meals" method="post">
<table border="1" cellspacing="0" cellpadding="6">

    <tr>
        <th>DateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th><input type="submit" name="action_new" value="New"></th>
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
                <input type="submit" name="action_update_${meal.id}" value="Update">
            </td>
            <td>
                <input type="submit" name="action_remove_${meal.id}" value="Remove">
            </td>
        </tr>
    </c:forEach>
</table>
</form>
</body>
</html>

