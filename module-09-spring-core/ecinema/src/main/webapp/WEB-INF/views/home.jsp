<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form id="addClientAndReservation" action="">
 Client:<br>
 <input type="text" id="firstName" placeholder="Enter first name" required><br>
 <input type="text" id="lastName" placeholder="Enter last name" required><br><br>
 Reservation:<br>
 <input type="text" id="filmName" placeholder="Enter film name" required><br>
 <input type="datetime" id="date" placeholder="Choose date and time" required><br>
 <!-- 32 seats per row, 32 rows -->
 <input type="number" min="1" max="1024" id="seat" placeholder="Choose a seat" required><br>
 <input type="number" min="0" step="0.01" id="price" placeholder="Enter a price" required><br>
 <input type="hidden" id="reservationId" value="0">
 <button id="save" type="submit">Save</button> <button type="reset">Clear form</button> <button id="update" type="button">Update</button><br><br>
</form>
<div>
    <table border="1">
        <thead>
            <tr>
                <th>Ticket number</th>
                <th>Film</th>
                <th>Date</th>
                <th>Seat</th>
                <th>Price</th>
                <th>Client ID</th>
                <th>Client first name</th>
                <th>Client last name</th>
                <th></th>
            </tr>
        </thead>
        <tfoot>
            <tr>
                <th>Ticket number</th>
                <th>Film</th>
                <th>Date</th>
                <th>Seat</th>
                <th>Price</th>
                <th>Client ID</th>
                <th>Client first name</th>
                <th>Client last name</th>
                <th></th>
            </tr>
        </tfoot>
        <tbody id="reservations">
        </tbody>
    </table>
</div>
<a href="${currentPage}?lang=EN">EN</a> <a href="${currentPage}?lang=RU">RU</a>
<div>i18n is working: <spring:message code="test.message" /></div>

<p><a href="logout">Logout</a></p>
<script type="text/javascript" src="/ecinema/resources/jquery-3.1.0.min.js"></script>
<script type="text/javascript" src="/ecinema/resources/engine.js"></script>
