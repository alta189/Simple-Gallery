package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.TemplateEngine;
import com.alta189.simple.gallery.PageServ;
import com.alta189.simple.gallery.SimpleGalleryConstants;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.EmailConfirm;
import com.alta189.simple.gallery.objects.MessagePosition;
import com.alta189.simple.gallery.objects.MessageStyle;
import com.alta189.simple.gallery.objects.PasswordReset;
import com.alta189.simple.gallery.objects.User;
import com.alta189.simple.gallery.objects.UserRole;
import com.alta189.simple.gallery.objects.ValidationRule;
import com.alta189.simple.gallery.utils.MessageBuilder;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.List;
import java.util.Map;

@Controller
public class EmailHandler {
	@ResourceMapping("/verify/:user/:key")
	public void verify(Request request, Response response) {
		int userId = NumberUtils.toInt(request.params("user"), -1);
		String key = request.params("key");

		if (userId <= 0 || StringUtils.isEmpty(key)) {
			MessageBuilder.get(request).setMessage("Invalid Request!").setStyle(MessageStyle.DANGER).setPosition(MessagePosition.TOP_RIGHT).save();
			response.redirect("/");
			return;
		}

		EmailConfirm emailConfirm = SimpleGalleryServer.getDatabase().select(EmailConfirm.class).where().equal("key", key).and().equal("user", userId).execute().findOne();
		if (emailConfirm == null) {
			MessageBuilder.get(request).setMessage("Invalid Request!").setStyle(MessageStyle.DANGER).setPosition(MessagePosition.TOP_RIGHT).save();
			response.redirect("/");
			return;
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", userId).execute().findOne();
		if (user == null) {
			MessageBuilder.get(request).setMessage("Invalid Request!").setStyle(MessageStyle.DANGER).setPosition(MessagePosition.TOP_RIGHT).save();
			response.redirect("/");
			return;
		}

		user.setVerifiedEmail(true);

		final String defaultAdmin = SimpleGalleryServer.SETTINGS.getString(SimpleGalleryConstants.Settings.Keys.USER_DEFAULT_ADMIN);
		if (StringUtils.isNotEmpty(defaultAdmin) && StringUtils.isNotBlank(defaultAdmin) && EmailValidator.getInstance().isValid(defaultAdmin)) {
			if (user.getEmail().equalsIgnoreCase(defaultAdmin)) {
				user.setRole(UserRole.ADMINISTRATOR);
			}
		}

		if (user.getRole().getValue() <= UserRole.UNVERIFIED.getValue()) {
			List<ValidationRule> validationRules = SimpleGalleryServer.getDatabase().select(ValidationRule.class).execute().find();
			if (validationRules != null && validationRules.size() > 0) {
				validationRules.stream().filter(rule -> rule.check(user.getEmail())).forEach(rule -> user.setRole(UserRole.VERIFIED));
			}
		}

		SimpleGalleryServer.getDatabase().save(user);
		SimpleGalleryServer.getDatabase().remove(emailConfirm);

		MessageBuilder.get(request).setMessage("Email Verified Successfully!").setStyle(MessageStyle.SUCCESS).setPosition(MessagePosition.TOP_RIGHT).save();
		response.redirect("/");
	}

	@ResourceMapping("/reset/:user/:key")
	@TemplateEngine(FreeMarkerEngine.class)
	public ModelAndView reset(Request request, Response response) {
		int userId = NumberUtils.toInt(request.params("user"), -1);
		String key = request.params("key");

		if (userId <= 0 || org.apache.commons.lang.StringUtils.isEmpty(key)) {
			MessageBuilder.get(request).setMessage("Invalid Request!").setStyle(MessageStyle.DANGER).setPosition(MessagePosition.TOP_RIGHT).save();
			response.redirect("/");
			return null;
		}

		PasswordReset passwordReset = SimpleGalleryServer.getDatabase().select(PasswordReset.class).where().equal("key", key).and().equal("user", userId).execute().findOne();
		if (passwordReset == null) {
			MessageBuilder.get(request).setMessage("Invalid Request!").setStyle(MessageStyle.DANGER).setPosition(MessagePosition.TOP_RIGHT).save();
			response.redirect("/");
			return null;
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", userId).execute().findOne();
		if (user == null) {
			MessageBuilder.get(request).setMessage("Invalid Request!").setStyle(MessageStyle.DANGER).setPosition(MessagePosition.TOP_RIGHT).save();
			response.redirect("/");
			return null;
		}

		Map<String, String> model = PageServ.getBaseModel();
		model.put("reset_email", user.getEmail());
		model.put("reset_key", key);

		return new ModelAndView(model, "reset-password.ftl");
	}
}
