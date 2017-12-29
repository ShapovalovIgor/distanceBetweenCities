<%--
  Created by IntelliJ IDEA.
  User: igor
  Date: 14.07.15
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Select XML</title>
</head>
<body>
<form align="center" action="calculationOfDistance" method="POST">
<text>Input the coordinates</text>
  <p>
  <input type="text" name="nameCity1" maxlenght="50" required="on"/>
  </p>
  <text>latitude 1</text>
  <input type="number" name="latitudeA1" min="-90" max="90" size="50" required="on"/><text> . </text>
  <input type="number" name="latitudeA2" min="000" max="999" size="50" required="on"/>
  <text>  longitude 1</text>
  <input type="number" name="longitudeA1" min="-180" max="180" size="50" required="on"/><text> . </text>
    <input type="number" name="longitudeA2" min="000" max="999" size="50" required="on"/>
  <p>
  <input type="text" name="nameCity2" maxlenght="50" required="on"/>
  </p>
  <text>latitude 2</text>
  <input type="number" name="latitudeB1" min="-90" max="90" size="50" required="on"/><text> . </text>
  <input type="number" name="latitudeB2" min="000" max="999" size="50" required="on"/>
  <text>  longitude 2</text>
  <input type="number" name="longitudeB1" min="-180" max="180" size="50" required="on"/><text> . </text>
  <input type="number" name="longitudeB2" min="000" max="999" size="50" required="on"/>
  <br>

  <input type="submit" value="Send"/>

</form>
</body>
</html>
