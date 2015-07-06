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

		user.setEmail(user.getEmail().toLowerCase());

		if (StringUtils.isEmpty(user.getName()) || StringUtils.isBlank(user.getName())) {
			return Result.error("invalid name");
		}

		String pass = request.queryParams("password");
		if (!PasswordUtils.validPassword(pass)) {
			return Result.error("invalid password");
		}

		if (SimpleGalleryServer.getDatabase().select(User.class).where().equal("email", user.getEmail()).execute().find().size() > 0) {
			return Result.error("email already exists");
		}

		SimpleGalleryServer.getDatabase().save(user);
		EmailConfirm emailConfirm = EmailConfirm.generateNew(user.getId());
		SimpleGalleryServer.getDatabase().save(emailConfirm);

		// TODO send email conformation

		return SimpleGalleryConstants.Results.SUCCESS;
	}
}
