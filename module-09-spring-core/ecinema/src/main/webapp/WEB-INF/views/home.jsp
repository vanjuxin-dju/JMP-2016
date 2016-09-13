<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Home</title>
        <script type="text/javascript" src="jquery-3.1.0.min.js"></script>
        <script type="text/javascript" src="engine.js"></script>
    </head>
    <body>
        <h1>Reservation!</h1>
        <p>This is the homepage!</p>
        <form id="addClientAndReservation" action="">
	        Client:<br>
	        <input type="text" id="firstName" placeholder="Enter first name"><br>
	        <input type="text" id="lastName" placeholder="Enter last name"><br><br>
	        Reservation:<br>
	        <input type="text" id="filmName" placeholder="Enter film name"><br>
	        <input type="datetime" id="date" placeholder="Choose date and time"><br>
	        <input type="number" id="seat" placeholder="Choose a seat"><br>
	        <input type="number" id="price" placeholder="Enter a price"><br>
	        <button id="save" type="submit">Save</button><br>
	        <button type="reset">Clear form</button>
        </form>
        <div id="results"></div>
    </body>
</html>
