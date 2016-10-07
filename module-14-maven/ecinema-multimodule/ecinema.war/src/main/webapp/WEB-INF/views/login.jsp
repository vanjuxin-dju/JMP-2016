<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form action="" method="post">
    Enter the password to access the app:<br>
    <input type="password" name="password" placeholder="Enter password" required><br>
    <button type="submit">Submit</button><br>
    <div>${errorMessage}</div>
</form>

