function reset() {
	var email = $("#email").val();
	var password = $("#password").val();
	var confirm = $("#confirm").val();
	var key = $("#key").val();
	var data = {
		email: email,
		password: password,
		confirm: confirm,
		key: key
	};
	$.ajax({
		type: 'POST',
		url: "/api/users/reset",
		dataType: 'json',
		data: data,
		cache: false,
		success: function(obj, status, xhr){
			console.log(JSON.stringify(obj));
			if (obj.error) {
				UIkit.notify(obj.errorMessage, { status  : 'danger', pos: 'top-right'  });
			} else {
				if (!location.origin) {
					location.origin = location.protocol + "//" + location.host;
				}
				messages.send({
					message: "Reset Password Successful!",
					style: "success"
				}, function () {
					window.location.href = location.origin + "/signin";
				});
			}
		},
		error: function (obj, status, xhr) {
			console.log(obj);
			UIkit.notify("Internal Error: Could not Reset Password", { status  : 'danger', pos: 'top-right' });
		}
	});
}

$(function () {
	$("#reset-form").on("submit", function (event) {
		reset();
		event.preventDefault();
	});
});