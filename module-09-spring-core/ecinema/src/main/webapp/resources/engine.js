$(function() {
	$("#addClientAndReservation").submit(function(e) {
		e.preventDefault();
		
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val();
		
		var filmName = $("#filmName").val();
		var date = $("#date").val();
		var seat = $("#seat").val();
		var price = $("#price").val();
		
		$.post("clients", { "firstName" : firstName, "lastName" : lastName }).done(function(clientId) {
			
		});
	});
});