package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.EmailConfirm;
import com.alta189.simple.gallery.objects.PasswordReset;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.objects.User;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;

@Controller
public class EmailHandler {
	@ResourceMapping("/verify/:user/:key")
	public void verify(Request request, Response response) {
		int userId = NumberUtils.toInt(request.params("user"), -1);
		String key = request.params("key");

		if (userId <= 0 || StringUtils.isEmpty(key)) {
			invalidRequest(request, response);
			return;
		}

		EmailConfirm emailConfirm = SimpleGalleryServer.getDatabase().select(EmailConfirm.class).where().equal("key", key).and().equal("user", userId).execute().findOne();
		if (emailConfirm == null) {
			invalidRequest(request, response);
			return;
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", userId).execute().findOne();
		if (user == null) {
			invalidRequest(request, response);
			return;
		}
		user.setVerifiedEmail(true);

		message(request, response, "Email Verified Successfully");
	}

	@ResourceMapping("/reset/:user/:key")
	public void reset(Request request, Response response) {
		int userId = NumberUtils.toInt(request.params("user"), -1);
		String key = request.params("key");

		if (userId <= 0 || StringUtils.isEmpty(key)) {
			invalidRequest(request, response);
			return;
		}

		PasswordReset passwordReset = SimpleGalleryServer.getDatabase().select(PasswordReset.class).where().equal("key", key).and().equal("user", userId).execute().findOne();
		if (passwordReset == null) {
			invalidRequest(request, response);
			return;
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", userId).execute().findOne();
		if (user == null) {
			invalidRequest(request, response);
			return;
		}

		// TODO Handle password request
		response.redirect("/");
	}

	private void invalidRequest(Request request, Response response) {
		message( request, response, "Invalid Request");
	}

	private void message(Request request, Response response, String message) {
		request.session(true);
		request.session().attribute("message", message);
		response.redirect("/");
	}
}
