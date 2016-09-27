$(function() {
	$("#update").prop("disabled", true);
	function formatDate(rawDate) {
		var d = new Date(rawDate);
		var yyyy = d.getFullYear();
		var mm = d.getMonth() + 1;
		mm = (parseInt(mm / 10) == 0 ? "0" + mm : mm);
		var dd = d.getDate();
		dd = (parseInt(dd / 10) == 0 ? "0" + dd : dd);
		var hh = d.getHours();
		hh = (parseInt(hh / 10) == 0 ? "0" + hh : hh);
		var m = d.getMinutes();
		m = (parseInt(m / 10) == 0 ? "0" + m : m);
		return yyyy + "-" + mm + "-" + dd + " " + hh + ":" + m;
	}
	$("#addClientAndReservation").submit(function(e) {
		e.preventDefault();
		
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val();
		
		var filmName = $("#filmName").val();
		var date = $("#date").val();
		var seat = $("#seat").val();
		var price = $("#price").val();
		
		$.post("clients", { 
			"firstName" : firstName, 
			"lastName" : lastName 
		}).done(function(clientId) {
			$.post("reservations", { 
				"filmName" : filmName, 
				"date" : date, 
				"seat" : seat, 
				"price" : price, 
				"clientId" : clientId 
			}).done(function(reservationId) {
				addRow(reservationId, filmName, date, seat, price, clientId, firstName, lastName);
				$("#firstName").val("");
				$("#lastName").val("");
				$("#filmName").val("");
				$("#date").val("");
				$("#seat").val("");
				$("#price").val("");
			});
		});
	});
	$.get("reservations").done(function(data) {
		$(data).each(function() {
			addRow(this.id, this.filmName, formatDate(this.date), this.seat, this.price, this.client.id, this.client.firstName, this.client.lastName);
		});
	});
	
	function addRow(id, filmName, date, seat, price, clientId, firstName, lastName) {
		var clientWasNotInTheList = ($("#c" + clientId).length == 0);
		$("<tr />")
			.append($("<td />").append($("<a />").attr("href", "reservations/" + id).attr("id", "r" + id).text(id)))
			.append($("<td />").text(filmName))
			.append($("<td />").text(date))
			.append($("<td />").text(seat))
			.append($("<td />").text(price))
			.append($("<td />").append((clientWasNotInTheList ? $("<a />").attr("href", "clients/" + clientId).attr("id", "c" + clientId).text(clientId) : clientId)))
			.append($("<td />").text(firstName))
			.append($("<td />").text(lastName))
			.append($("<td />").append($("<button />").attr("type", "button").attr("id", "res" + id).text("Delete")).append($("<button />").attr("type", "button").attr("id", "edt" + id).text("Edit")))
			.appendTo("#reservations");
		$("#res" + id).click(function(e) {
			var tr = $(this).parent().parent();
			$.ajax({
				url : "reservations/" + id,
				type : "DELETE"
			}).done(function(data) {
				$(tr).remove();
			});
		});
		$("#r" + id).click(function(e) {
			e.preventDefault();
			$.get($(this).attr("href")).done(function(data) {
				showInfoAboutReservation(data);
			});
		});
		if (clientWasNotInTheList) {
			$("#c" + clientId).click(function(e) {
				e.preventDefault();
				$.get($(this).attr("href")).done(function(data) {
					showInfoAboutClient(data);
				});
			});
		}
		$("#edt" + id).click(function(e) {
			$("#firstName").val(firstName);
			$("#lastName").val(lastName);
			$("#clientId").val(clientId);
			
			$("#filmName").val(filmName);
			$("#date").val(date);
			$("#seat").val(seat);
			$("#price").val(price);
			$("#reservationId").val(id);
			
			$("#save").prop("disabled", true);
			$("#update").prop("disabled", false);
		});
	}
	
	$("#update").click(function(e) {
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val();
		
		var filmName = $("#filmName").val();
		var date = $("#date").val();
		var seat = $("#seat").val();
		var price = $("#price").val();
		var reservationId = $("#reservationId").val();
		
		var tr = $("#edt" + reservationId).parent().parent();
		
		$.post("clients", { 
			"firstName" : firstName, 
			"lastName" : lastName 
		}).done(function(clientId) {
			$.ajax({
				url : "reservations/" + reservationId,
				type : "PUT",
				data: { 
					"filmName" : filmName, 
					"date" : date, 
					"seat" : seat, 
					"price" : price, 
					"clientId" : clientId 
				}
			}).done(function() {
				var columns = $(tr).children();
				$(columns[1]).text(filmName);
				$(columns[2]).text(date);
				$(columns[3]).text(seat);
				$(columns[4]).text(price);
				$(columns[5]).html("");
				var clientWasNotInTheList = ($("#c" + clientId).length == 0);
				$(columns[5]).append((clientWasNotInTheList ? $("<a />").attr("href", "clients/" + clientId).attr("id", "c" + clientId).text(clientId) : clientId));
				$(columns[6]).text(firstName);
				$(columns[7]).text(lastName);
				
				$("#firstName").val("");
				$("#lastName").val("");
				$("#filmName").val("");
				$("#date").val("");
				$("#seat").val("");
				$("#price").val("");
				$("#reservationId").val("0");
				
				$("#save").prop("disabled", false);
				$("#update").prop("disabled", true);
				
				if (clientWasNotInTheList) {
					$("#c" + clientId).click(function(e) {
						e.preventDefault();
						$.get($(this).attr("href")).done(function(data) {
							showInfoAboutClient(data);
						});
					});
				}
			});
		});
	});
	
	function showInfoAboutReservation(data) {
		var result = "Reservation \nID: " + data.id +
			"\nFilm name: " + data.filmName + 
			"\nDate: " + formatDate(data.date) + 
			"\nSeat: " + data.seat + 
			"\nPrice: " + data.price + 
			"\n\nClient \nID: " + data.client.id + 
			"\nFirst name: " + data.client.firstName + 
			"\nLast name: " + data.client.lastName;
		alert(result);
	}
	
	function showInfoAboutClient(data) {
		var result = "Client \nID: " + data.id + 
			"\nFirst name: " + data.firstName + 
			"\nLast name: " + data.lastName;
		alert(result);
	}
});