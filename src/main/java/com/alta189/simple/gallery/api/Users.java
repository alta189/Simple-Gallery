package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.Transformer;
import com.alta189.simple.gallery.SimpleGalleryConstants;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.objects.EmailConfirm;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.objects.ResultTransformer;
import com.alta189.simple.gallery.objects.User;
import com.alta189.simple.gallery.utils.PasswordUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import spark.Request;
import spark.Response;

@Controller
@ResourceMapping("/api/users")
@Transformer(ResultTransformer.class)
public class Users {
	@ResourceMapping("/signup")
	public Result signup(Request request, Response response) {
		User user = new User();
		user.setEmail(request.queryParams("email"));
		user.setName(request.queryParams("name"));

		if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isBlank(user.getEmail()) || !EmailValidator.getInstance().isValid(user.getEmail())) {
			return Result.error("invalid email");
		}

		user.setEmail(user.getEmail().trim().toLowerCase());

		if (StringUtils.isEmpty(user.getName()) || StringUtils.isBlank(user.getName())) {
			return Result.error("invalid name");
		}

		String pass = request.queryParams("password");
		if (!PasswordUtils.validPassword(pass)) {
			return Result.error("invalid password");
		}

		user.setPassword(DigestUtils.sha1Hex(pass));

		if (SimpleGalleryServer.getDatabase().select(User.class).where().equal("email", user.getEmail()).execute().find().size() > 0) {
			return Result.error("email already exists");
		}

		SimpleGalleryServer.getDatabase().save(user);
		EmailConfirm emailConfirm = EmailConfirm.generateNew(user.getId());
		SimpleGalleryServer.getDatabase().save(emailConfirm);

		// TODO send email confirmation

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping("/login")
	public Result login(Request request, Response response) {
		String email = request.queryParams("email");
		String pass = request.queryParams("password");

		if (StringUtils.isEmpty(email) || StringUtils.isBlank(email) || !EmailValidator.getInstance().isValid(email)) {
			return Result.error("invalid email");
		}

		if (!PasswordUtils.validPassword(pass)) {
			return Result.error("invalid password");
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("email", email.trim().toLowerCase()).and().equal("password", DigestUtils.sha1Hex(pass)).execute().findOne();

		if (user == null) {
			return Result.error("invalid email/password combination");
		}

		if (!user.isVerifiedEmail()) {
			return Result.error("email not confirmed");
		}

		request.session(true);
		request.session().attribute("user-id", user.getId());

		return SimpleGalleryConstants.Results.SUCCESS;
	}
}
