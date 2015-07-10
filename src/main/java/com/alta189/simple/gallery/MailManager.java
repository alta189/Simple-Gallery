package com.alta189.simple.gallery;

import com.alta189.simple.gallery.objects.EmailConfirm;
import com.alta189.simple.gallery.objects.PasswordReset;
import com.alta189.simple.gallery.objects.User;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.resource.ClassPathResource;
import spark.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.alta189.simple.gallery.SimpleGalleryConstants.FileLocations.TEMPLATES_DIRECTORY;
import static com.alta189.simple.gallery.SimpleGalleryConstants.Settings.Defaults;
import static com.alta189.simple.gallery.SimpleGalleryConstants.Settings.Keys;
import static com.alta189.simple.gallery.SimpleGalleryConstants.Templates.EMAIL_VERIFICATION;
import static com.alta189.simple.gallery.SimpleGalleryConstants.Templates.RESET_PASSWORD;
import static com.alta189.simple.gallery.SimpleGalleryConstants.Templates.Variables;

public class MailManager {
	private final static MailManager instance = new MailManager();
	private static final Logger logger = LoggerFactory.getLogger(MailManager.class);
	private final PegDownProcessor pegDownProcessor = new PegDownProcessor(Extensions.ALL);

	private MailManager() {
	}

	public static MailManager getInstance() {
		return instance;
	}

	protected void checkTemplates() {
		TEMPLATES_DIRECTORY.mkdirs();
		if (!EMAIL_VERIFICATION.exists()) {
			try {
				String contents = IOUtils.toString(new ClassPathResource(EMAIL_VERIFICATION.getPath()).getInputStream());
				FileUtils.writeStringToFile(EMAIL_VERIFICATION, contents);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!RESET_PASSWORD.exists()) {
			try {
				String contents = IOUtils.toString(new ClassPathResource(RESET_PASSWORD.getPath()).getInputStream());
				FileUtils.writeStringToFile(RESET_PASSWORD, contents);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendPasswordReset(User user, PasswordReset passwordReset) {
		SendGrid sendgrid = getSendGrid();
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(user.getEmail());
		email.setFrom(SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_ADDRESS_EMAIL_VALIDATION));
		email.setSubject(SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_SUBJECT_EMAIL_VALIDATION));

		Map<String, String> variables = generateStandardVariables();
		variables.put(Variables.RESET_PASSWORD_LINK, getPasswordResetLink(passwordReset));
		variables.put(Variables.USER_NAME, user.getName());
		variables.put(Variables.USER_EMAIL, user.getEmail());

		String renderedHtml = renderTemplate(RESET_PASSWORD, variables);

		email.setHtml(renderedHtml);
		try {
			sendgrid.send(email);
		} catch (SendGridException e) {
			logger.error("Exception sending email", e);
		}
	}

	public void sendEmailVerification(User user, EmailConfirm confirm) {
		SendGrid sendgrid = getSendGrid();
		SendGrid.Email email = new SendGrid.Email();
		email.addTo(user.getEmail());
		email.setFrom(SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_ADDRESS_EMAIL_VALIDATION));
		email.setSubject(SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_SUBJECT_EMAIL_VALIDATION));

		Map<String, String> variables = generateStandardVariables();
		variables.put(Variables.EMAIL_VERIFICATION_LINK, getEmailVerificationLink(confirm));
		variables.put(Variables.USER_NAME, user.getName());
		variables.put(Variables.USER_EMAIL, user.getEmail());

		String renderedHtml = renderTemplate(EMAIL_VERIFICATION, variables);

		email.setHtml(renderedHtml);
		try {
			sendgrid.send(email);
		} catch (SendGridException e) {
			logger.error("Exception sending email", e);
		}
	}

	public Map<String, String> generateStandardVariables() {
		Map<String, String> result = new HashMap<>();
		put(result, Variables.GALLERY_NAME, SimpleGalleryServer.SETTINGS.getString(Keys.GALLERY_NAME, Defaults.GALLERY_NAME));
		put(result, Variables.GALLERY_DESCRIPTION, SimpleGalleryServer.SETTINGS.getString(Keys.GALLERY_DESCRIPTION, Defaults.GALLERY_DESCRIPTION));
		put(result, Variables.GALLERY_HOST, SimpleGalleryServer.SETTINGS.getString(Keys.GALLERY_HOST, Defaults.GALLERY_HOST));
		put(result, Variables.GALLERY_WEBADMIN, SimpleGalleryServer.SETTINGS.getString(Keys.GALLERY_WEBADMIN, Defaults.GALLERY_WEBADMIN));
		put(result, Variables.EMAIL_CONTACT_US, SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_ADDRESS_CONTACT_US));
		put(result, Variables.NOW, DateTime.now().toString(SimpleGalleryServer.DATE_FORMAT));
		return result;
	}

	private void put(Map<String, String> map, String key, String value) {
		map.put("{{" + key + "}}", value);
	}

	public String renderTemplate(File template, Map<String, String> variables) {
		if (!template.exists()) {
			logger.error("Template does not exist");
			return null;
		}

		String contents;
		try {
			contents = FileUtils.readFileToString(template);
		} catch (IOException e) {
			logger.error("error reading template", e);
			return null;
		}

		if (variables != null && variables.size() >= 1) {
			for (Map.Entry<String, String> entry : variables.entrySet()) {
				contents = contents.replaceAll(Pattern.quote(entry.getKey()), entry.getValue());
			}
		}

		return pegDownProcessor.markdownToHtml(contents);
	}

	private SendGrid getSendGrid() {
		if (StringUtils.isNotEmpty(SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_SENDGRID_APIKEY))) {
			return new SendGrid(SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_SENDGRID_APIKEY));
		} else if (StringUtils.isNotEmpty(SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_SENDGRID_USERNAME)) && StringUtils.isNotEmpty(SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_SENDGRID_USERNAME))) {
			return new SendGrid(SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_SENDGRID_USERNAME), SimpleGalleryServer.SETTINGS.getString(Keys.EMAIL_SENDGRID_PASSWORD));
		}
		throw new RuntimeException("SendGrid is not setup");
	}

	private String getEmailVerificationLink(EmailConfirm emailConfirm) {
		StringBuilder builder = new StringBuilder(SimpleGalleryServer.SETTINGS.getString(Keys.GALLERY_HOST));
		builder.append("/verify/").append(emailConfirm.getUser()).append("/").append(emailConfirm.getKey());
		return builder.toString();
	}

	private String getPasswordResetLink(PasswordReset passwordReset) {
		StringBuilder builder = new StringBuilder(SimpleGalleryServer.SETTINGS.getString(Keys.GALLERY_HOST));
		builder.append("/reset/").append(passwordReset.getUser()).append("/").append(passwordReset.getKey());
		return builder.toString();
	}
}
