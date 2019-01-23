<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Meals</title>
</head>
<style>
    .Exceeded{
        color: red;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <th>
            Description
        </th>
        <th>
            DateTime
        </th>
        <th>
            Calories
        </th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <c:if test="${meal.exceed}">
            <tr class="Exceeded">
        </c:if>
        <c:if test="${!meal.exceed}">
            <tr>
        </c:if>

            <td>
                <c:out value="${meal.description}"/>
            </td>
            <td>
                <c:out value="${meal.formattedDateTime}"/>
            </td>
            <td>
                <c:out value="${meal.calories}"/>
            </td>
        </tr>

    </c:forEach>

</table>
</body>
</html>

