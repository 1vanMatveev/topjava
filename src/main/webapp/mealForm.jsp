<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${title}</title>
    <style>
        dd{
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
        dt{
            display: inline-block;
            width: 170px;
        }
        dl{
            background: #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>${title}</h2>
<hr>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>DateTime</dt>
        <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}"></dd>
    </dl>
    <dl>
        <dt>Description</dt>
        <dd><input type="text" name="description" value="${meal.description}" required></dd>
    </dl>
    <dl>
        <dt>Calories</dt>
        <dd><input type="text" name="calories" value="${meal.calories}" required></dd>
    </dl>
<input type="submit" value="Save">
<input type="button" onclick="window.history.back()" value="Cancel">
</form>
</body>
</html>
