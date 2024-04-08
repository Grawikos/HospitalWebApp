<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Quad Function</title>
</head>
<body>

<h1>${message}</h1>

<h2>Quadratic function of the form ax^2 + bx + c = 0</h2>
<form:form method="post" action="evalQuadratic.html" modelAttribute="quadratic">
    <form:label path="rawA">a</form:label>
    <form:input path="rawA" />
    <br>
    <form:label path="rawB">b</form:label>
    <form:input path="rawB" />
    <br>
    <form:label path="rawC">c</form:label>
    <form:input path="rawC" />
    <br>
    <input type="submit" value="Evaluate roots"/>
</form:form>


</body>
</html>
