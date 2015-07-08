package com.alta189.simple.gallery;

import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.utils.TempDirectory;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Map;

public class SimpleGalleryConstants {
    public static class FileLocations {
	    public static final TempDirectory TEMP_DIRECTORY = new TempDirectory("simple-gallery-");
	    public static final File PUBLIC_DIRECTORY = new File("public");
	    public static final File IMAGES_DIRECTORY = new File(PUBLIC_DIRECTORY, "images");
	    public static final File TEMPLATES_DIRECTORY = new File("templates");
	    public static final File SETTINGS_FILE = new File("settings.xml");
    }

	public static class Settings {
		public static class Keys {
			public static final String SERVER_PORT = "server.port";
			public static final String SERVER_TIMEZONE = "server.timezone";
			public static final String GALLERY_NAME = "gallery.name";
			public static final String GALLERY_DESCRIPTION = "gallery.description";
			public static final String GALLERY_HOST = "gallery.host";
			public static final String GALLERY_WEBADMIN = "gallery.webadmin";
			public static final String EMAIL_SENDGRID_APIKEY = "email.sendgrid.apikey";
			public static final String EMAIL_SENDGRID_USERNAME = "email.sendgrid.username";
			public static final String EMAIL_SENDGRID_PASSWORD = "email.sendgrid.password";
			public static final String EMAIL_ADDRESS_EMAIL_VALIDATION = "email.address.email-validation";
			public static final String EMAIL_ADDRESS_FORGOT_PASSWORD = "email.address.forgot-password";
			public static final String EMAIL_SUBJECT_EMAIL_VALIDATION = "email.subject.email-validation";
			public static final String EMAIL_SUBJECT_FORGOT_PASSWORD = "email.subject.forgot-password";
			public static final String EMAIL_ADDRESS_CONTACT_US = "email.address.contact-us";
		}
		public static class Defaults {
			public static final int SERVER_PORT = 8080;
			public static final String SERVER_TIMEZONE = "EST5EDT";
			public static final String GALLERY_NAME = "Simple Gallery";
			public static final String GALLERY_DESCRIPTION = "Simple Gallery example";
			public static final String GALLERY_HOST = "http://localhost";
			public static final String GALLERY_WEBADMIN = "Simple Gallery Team";
		}
	}

	public static class Templates {
		public static final File EMAIL_VERIFICATION = new File(FileLocations.TEMPLATES_DIRECTORY, "email_verification.md");
		public static final File RESET_PASSWORD = new File(FileLocations.TEMPLATES_DIRECTORY, "reset_password.md");

		public static class Variables {
			public static String GALLERY_NAME = "{{gallery-name}}";
			public static String GALLERY_DESCRIPTION = "{{gallery-description}}";
			public static String GALLERY_HOST = "{{gallery-host}}";
			public static String GALLERY_WEBADMIN = "{{gallery-webadmin}}";
			public static String USER_NAME = "{{user-name}}";
			public static String USER_EMAIL = "{{user-email}}";
			public static String EMAIL_CONTACT_US = "{{email-contact-us}}";
			public static String NOW = "{{now}}";
			public static String EMAIL_VERIFICATION_LINK = "{{email-verification-link}}";
			public static String RESET_PASSWORD_LINK = "{{password-reset-link}}";
		}
	}

    public static class GsonTypes {
        public static final Type TYPE_MAP_STRING_OBJECT = new TypeToken<Map<String, Object>>() {
        }.getType();
    }

    public static class Results {
	    public static final Result SUCCESS = Result.wrap("success");
    }
}
