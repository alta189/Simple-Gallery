function signup() {
	var name = $("#name").val();
	var email = $("#email").val();
	var password = $("#password").val();
	var emailConfirm = $("#confirm-email").val();
	var passwordConfirm = $("#confirm-password").val();

	var data = {
		name: name,
		email: email,
		password: password,
		confirm_password : passwordConfirm,
		confirm_email: emailConfirm
	};

	$.ajax({
		type: 'POST',
		url: "/api/users/signup",
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
					message: "Signed Up Successfully!",
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
	$("#signup-form").on("submit", function (event) {
		signup();
		event.preventDefault();
	});
});