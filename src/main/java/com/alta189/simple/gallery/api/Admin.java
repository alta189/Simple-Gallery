package com.alta189.simple.gallery.api;

import com.alta189.auto.spark.Controller;
import com.alta189.auto.spark.RequestMethod;
import com.alta189.auto.spark.ResourceMapping;
import com.alta189.auto.spark.Transformer;
import com.alta189.simple.gallery.SimpleGalleryConstants;
import com.alta189.simple.gallery.SimpleGalleryServer;
import com.alta189.simple.gallery.auth.AccessRule;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.objects.ResultTransformer;
import com.alta189.simple.gallery.objects.UserRole;
import com.alta189.simple.gallery.objects.ValidationRule;
import com.alta189.simple.gallery.objects.ValidationType;
import com.alta189.simple.gallery.utils.RegexUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;

import static com.alta189.simple.gallery.SimpleGalleryConstants.Settings.Defaults;
import static com.alta189.simple.gallery.SimpleGalleryConstants.Settings.Keys;
import static com.alta189.simple.gallery.SimpleGalleryConstants.Templates;
import static com.alta189.simple.gallery.SimpleGalleryServer.GSON;
import static com.alta189.simple.gallery.SimpleGalleryServer.SETTINGS;
import static com.alta189.simple.gallery.SimpleGalleryServer.getDatabase;


@Controller
@ResourceMapping("/api/admin")
@Transformer(ResultTransformer.class)
@AccessRule(value = UserRole.ADMINISTRATOR, json = true)
public class Admin {
	@ResourceMapping("/settings")
	public Result settings(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		Gallery gallery = new Gallery();
		gallery.setName(SETTINGS.getString(Keys.GALLERY_NAME, Defaults.GALLERY_NAME));
		gallery.setDescription(SETTINGS.getString(Keys.GALLERY_DESCRIPTION, Defaults.GALLERY_DESCRIPTION));
		gallery.setHost(SETTINGS.getString(Keys.GALLERY_HOST, Defaults.GALLERY_HOST));
		gallery.setWebAdmin(SETTINGS.getString(Keys.GALLERY_NAME, Defaults.GALLERY_WEBADMIN));

		SendGridSettings sendgrid = new SendGridSettings();
		sendgrid.setUsername(SETTINGS.getString(Keys.EMAIL_SENDGRID_USERNAME));
		sendgrid.setPassword(SETTINGS.getString(Keys.EMAIL_SENDGRID_PASSWORD));
		sendgrid.setApikey(SETTINGS.getString(Keys.EMAIL_SENDGRID_APIKEY));

		Address address = new Address();
		address.setValidation(SETTINGS.getString(Keys.EMAIL_ADDRESS_EMAIL_VALIDATION, Defaults.EMAIL_ADDRESS_EMAIL_VALIDATION));
		address.setReset(SETTINGS.getString(Keys.EMAIL_ADDRESS_FORGOT_PASSWORD, Defaults.EMAIL_ADDRESS_FORGOT_PASSWORD));
		address.setContactUs(SETTINGS.getString(Keys.EMAIL_ADDRESS_EMAIL_VALIDATION, Defaults.EMAIL_ADDRESS_CONTACT_US));

		Subject subject = new Subject();
		subject.setValidation(SETTINGS.getString(Keys.EMAIL_SUBJECT_EMAIL_VALIDATION, Defaults.EMAIL_SUBJECT_EMAIL_VALIDATION));
		subject.setReset(SETTINGS.getString(Keys.EMAIL_SUBJECT_FORGOT_PASSWORD, Defaults.EMAIL_SUBJECT_FORGOT_PASSWORD));

		Settings settings = new Settings();
		settings.setGallery(gallery);
		settings.setSendgrid(sendgrid);
		settings.setAddress(address);
		settings.setSubject(subject);

		return Result.wrap(settings);
	}

	@ResourceMapping(value = "/settings", accepts = "application/json", method = RequestMethod.PUT)
	public Result getSettings(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		if (StringUtils.isEmpty(request.body()) || StringUtils.isBlank(request.body())) {
			return Result.error("Invalid Request");
		}

		Settings settings = GSON.fromJson(request.body(), Settings.class);
		if (settings == null) {
			return Result.error("Invalid Request");
		}

		if (settings.getGallery() != null) {
			saveSetting(Keys.GALLERY_NAME, settings.getGallery().getName());
			saveSetting(Keys.GALLERY_DESCRIPTION, settings.getGallery().getDescription());
			saveSetting(Keys.GALLERY_HOST, settings.getGallery().getHost());
			saveSetting(Keys.GALLERY_WEBADMIN, settings.getGallery().getWebAdmin());
		}

		if (settings.getSendgrid() != null) {
			saveSetting(Keys.EMAIL_SENDGRID_USERNAME, settings.getSendgrid().getUsername());
			saveSetting(Keys.EMAIL_SENDGRID_PASSWORD, settings.getSendgrid().getPassword());
			saveSetting(Keys.EMAIL_SENDGRID_APIKEY, settings.getSendgrid().getApikey());
		}

		if (settings.getAddress() != null) {
			saveSetting(Keys.EMAIL_ADDRESS_EMAIL_VALIDATION, settings.getAddress().getValidation());
			saveSetting(Keys.EMAIL_ADDRESS_FORGOT_PASSWORD, settings.getAddress().getReset());
			saveSetting(Keys.EMAIL_ADDRESS_CONTACT_US, settings.getAddress().getContactUs());
		}

		if (settings.getSubject() != null) {
			saveSetting(Keys.EMAIL_SUBJECT_EMAIL_VALIDATION, settings.getSubject().getValidation());
			saveSetting(Keys.EMAIL_SUBJECT_FORGOT_PASSWORD, settings.getSubject().getReset());
		}

		if (settings.getTemplate() != null) {
			if (StringUtils.isNotEmpty(settings.getTemplate().getValidation()) || StringUtils.isNotBlank(settings.getTemplate().getValidation())) {
				try {
					FileUtils.writeStringToFile(Templates.EMAIL_VERIFICATION, settings.getTemplate().getValidation());
				} catch (IOException e) {
					e.printStackTrace();
					Result.error("Error Saving Template");
				}
			}
			if (StringUtils.isNotEmpty(settings.getTemplate().getReset()) || StringUtils.isNotBlank(settings.getTemplate().getReset())) {
				try {
					FileUtils.writeStringToFile(Templates.RESET_PASSWORD, settings.getTemplate().getReset());
				} catch (IOException e) {
					e.printStackTrace();
					Result.error("Error Saving Template");
				}
			}
		}

		try {
			SETTINGS.save();
		} catch (ConfigurationException e) {
			Result.error("Error Saving Settings");
			e.printStackTrace();
		}

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping(value = "/validation/rules")
	public Result getRules(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		List<ValidationRule> rules = SimpleGalleryServer.getDatabase().select(ValidationRule.class).execute().find();

		return Result.wrap(rules);
	}

	@ResourceMapping(value = "/validation/rules/types")
	public Result getValidationTypes(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		return Result.wrap(ValidationType.values());
	}

	@ResourceMapping(value = "/validation/rule/remove/:id")
	public Result deleteRule(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		int id = NumberUtils.toInt(request.params(":id"), -1);
		if (id < 1) {
			return Result.error("Invalid Request");
		}

		ValidationRule rule = SimpleGalleryServer.getDatabase().select(ValidationRule.class).where().equal("id", id).execute().findOne();
		if (rule == null) {
			return Result.error("Invalid Request");
		}

		getDatabase().remove(rule);

		return SimpleGalleryConstants.Results.SUCCESS;
	}

	@ResourceMapping(value = "/validation/rule", accepts = "application/json", method = RequestMethod.PUT)
	public Result createRule(Request request, Response response) {
		response.type(ContentType.APPLICATION_JSON.getMimeType());

		if (StringUtils.isEmpty(request.body()) || StringUtils.isBlank(request.body())) {
			return Result.error("Invalid Request");
		}

		ValidationRule rule = GSON.fromJson(request.body(), ValidationRule.class);
		if (rule == null) {
			return Result.error("Invalid Request");
		}

		if (StringUtils.isEmpty(rule.getRule()) || StringUtils.isBlank(rule.getRule())) {
			return Result.error("Empty Rule");
		}

		if (rule.getType() == ValidationType.REGEX && !RegexUtils.isValidRegex(rule.getRule())) {
			return Result.error("Invalid Regex");
		}

		getDatabase().save(rule);

		return Result.wrap(rule);
	}

	private void saveSetting(String key, String value) {
		if (StringUtils.isNotEmpty(value) || StringUtils.isNotBlank(value)) {
			SETTINGS.setProperty(key, value);
		}
	}

	private class Address {
		@Expose
		private String reset;
		@SerializedName("contact_us")
		@Expose
		private String contactUs;
		@Expose
		private String validation;

		public String getReset() {
			return reset;
		}

		public void setReset(String reset) {
			this.reset = reset;
		}

		public String getContactUs() {
			return contactUs;
		}

		public void setContactUs(String contactUs) {
			this.contactUs = contactUs;
		}

		public String getValidation() {
			return validation;
		}

		public void setValidation(String validation) {
			this.validation = validation;
		}
	}

	private class Settings {
		@Expose
		private Address address;
		@Expose
		private Subject subject;
		@Expose
		private SendGridSettings sendgrid;
		@Expose
		private Gallery gallery;
		@Expose
		private TemplateSettings template;

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public Subject getSubject() {
			return subject;
		}

		public void setSubject(Subject subject) {
			this.subject = subject;
		}

		public SendGridSettings getSendgrid() {
			return sendgrid;
		}

		public void setSendgrid(SendGridSettings sendgrid) {
			this.sendgrid = sendgrid;
		}

		public Gallery getGallery() {
			return gallery;
		}

		public void setGallery(Gallery gallery) {
			this.gallery = gallery;
		}

		public TemplateSettings getTemplate() {
			return template;
		}

		public void setTemplate(TemplateSettings template) {
			this.template = template;
		}
	}

	private class Gallery {
		@Expose
		private String name;
		@Expose
		private String host;
		@Expose
		private String description;
		@Expose
		private String webadmin;


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getWebAdmin() {
			return webadmin;
		}

		public void setWebAdmin(String webadmin) {
			this.webadmin = webadmin;
		}
	}

	private class SendGridSettings {
		@Expose
		private String password;
		@Expose
		private String apikey;
		@Expose
		private String username;

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getApikey() {
			return apikey;
		}

		public void setApikey(String apikey) {
			this.apikey = apikey;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}

	private class Subject {
		@Expose
		private String reset;
		@Expose
		private String validation;

		public String getReset() {
			return reset;
		}

		public void setReset(String reset) {
			this.reset = reset;
		}

		public String getValidation() {
			return validation;
		}

		public void setValidation(String validation) {
			this.validation = validation;
		}
	}

	private class TemplateSettings {
		@Expose
		private String validation;
		@Expose
		private String reset;

		public String getValidation() {
			return validation;
		}

		public void setValidation(String validation) {
			this.validation = validation;
		}

		public String getReset() {
			return reset;
		}

		public void setReset(String reset) {
			this.reset = reset;
		}
	}
}