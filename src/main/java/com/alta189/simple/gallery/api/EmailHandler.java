package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.TemplateEngine;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.EmailConfirm;
import com.alta189.simple.gallery.objects.PasswordReset;
import com.alta189.simple.gallery.objects.User;
import com.alta189.simple.gallery.utils.UserUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;

@Controller
public class EmailHandler {
	@ResourceMapping("/verify/:user/:key")
	public void verify(Request request, Response response) {
		int userId = NumberUtils.toInt(request.params("user"), -1);
		String key = request.params("key");

		if (userId <= 0 || StringUtils.isEmpty(key)) {
			UserUtils.message(response, "Invalid Request!", "danger");
			response.redirect("/");
			return;
		}

		EmailConfirm emailConfirm = SimpleGalleryServer.getDatabase().select(EmailConfirm.class).where().equal("key", key).and().equal("user", userId).execute().findOne();
		if (emailConfirm == null) {
			UserUtils.message(response, "Invalid Request!", "danger");
			response.redirect("/");
			return;
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", userId).execute().findOne();
		if (user == null) {
			UserUtils.message(response, "Invalid Request!", "danger");
			response.redirect("/");
			return;
		}

		user.setVerifiedEmail(true);
		SimpleGalleryServer.getDatabase().save(user);
		SimpleGalleryServer.getDatabase().remove(emailConfirm);

		UserUtils.message(response, "Email Verified Successfully", "success");
		response.redirect("/");
	}
}
