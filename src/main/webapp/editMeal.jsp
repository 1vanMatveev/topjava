<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 24.01.2019
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${action_name} meal</title>
</head>
<body>
<h3><a href="meals">Back</a></h3>
<h2>${action_name} meal</h2>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <table>
        <tr>
            <td>
                Description
            </td>
            <td>
                <input type="text" name="description" value="${meal.description}">
            </td>
        </tr>
        <tr>
            <td>
                DateTime
            </td>
            <td>
                <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
            </td>
        </tr>
        <tr>
            <td>
                Calories
            </td>
            <td>
                <input type="text" name="calories" value="${meal.calories}">
            </td>
        </tr>
        <tr>
            <td>
                <input type="reset" value="Reset">
            </td>
            <td>
                <input type="submit" name="action_save" value="Save">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
