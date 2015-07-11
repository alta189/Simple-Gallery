var messages = {
	check: function () {
		$.ajax({
			type: 'GET',
			url: "/api/messages/get",
			dataType: 'json',
			cache: false,
			success: function (obj, status, xhr) {
				if (!obj.error) {
					obj.result.forEach(function (message) {
						UIkit.notify({
							message: message.message,
							status: message.style,
							timeout: message.timeout,
							pos: message.position
						});
					});
				}
			},
			error: function (text, status, xhr) {
				console.log(text);
			}
		});
	},
	send: function (message, callback) {
		$.ajax({
			type: 'PUT',
			url: "/api/messages/create",
			contentType: 'application/json',
			dataType: 'json',
			data: JSON.stringify(message),
			cache: false,
			success: function (obj, status, xhr) {
				console.log(JSON.stringify(obj));
				if (typeof callback === "function") {
					callback(!obj.error);
				}
			},
			error: function (text, status, xhr) {
				if (typeof callback === "function") {
					callback(false);
				}
			}
		});
	}

};
