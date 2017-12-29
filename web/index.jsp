<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ru.shapovalov.Servlets.ReadDatabase"%>
<%--
  Created by IntelliJ IDEA.
  User: igor
  Date: 18.03.15
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="error.jsp" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>DistanceBetweenCities ${infoXML}</title>    <style>
    body {
        background-image: url(res/bg.jpg);
        background-color: #eff4f4;
    }
    #left {
        float: left;
        width: 50%;
        height: 100%;
        overflow: auto;
        /*background: red;*/
    }
    #right {
        height: 100%;
        margin-left: auto;
        /*overflow: auto;*/
        /*background: green;*/
    }
</style>
</head>
<body>
</br>
<%@include file="selectXML.jsp"%>
${infoXML}
<%@include file="inputTheCoordinates.jsp"%>
<form align="center">
    <p>${text1}${nameCity1}${text2}${nameCity2}</p>
    <p>${m}${text3} </p>
    <p>${text4}${km}${text5}</p>
</form>
<div id="left">
<form action="readDatabase" method="POST">
    <p><text>Select the cities to calculate the distance between them:</text></p>
    <input type="submit" name="readCity" value="Fill in the list of cities">
    <p>
        <select name="selectSizeCity1">
            <option disabled>City A</option>
            <c:forEach items="${cityList}" var="city">
                <option>${city['key']}</option>
            </c:forEach>
        </select>
        <select name="selectSizeCity2">
            <option disabled>City B</option>
            <c:forEach items="${cityList}" var="city">
                <option>${city['key']}</option>
            </c:forEach>
        </select></p>
    <input type="submit" name="Select" value="Calculate">
</form>
</div>
<div id="right">
<form action="readDatabaseCityBetween" method="POST">
    </br><p><text>Choose a city to see the distance between them:</text></p>
    <input type="submit" name="readCity" value="Fill in the list of cities">
    <p>
        <select name="selectSizeCity3">
            <option disabled>City A</option>
            <c:forEach items="${cityList1}" var="city">
                <option>${city}</option>
            </c:forEach>
        </select>
        <input type="submit" name="selectCity" value="choose city">
        <select name="selectSizeCity4">
            <option disabled>City B</option>
            <c:forEach items="${cityList2}" var="city">
                <option>${city['key']}</option>
            </c:forEach>
        </select></p>
    <input type="submit" name="viewSelect" value="View distance">
</form>
</div>
</body>
</html>