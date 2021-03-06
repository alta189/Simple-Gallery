package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.AutoSparkUtils;
import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.RequestMethod;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.Transformer;
import com.alta189.simple.gallery.MailManager;
import com.alta189.simple.gallery.SimpleGalleryConstants;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.auth.AccessRule;
import com.alta189.simple.gallery.objects.EmailConfirm;
import com.alta189.simple.gallery.objects.PasswordReset;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.objects.ResultTransformer;
import com.alta189.simple.gallery.objects.User;
import com.alta189.simple.gallery.objects.UserRole;
import com.alta189.simple.gallery.utils.PasswordUtils;
import com.alta189.simple.gallery.utils.UserUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.http.entity.ContentType;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;

@Controller
@ResourceMapping("/api/users")
@Transformer(ResultTransformer.class)
public class Users {
	@ResourceMapping(value = "/signup", method = RequestMethod.POST)
	public Result signup(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		User user = new User();
		user.setEmail(request.queryParams("email"));
		user.setName(request.queryParams("name"));
		user.setRole(UserRole.UNVERIFIED);
		String emailConfirm = request.queryParams("confirm_email");
		String passConfirm = request.queryParams("confirm_password");

		if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isBlank(user.getEmail()) || !EmailValidator.getInstance().isValid(user.getEmail())) {
			return Result.error("Invalid Email");
		}

		if (StringUtils.isEmpty(emailConfirm) || StringUtils.isBlank(emailConfirm) || !EmailValidator.getInstance().isValid(emailConfirm)) {
			return Result.error("Invalid Confirm Email");
		}

		if (!emailConfirm.equals(user.getEmail())) {
			return Result.error("Emails do not match!");
		}

		user.setEmail(user.getEmail().trim().toLowerCase());

		if (StringUtils.isEmpty(user.getName()) || StringUtils.isBlank(user.getName())) {
			return Result.error("Invalid Name");
		}

		user.setName(WordUtils.capitalizeFully(user.getName()));

		String pass = request.queryParams("password");
		if (!PasswordUtils.validPassword(pass)) {
			return Result.error("Invalid Password");
		}

		if (!PasswordUtils.validPassword(passConfirm)) {
			return Result.error("Invalid Password");
		}

		if (!passConfirm.equals(pass)) {
			return Result.error("Passwords do not match!");
		}

		user.setPassword(DigestUtils.sha1Hex(pass));

		if (SimpleGalleryServer.getDatabase().select(User.class).where().equal("email", user.getEmail()).execute().find().size() > 0) {
			return Result.error("User with email already exists!");
		}

		SimpleGalleryServer.getDatabase().save(user);
		EmailConfirm em = EmailConfirm.generateNew(user.getId());
		SimpleGalleryServer.getDatabase().save(em);

		new Thread(() -> {
			MailManager.getInstance().sendEmailVerification(user, em);
		}).start();

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping("/remove/:id")
	@AccessRule(UserRole.ADMINISTRATOR)
	public Result remove(Request request, Response response) {
		int userId = NumberUtils.toInt(request.params("id"), -1);

		if (userId <= 0) {
			return Result.error("Invalid Request!");
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", userId).execute().findOne();
		if (user == null) {
			return Result.error("Invalid Request!");
		}

		SimpleGalleryServer.getDatabase().remove(user);

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping(value = "/update", accepts = "application/json", method = RequestMethod.PUT)
	@AccessRule(UserRole.ADMINISTRATOR)
	public Result update(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		if (StringUtils.isEmpty(request.body()) || StringUtils.isBlank(request.body())) {
			return Result.error("Invalid Request");
		}

		Map<String, Object> map = SimpleGalleryServer.GSON.fromJson(request.body(), SimpleGalleryConstants.GsonTypes.TYPE_MAP_STRING_OBJECT);
		if (map == null || map.size() < 1) {
			return Result.error("Invalid Request");
		}

		if (!map.containsKey("id")) {
			return Result.error("Invalid Request");
		}

		int id = safeCastDouble(map.get("id"), -1.0).intValue();
		if (id <= 0) {
			return Result.error("Invalid Request");
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("id", id).execute().findOne();
		if (user == null) {
			return Result.error("User Does Not Exist");
		}

		if (map.containsKey("name")) {
			String name = AutoSparkUtils.safeCast(map.get("name"));
			if (StringUtils.isNotEmpty(name) && StringUtils.isNotBlank(name)) {
				user.setName(name);
			}
		}

		if (map.containsKey("password") && map.containsKey("confirm")) {
			String password = AutoSparkUtils.safeCast(map.get("name"));
			String confirm = AutoSparkUtils.safeCast(map.get("name"));
			if (StringUtils.isNotEmpty(password) && StringUtils.isNotBlank(password) && StringUtils.isNotEmpty(confirm) && StringUtils.isNotBlank(confirm)) {
				if (!password.equals(confirm)) {
					return Result.error("Passwords Do Not Match");
				} else if (!PasswordUtils.validPassword(password)) {
					return Result.error("Invalid Password");
				}
				user.setPassword(DigestUtils.sha1Hex(password));
			}
		}

		if (map.containsKey("email")) {
			String email = AutoSparkUtils.safeCast(map.get("email"));
			if (StringUtils.isNotEmpty(email) && StringUtils.isNotBlank(email)) {
				if (!EmailValidator.getInstance().isValid(email)) {
					return Result.error("Invalid Email");
				}
				user.setEmail(email);
			}
		}

		if (map.containsKey("role")) {
			String roleRaw = AutoSparkUtils.safeCast(map.get("role"));
			if (StringUtils.isNotEmpty(roleRaw) && StringUtils.isNotBlank(roleRaw)) {
				UserRole role = UserRole.fromName(roleRaw, null);
				if (role != null) {
					user.setRole(role);
				}
			}
		}

		SimpleGalleryServer.getDatabase().save(user);

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping(value = {"/login", "/signin"}, method = RequestMethod.POST)
	public Result login(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		String email = request.queryParams("email");
		String pass = request.queryParams("password");

		if (StringUtils.isEmpty(email) || StringUtils.isBlank(email) || !EmailValidator.getInstance().isValid(email)) {
			return Result.error("Invalid Email");
		}

		if (!PasswordUtils.validPassword(pass)) {
			return Result.error("Invalid Password");
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("email", email.trim().toLowerCase()).and().equal("password", DigestUtils.sha1Hex(pass)).execute().findOne();

		if (user == null) {
			return Result.error("Invalid Email/Password Combination");
		}

		if (!user.isVerifiedEmail()) {
			return Result.error("Your email not confirmed yet");
		}

		request.session(true);
		request.session().attribute("user-id", user.getId());

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping(value = "/resend", method = RequestMethod.POST)
	public Result resend(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		String email = request.queryParams("email");

		if (StringUtils.isEmpty(email) || StringUtils.isBlank(email) || !EmailValidator.getInstance().isValid(email)) {
			return Result.error("invalid email");
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("email", email.trim().toLowerCase()).execute().findOne();
		if (user == null) {
			return Result.error("user not found");
		}

		if (user.isVerifiedEmail()) {
			return Result.error("email is already confirmed");
		}

		EmailConfirm emailConfirm = SimpleGalleryServer.getDatabase().select(EmailConfirm.class).where().equal("user", user.getId()).execute().findOne();
		if (emailConfirm == null) {
			emailConfirm = EmailConfirm.generateNew(user.getId());
			SimpleGalleryServer.getDatabase().save(emailConfirm);
		}

		final EmailConfirm confirm = emailConfirm;

		new Thread(() -> {
			MailManager.getInstance().sendEmailVerification(user, confirm);
		}).start();

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping(value = "/forgot", method = RequestMethod.POST)
	public Result forgot(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		String email = request.queryParams("email");

		if (StringUtils.isEmpty(email) || StringUtils.isBlank(email) || !EmailValidator.getInstance().isValid(email)) {
			return Result.error("invalid email");
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("email", email.trim().toLowerCase()).execute().findOne();
		if (user == null) {
			return Result.error("user not found");
		}

		if (!user.isVerifiedEmail()) {
			return Result.error("email is not confirmed");
		}

		PasswordReset pr = SimpleGalleryServer.getDatabase().select(PasswordReset.class).where().equal("user", user.getId()).execute().findOne();
		if (pr == null) {
			pr = PasswordReset.generateNew(user.getId());
			SimpleGalleryServer.getDatabase().save(pr);
		}

		final PasswordReset passwordReset = pr;

		new Thread(() -> {
			MailManager.getInstance().sendPasswordReset(user, passwordReset);
		}).start();

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping(value = "/reset", method = RequestMethod.POST)
	public Result reset(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		String email = request.queryParams("email");
		String key = request.queryParams("key");
		String password = request.queryParams("password");
		String confirm = request.queryParams("confirm");

		if (email == null || StringUtils.isEmpty(email) || !EmailValidator.getInstance().isValid(email)) {
			return Result.error("Invalid Email");
		}

		email = email.toLowerCase();

		if (StringUtils.isEmpty(password) || !PasswordUtils.validPassword(password)) {
			return Result.error("Invalid Password!");
		}

		if (StringUtils.isEmpty(confirm) || !PasswordUtils.validPassword(confirm)) {
			return Result.error("Invalid Confirmation Password!");
		}

		if (!password.equals(confirm)) {
			return Result.error("Passwords do not match");
		}

		User user = SimpleGalleryServer.getDatabase().select(User.class).where().equal("email", email).execute().findOne();
		if (user == null) {
			return Result.error("Invalid Request");
		}

		PasswordReset passwordReset = SimpleGalleryServer.getDatabase().select(PasswordReset.class).where().equal("key", key).and().equal("user", user.getId()).execute().findOne();
		if (passwordReset == null) {
			return Result.error("Invalid Request");
		}

		user.setPassword(DigestUtils.sha1Hex(password));

		SimpleGalleryServer.getDatabase().save(user);
		SimpleGalleryServer.getDatabase().remove(passwordReset);

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping("/info")
	public Result result(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		User user = UserUtils.getUser(request);
		if (user == null) {
			return Result.error("not logged in");
		}

		return Result.wrap(user);
	}

	@ResourceMapping("/list")
	public Result info(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		List<User> users = SimpleGalleryServer.getDatabase().select(User.class).execute().find();

		return Result.wrap(users);
	}

	@ResourceMapping("/roles")
	public Result roles(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		return Result.wrap(UserRole.SELECT_OPTIONS);
	}

	private Double safeCastDouble(Object o, double defaultValue) {
		try {
			return (Double) o;
		} catch (Exception ignored) {
		}
		return defaultValue;
	}
}
