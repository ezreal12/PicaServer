<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>DB 조회 결과</title>
</head>
<body>

<c:forEach items="${result}" var="item">
	<table border="1">
		<tr>
			<td>${item}</td>
		</tr>
	</table>
  <br/>
</c:forEach>

</body>
</html>
