<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/4
  Time: 9:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${info.msg}
<form action="locationData/add_LocationDataByExcel.do" enctype="multipart/form-data" method="post">
    <input type="file" name="excelfile" />
    <input type="submit" value="submit"/>
</form>
</body>
</html>
