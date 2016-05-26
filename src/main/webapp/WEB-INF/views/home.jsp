<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>

<form action="<c:url value='/getlocation'></c:url>" method="post">

<p>Enter zipcode: <input type="text" value="" name="zipcode" /></p>
<p>Enter store type: <input type="text" value="" name="store_type" /></p>
<p>Enter store name: <input type="text" value="" name="store_name" /></p>
<p>Enter Radius: <input type="text" value="" name="radius" /></p>
<p><input type="submit" value="Submit" /> </p>
</form>

<c:forEach items="${listLocation}" var="location">
	<p><b><u>Country: ${location.country_name}</u></b></p>
	<c:forEach items="${location.stores}" var="store">
		<p>Store Name: ${store.store_name}</p>
	</c:forEach>	
</c:forEach>
</body>
</html>

