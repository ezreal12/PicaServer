<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>DB 조회 결과</title>
</head>
<body>

<form action="dbResult.do", method="get" enctype="multipart/form-data">
<select name="tableName">
	<c:forEach items="${table}" var="item">
	<option value="${item}">${item}</option>
	</c:forEach>
</select>
<input type="submit" value="보기">
</form>

</body>
</html>
