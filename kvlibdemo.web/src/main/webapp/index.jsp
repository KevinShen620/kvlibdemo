<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="v" uri="http://kevsn.com/fns"%>
<html>
<body>
	<h2>Hello World!</h2>
	<a href="<c:url value="/name"/>">Name</a>
	<div>
		<h1>${v:name()}</h1>
	</div>
</body>

</html>
