function modalPromptSelect(text, list, value, onsubmit, options) {

	onsubmit = UIkit.$.isFunction(onsubmit) ? onsubmit : function (value) {
	};
	options = UIkit.$.extend(true, {bgclose: false, keyboard: false, modal: false, labels: UIkit.modal.labels}, options);

	var builder = [];
	list.forEach(function (item) {
		builder.push('<option value="', item.value, '">', item.display, '</option>');
	});

	var modal = UIkit.modal.dialog(([
			text ? '<div class="uk-modal-content uk-form">' + String(text) + '</div>' : '',
			'<div class="uk-margin-small-top uk-modal-content uk-form"><p ><select id="modal-select" style="width: 558px;">', builder.join(""), '</select></div></p></div>',
			'<div class="uk-modal-footer uk-text-right"><button class="uk-button uk-button-primary js-modal-ok">' + options.labels.Ok + '</button> <button class="uk-button uk-modal-close">' + options.labels.Cancel + '</button></div>'
		]).join(""), options),

		input = modal.element.find("select");
	input.select2();
	input.select2('val', value);

	modal.element.find(".js-modal-ok").on("click", function () {
		if (onsubmit(input.val(), input.find('option:selected').text()) !== false) {
			modal.hide();
		}
	});

	modal.on('show.uk.modal', function () {
		setTimeout(function () {
			input.focus();
		}, 50);
	});

	modal.show();
}

function validPassword(password) {
	return /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=])(?=\S+$).{8,}$/.test(password);
}

function promptPassword(text, value, onsubmit, options) {

	onsubmit = UIkit.$.isFunction(onsubmit) ? onsubmit : function (value) {
	};
	options = UIkit.$.extend(true, {bgclose: false, keyboard: false, modal: false, labels: UIkit.modal.labels}, options);

	var modal = UIkit.modal.dialog(([
			text ? '<div class="uk-modal-content uk-form">' + String(text) + '</div>' : '',
			'<div class="uk-margin-small-top uk-modal-content uk-form"><p><div class="uk-form-row"><input type="password" id="prompt-modal-password" class="uk-width-1-1" placeholder="Password"></div><div class="uk-form-row"><input type="password"  id="prompt-modal-confirm" class="uk-width-1-1" placeholder="Confirm"></div></p></div>',
			'<div class="uk-modal-footer uk-text-right"><button class="uk-button uk-button-primary js-modal-ok">' + options.labels.Ok + '</button> <button class="uk-button uk-modal-close">' + options.labels.Cancel + '</button></div>'
		]).join(""), options),

		input = modal.element.find("#prompt-modal-password").val(value || '').on('keyup', function (e) {
			if (e.keyCode == 13) {
				modal.element.find(".js-modal-ok").trigger('click');
			}
		}).on('input', function (e) {
			if (validPassword(input.val())) {
				input.removeClass("uk-form-danger");
				input.addClass("uk-form-success");
			} else {
				input.removeClass("uk-form-success");
				input.addClass("uk-form-danger");
			}
		}),

		confirm = modal.element.find("#prompt-modal-confirm").val(value || '').on('keyup', function (e) {
			if (e.keyCode == 13) {
				modal.element.find(".js-modal-ok").trigger('click');
			}
		}).on('input', function (e) {
			if (validPassword(confirm.val())) {
				confirm.removeClass("uk-form-danger");
				confirm.addClass("uk-form-success");
			} else {
				confirm.removeClass("uk-form-success");
				confirm.addClass("uk-form-danger");
			}
		});

	modal.element.find(".js-modal-ok").on("click", function () {
		var valid = true;
		if (!validPassword(confirm.val())) {
			confirm.removeClass("uk-form-success");
			confirm.addClass("uk-form-danger");
			valid = false;
		}
		if (!validPassword(input.val())) {
			input.removeClass("uk-form-success");
			input.addClass("uk-form-danger");
			valid = false;
		}
		if (!valid) {
			return;
		}
		if (onsubmit(input.val(), confirm.val()) !== false) {
			modal.hide();
		}
	});

	modal.on('show.uk.modal', function () {
		setTimeout(function () {
			input.focus();
		}, 50);
	});

	modal.show();
}

var _ROLES = [];

function loadRoles() {
	$.ajax({
		type: 'GET',
		url: "/api/users/roles",
		dataType: 'json',
		cache: false,
		success: function (obj, status, xhr) {
			console.log(JSON.stringify(obj));
			if (!obj.error) {
				_ROLES = obj.result;
			}
		},
		error: function (text, status, xhr) {
			console.log(text);
		}
	});
}

function loadTypes() {
	$.ajax({
		type: 'GET',
		url: "/api/admin/validation/rules/types",
		dataType: 'json',
		cache: false,
		success: function (obj, status, xhr) {
			console.log(JSON.stringify(obj));
			if (!obj.error) {
				var builder = [];
				obj.result.forEach(function (type) {
					builder.push('<option value="', type.name, '">', type.value, '</option>')
				});
				var $validationSelect = $("#validation-type");
				$validationSelect.html(builder.join(""));
				$validationSelect.select2();
				if (obj.result.length > 1) {
					$validationSelect.select2('val', obj.result[0].name);
				}
			}
		},
		error: function (text, status, xhr) {
			console.log(text);
		}
	});
}

function loadSettings() {
	$.ajax({
		type: 'GET',
		url: "/api/admin/settings",
		dataType: 'json',
		cache: false,
		success: function (obj, status, xhr) {
			console.log(JSON.stringify(obj));
			if (!obj.error) {
				$("#gallery-name").val(obj.result.gallery.name);
				$("#gallery-description").val(obj.result.gallery.description);
				$("#gallery-host").val(obj.result.gallery.host);
				$("#gallery-webadmin").val(obj.result.gallery.webadmin);

				$("#sendgrid-username").val(obj.result.sendgrid.username);
				$("#sendgrid-password").val(obj.result.sendgrid.password);
				$("#sendgrid-apikey").val(obj.result.sendgrid.apikey);

				$("#email-verify").val(obj.result.address.validation);
				$("#email-reset").val(obj.result.address.reset);
				$("#email-contact-us").val(obj.result.address.contact_us);

				$("#template-verify-subject").val(obj.result.subject.validation);
				$("#template-reset-subject").val(obj.result.subject.reset);
			}
		},
		error: function (text, status, xhr) {
			console.log(text);
		}
	})
}

function loadUsers() {
	var $usersList = $("#users-list");
	$.ajax({
		type: 'GET',
		url: "/api/users/list",
		dataType: 'json',
		cache: false,
		success: function (obj, status, xhr) {
			console.log(JSON.stringify(obj));
			if (!obj.error) {
				var builder = [];
				obj.result.forEach(function (user) {
					builder.push('<tr class="uk-table-middle uk-text-center">');
					builder.push('<td><span id="user-name-', user.id, '" onclick="userName(', user.id, ',this)">', user.name, '</span></td>');
					builder.push('<td><span id="user-email-', user.id, '" onclick="userEmail(', user.id, ',this)">', user.email, '</span></td>');
					builder.push('<td>', user.verified_email, '</td>');
					builder.push('<td><span id="user-role-', user.id, '" onclick="userRole(', user.id, ',this)" data-role="', user.role.value, '">', user.role.name, '</span></td>');
					builder.push('<td><a onclick="userChangePassword(', user.id, ',this)" class="uk-icon-hover uk-icon-lock"></a></td>');
					builder.push('<td><a onclick="userDelete(', user.id, ',this)" class="uk-icon-hover uk-icon-close"></a></td>');
					builder.push('</tr>');
				});
				$usersList.html(builder.join(""));
			}
		},
		error: function (text, status, xhr) {
			console.log(text);
			$usersList.html('<tr><td class="uk-width-1-1">Error Loading Users</td></tr>');
		}
	});
}

function loadValidations() {
	var $validationList = $("#validations-list");
	$.ajax({
		type: 'GET',
		url: "/api/admin/validation/rules",
		dataType: 'json',
		cache: false,
		success: function (obj, status, xhr) {
			console.log(JSON.stringify(obj));
			if (!obj.error) {
				var builder = [];
				obj.result.forEach(function (validation) {
					builder.push('<tr class="uk-table-middle uk-text-center">');
					builder.push('<td>', validation.rule, '</td>');
					builder.push('<td>', validation.type.value, '</td>');
					builder.push('<td><a onclick="validationDelete(', validation.id, ',this)" class="uk-icon-hover uk-icon-close"></a></td>');
					builder.push('</tr>');
				});
				$validationList.html(builder.join(""));
			}
		},
		error: function (text, status, xhr) {
			console.log(text);
			$validationList.html('<tr><td class="uk-width-1-1">Error Loading Users</td></tr>');
		}
	});
}

function validationDelete(id, e) {
	UIkit.modal.confirm("Are you sure you want to delete this validation rule?", function () {
		$.ajax({
			type: 'GET',
			url: "/api/admin/validation/rule/remove/" + id,
			dataType: 'json',
			cache: false,
			success: function (obj, status, xhr) {
				if (obj.error) {
					UIkit.notify({
						message: obj.errorMessage,
						status: 'danger',
						timeout: 5000,
						pos: 'top-right'
					});
				} else {
					UIkit.notify({
						message: 'Successfully Removed!',
						status: 'success',
						timeout: 5000,
						pos: 'top-right'
					});
					$(e).parent().parent().remove();
				}
			},
			error: function (obj, status, xhr) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			}
		});
	});
}

function saveSettings(settings, callback) {
	$.ajax({
		type: 'PUT',
		url: "/api/admin/settings",
		contentType: 'application/json',
		dataType: 'json',
		data: JSON.stringify(settings),
		cache: false,
		success: function (obj, status, xhr) {
			if (typeof callback === "function") {
				callback(obj);
			}
		},
		error: function (obj, status, xhr) {
			if (typeof callback === "function") {
				callback(obj);
			}
		}
	});
}

function addValidation(validation, callback) {
	$.ajax({
		type: 'PUT',
		url: "/api/admin/validation/rule",
		contentType: 'application/json',
		dataType: 'json',
		data: JSON.stringify(validation),
		cache: false,
		success: function (obj, status, xhr) {
			if (typeof callback === "function") {
				callback(obj);
			}
		},
		error: function (obj, status, xhr) {
			if (typeof callback === "function") {
				callback(obj);
			}
		}
	});
}

function userName(id, e) {
	var $name = $(e);
	var name = $name.html();
	UIkit.modal.prompt("Name:", name, function (value) {
		var user = {
			id: id,
			name: value
		};
		updateUser(user, function (obj) {
			if (obj.error) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			} else {
				UIkit.notify({
					message: 'Updated User\'s Name!',
					status: 'success',
					timeout: 5000,
					pos: 'top-right'
				});

				$name.html(value);
			}
		});
	});
}

function userEmail(id, e) {
	var $email = $(e);
	var email = $email.html();
	UIkit.modal.prompt("Email:", email, function (value) {
		var user = {
			id: id,
			email: value
		};
		updateUser(user, function (obj) {
			if (obj.error) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			} else {
				UIkit.notify({
					message: 'Updated User\'s Email!',
					status: 'success',
					timeout: 5000,
					pos: 'top-right'
				});

				$email.html(value);
			}
		});
	});
}

function userRole(id, e) {
	var $role = $(e);
	var value = $role.data('role');
	modalPromptSelect("Role:", _ROLES, value, function (value, text) {
		var user = {
			id: id,
			role: text
		};
		updateUser(user, function (obj) {
			if (obj.error) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			} else {
				UIkit.notify({
					message: 'Updated User\'s Role!',
					status: 'success',
					timeout: 5000,
					pos: 'top-right'
				});

				$role.html(text);
				$role.data('role', value);
			}
		});
	});
}

function updateUser(user, callback) {
	$.ajax({
		type: 'PUT',
		url: "/api/users/update",
		contentType: 'application/json',
		dataType: 'json',
		data: JSON.stringify(user),
		cache: false,
		success: function (obj, status, xhr) {
			if (typeof callback === "function") {
				callback(obj);
			}
		},
		error: function (obj, status, xhr) {
			if (typeof callback === "function") {
				callback(obj);
			}
		}
	});
}

function userDelete(id, e) {
	UIkit.modal.confirm("Are you sure you want to delete this user?", function () {
		$.ajax({
			type: 'GET',
			url: "/api/users/remove/" + id,
			dataType: 'json',
			cache: false,
			success: function (obj, status, xhr) {
				if (obj.error) {
					UIkit.notify({
						message: obj.errorMessage,
						status: 'danger',
						timeout: 5000,
						pos: 'top-right'
					});
				} else {
					UIkit.notify({
						message: 'Successfully Removed!',
						status: 'success',
						timeout: 5000,
						pos: 'top-right'
					});
					$(e).parent().parent().remove();
				}
			},
			error: function (obj, status, xhr) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			}
		});
	});
}

function userChangePassword(id, e) {
	promptPassword("Change Password:", "", function (pass, confirm) {
		var user = {
			id: id,
			password: pass,
			confirm: confirm
		};
		updateUser(user, function (obj) {
			if (obj.error) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			} else {
				UIkit.notify({
					message: 'Updated User\'s Password!',
					status: 'success',
					timeout: 5000,
					pos: 'top-right'
				});
			}
		});
	});
}

$(function () {
	loadRoles();
	loadSettings();
	loadUsers();
	loadValidations();
	loadTypes();

	$("#form-settings").on('submit', function (e) {
		var settings = {
			gallery: {
				name: $("#gallery-name").val(),
				description: $("#gallery-description").val(),
				host: $("#gallery-host").val(),
				webadmin: $("#gallery-webadmin").val()
			},
			sendgrid: {
				username: $("#sendgrid-username").val(),
				password: $("#sendgrid-password").val(),
				apikey: $("#sendgrid-apikey").val()
			},
			address: {
				validation: $("#email-verify").val(),
				reset: $("#email-reset").val(),
				contact_us: $("#email-contact-us").val()
			}
		};
		saveSettings(settings, function (obj) {
			if (obj.error) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			} else {
				UIkit.notify({
					message: 'Successfully Saved!',
					status: 'success',
					timeout: 5000,
					pos: 'top-right'
				});
			}
		});
		e.preventDefault();
	});

	$("#form-verify").on('submit', function (e) {
		var settings = {
			subject: {
				validation: $("#template-verify-subject").val()
			},
			template: {
				validation: $("#template-verify").val()
			}
		};
		saveSettings(settings, function (obj) {
			if (obj.error) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			} else {
				UIkit.notify({
					message: 'Successfully Saved!',
					status: 'success',
					timeout: 5000,
					pos: 'top-right'
				});
			}
		});
		e.preventDefault();
	});

	$("#form-reset").on('submit', function (e) {
		var settings = {
			subject: {
				reset: $("#template-reset-subject").val()
			},
			template: {
				reset: $("#template-reset").val()
			}
		};
		saveSettings(settings, function (obj) {
			if (obj.error) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			} else {
				UIkit.notify({
					message: 'Successfully Saved!',
					status: 'success',
					timeout: 5000,
					pos: 'top-right'
				});
			}
		});
		e.preventDefault();
	});

	$("#form-validation-add").on('submit', function (e) {
		var validation = {
			rule: $('#validation-rule').val(),
			type: $('#validation-type').select2('val')
		};
		addValidation(validation, function (obj) {
			console.log(JSON.stringify(obj));
			if (obj.error) {
				UIkit.notify({
					message: obj.errorMessage,
					status: 'danger',
					timeout: 5000,
					pos: 'top-right'
				});
			} else {
				UIkit.notify({
					message: 'Successfully Added!',
					status: 'success',
					timeout: 5000,
					pos: 'top-right'
				});

				var builder = [];
				builder.push('<tr class="uk-table-middle uk-text-center">');
				builder.push('<td>', obj.result.rule, '</td>');
				builder.push('<td>', obj.result.type.value, '</td>');
				builder.push('<td><a onclick="validationDelete(', obj.result.id, ',this)" class="uk-icon-hover uk-icon-close"></a></td>');
				builder.push('</tr>');
				$("#validations-list").append($(builder.join("")));
			}
		});
		e.preventDefault();
	});
});
