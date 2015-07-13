package com.alta189.simple.gallery;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.TemplateEngine;
import com.alta189.simple.gallery.auth.AccessRule;
import com.alta189.simple.gallery.objects.MessagePosition;
import com.alta189.simple.gallery.objects.MessageStyle;
import com.alta189.simple.gallery.objects.PasswordReset;
import com.alta189.simple.gallery.objects.User;
import com.alta189.simple.gallery.objects.UserRole;
import com.alta189.simple.gallery.utils.MessageBuilder;
import com.alta189.simple.gallery.utils.UserUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.alta189.simple.gallery.SimpleGalleryConstants.Templates.Variables;

@Controller
@TemplateEngine(FreeMarkerEngine.class)
public class PageServ {
	@ResourceMapping({"/", "/index", "/index.*", "/home", "/home.*"})
	public ModelAndView index(Request request, Response response) {
		Map<String, String> model = getBaseModel();

		return new ModelAndView(model, "index.ftl");
	}

	@ResourceMapping({"/login", "/signin", "/login.*", "/signin.*"})
	public ModelAndView signin(Request request, Response response) {
		Map<String, String> model = getBaseModel();

		return new ModelAndView(model, "signin.ftl");
	}

	@ResourceMapping({"/signup", "/signup.*"})
	public ModelAndView signup(Request request, Response response) {
		Map<String, String> model = getBaseModel();

		return new ModelAndView(model, "signup.ftl");
	}

	@ResourceMapping({"/logout", "/signout", "/logout.*", "/signout.*"})
	@TemplateEngine(ignoreParent = true)
	public void signout(Request request, Response response) {
		UserUtils.signOut(request);
		MessageBuilder.get(request).setMessage("Signed Out Successfully!").setStyle(MessageStyle.SUCCESS).setPosition(MessagePosition.TOP_RIGHT).save();
		response.redirect("/");
	}

	@ResourceMapping({"/forgot", "/forgotpassword", "/reset"})
	public ModelAndView forgotPass(Request request, Response response) {
		Map<String, String> model = getBaseModel();

		return new ModelAndView(model, "forgot-password.ftl");
	}

	@ResourceMapping({"/admin"})
	@AccessRule(UserRole.ADMINISTRATOR)
	public ModelAndView admin(Request request, Response response) {
		Map<String, String> model = getBaseModel();

		try {
			model.put("editor_verify", FileUtils.readFileToString(SimpleGalleryConstants.Templates.EMAIL_VERIFICATION));
			model.put("editor_reset", FileUtils.readFileToString(SimpleGalleryConstants.Templates.RESET_PASSWORD));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ModelAndView(model, "admin.ftl");
	}

	@ResourceMapping({"/404", "/errors/404","/errors/404.html"})
	public ModelAndView error404(Request request, Response response) {
		Map<String, String> model = getBaseModel();

		return new ModelAndView(model, "404.ftl");
	}

	@ResourceMapping("/403")
	public ModelAndView error403(Request request, Response response) {
		Map<String, String> model = getBaseModel();

		return new ModelAndView(model, "403.ftl");
	}

	public static Map<String, String> getBaseModel() {
		Map<String, String> result = new HashMap<>();
		result.put(Variables.GALLERY_NAME, SimpleGalleryServer.SETTINGS.getString(SimpleGalleryConstants.Settings.Keys.GALLERY_NAME, SimpleGalleryConstants.Settings.Defaults.GALLERY_NAME));
		result.put(Variables.GALLERY_DESCRIPTION, SimpleGalleryServer.SETTINGS.getString(SimpleGalleryConstants.Settings.Keys.GALLERY_DESCRIPTION, SimpleGalleryConstants.Settings.Defaults.GALLERY_DESCRIPTION));
		result.put(Variables.GALLERY_HOST, SimpleGalleryServer.SETTINGS.getString(SimpleGalleryConstants.Settings.Keys.GALLERY_HOST, SimpleGalleryConstants.Settings.Defaults.GALLERY_HOST));
		result.put(Variables.GALLERY_WEBADMIN, SimpleGalleryServer.SETTINGS.getString(SimpleGalleryConstants.Settings.Keys.GALLERY_WEBADMIN, SimpleGalleryConstants.Settings.Defaults.GALLERY_WEBADMIN));
		result.put(Variables.EMAIL_CONTACT_US, SimpleGalleryServer.SETTINGS.getString(SimpleGalleryConstants.Settings.Keys.EMAIL_ADDRESS_CONTACT_US));
		return result;
	}
}
