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

function checkMessage() {
	if ($.cookie("message")) {
		var style = (typeof $.cookie("message_style") === 'undefined') ? "info" : $.cookie("message_style");
		var position = (typeof $.cookie("message_position") === 'undefined') ? "top-right" : $.cookie("message_position");
		var timeout = (typeof $.cookie("message_position") === 'undefined') ? 3000 : parseInt($.cookie("message_timeout"), 10);
		UIkit.notify({
			message : $.cookie("message"),
			status  : style,
			timeout : timeout,
			pos     : position
		});
		$.removeCookie("message");
		$.removeCookie("message_style");
		$.removeCookie("message_position");
		$.removeCookie("message_timeout");
	}
}

$(function () {
	loadUserInfo();
	checkMessage();
});