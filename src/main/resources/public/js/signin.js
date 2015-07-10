function signin() {
	var email = $("#email").val();
	var password = $("#password").val();
	var data = {
		email: email,
		password: password
	};
	$.ajax({
		type: 'POST',
		url: "/api/users/signin",
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
				$.cookie("message", "Signed In Successfully!");
				$.cookie("message_style", "success");
				window.location.href = location.origin + "/";
			}
		},
		error: function (obj, status, xhr) {
			console.log(obj);
			UIkit.notify("Internal Error: Could not Sign In", { status  : 'danger', pos: 'top-right' });
		}
	});
}

$(function () {
	$("#signin-form").on("submit", function (event) {
		signin();
		event.preventDefault();
	});
});