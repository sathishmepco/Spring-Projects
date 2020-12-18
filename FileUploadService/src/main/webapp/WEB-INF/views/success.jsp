<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Success</title>
</head>
<body>

<h1>Successfully uploaded!</h1>  

<img src="${filename}"/>  

<!-- Upload action should be changed to "upload2" -->
<%-- <img src="data:image/jpeg;base64,${filename}"/> --%>

</body>
</html>