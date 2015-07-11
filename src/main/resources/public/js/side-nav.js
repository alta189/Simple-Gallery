function loadUserInfo() {
	$.ajax({
		type: 'GET',
		url: "/api/users/info",
		dataType: 'json',
		cache: false,
		success: function(obj, status, xhr) {
			if (!obj.error) {
				$("#user-name").text(obj.result.name);
				$("#user-email").text(obj.result.email);
				$("#user-info-panel").removeClass("hidden");

				$("#nav-user-control").html('<a href="/signout"><i class="uk-icon-sign-in"></i> &nbsp;Sign Out</a>');
			}
		},
		error: function (text, status, xhr) {
			console.log(text);
		}
	});
}

$(function () {
	loadUserInfo();
	messages.check();
});