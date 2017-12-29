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
<form enctype="multipart/form-data" align="center" action="readXML" method="POST">
<text>Upload a XML file with information about the distance between cities</text>
  <br> <br>
  <input type="file" name="file"  accept=text/xml size="1"/>
  <input type="submit" value="Send"/>
</form>
</body>
</html>
