<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Upload your image!  
</h1>

<form action="upload2" method="post" enctype="multipart/form-data">
  <input type="file" name="image" accept="image/png, image/jpeg" id="iamge"/>
  <input type="submit">
</form>


</body>
</html>