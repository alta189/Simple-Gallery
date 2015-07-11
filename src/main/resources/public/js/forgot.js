function forgot() {
	var email = $("#email").val();
	var data = {
		email: email
	};
	$.ajax({
		type: 'POST',
		url: "/api/users/forgot",
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
					message: "Reset Password Email Sent!",
					style: "success"
				}, function () {
					window.location.href = location.origin + "/";
				});
			}
		},
		error: function (obj, status, xhr) {
			console.log(obj);
			UIkit.notify("Internal Error: Could not Sign In", { status  : 'danger', pos: 'top-right' });
		}
	});
}

$(function () {
	$("#forgot-form").on("submit", function (event) {
		forgot();
		event.preventDefault();
	});
});