package com.alta189.simple.gallery;

import com.alta189.auto.spark.AutoSpark;
import com.alta189.simple.gallery.api.Images;
import com.alta189.simple.gallery.auth.AuthenticationManager;
import com.alta189.simple.gallery.objects.Message;
import com.alta189.simple.gallery.objects.MessagePosition;
import com.alta189.simple.gallery.objects.MessageStyle;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.utils.DateTimeTypeConverter;
import com.alta189.simple.gallery.utils.MessagePositionTypeAdapter;
import com.alta189.simple.gallery.utils.MessageStyleTypeAdapter;
import com.alta189.simplesave.Database;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import spark.resource.ClassPathResource;
import spark.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.alta189.simple.gallery.SimpleGalleryConstants.FileLocations.IMAGES_DIRECTORY;
import static com.alta189.simple.gallery.SimpleGalleryConstants.FileLocations.SETTINGS_FILE;
import static com.alta189.simple.gallery.SimpleGalleryConstants.FileLocations.TEMP_DIRECTORY;
import static com.alta189.simple.gallery.SimpleGalleryConstants.Settings.Defaults;
import static com.alta189.simple.gallery.SimpleGalleryConstants.Settings.Keys;

public class SimpleGalleryServer {
	public static final Gson GSON = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.serializeNulls()
			.setDateFormat("MM/dd/yyyy hh:mm:ss aa")
			.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
			.registerTypeAdapter(MessagePosition.class, new MessagePositionTypeAdapter<>())
			.registerTypeAdapter(MessageStyle.class, new MessageStyleTypeAdapter<>())
			.create();
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss aa");
	public static final DatabaseManager DATABASE_MANAGER = new DatabaseManager();
	private static final Logger logger = LoggerFactory.getLogger(SimpleGalleryServer.class);
	public static AboutInfo ABOUT;
	public static XMLConfiguration SETTINGS;
	public static Map<String, String> VERSION_INFO;
	public static String VERSION_INFO_JSON;
	public static AutoSpark AUTO_SPARK;

	static {
		ABOUT = ConfigFactory.create(AboutInfo.class);

		VERSION_INFO = new HashMap<>();
		VERSION_INFO.put("version", ABOUT.version());
		VERSION_INFO.put("build", String.valueOf(ABOUT.build()));
		VERSION_INFO.put("build-date", ABOUT.buildDate());

		VERSION_INFO_JSON = Result.wrap(VERSION_INFO).toJson();
	}

	public static void main(String[] args) {
		DateTime start = DateTime.now();
		printBanner(start);

		checkSettingsFile();
		try {
			SETTINGS = new XMLConfiguration(SETTINGS_FILE);
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}

		MailManager.getInstance().checkTemplates();

		DateTimeZone zone = DateTimeZone.forID(SETTINGS.getString(Keys.SERVER_TIMEZONE, Defaults.SERVER_TIMEZONE));
		DateTimeZone.setDefault(zone);

		TEMP_DIRECTORY.getPath().toFile().mkdirs();
		TEMP_DIRECTORY.deleteOnExit();

		IMAGES_DIRECTORY.mkdirs();

		final File uploadDir = new File(TEMP_DIRECTORY.getPath().toFile(), "upload");
		uploadDir.mkdir();

		Images.setInstance(new Images(uploadDir));

		setupDatabase();

		Spark.port(SETTINGS.getInt(Keys.SERVER_PORT, Defaults.SERVER_PORT));
		Spark.externalStaticFileLocation("public");
		Spark.staticFileLocation("public");

		AUTO_SPARK = new AutoSpark();
		AUTO_SPARK.run();

		AuthenticationManager.getInstance().scan();

		System.out.println("INITIALIZED");
	}

	private static void printBanner(DateTime start) {
		try {
			String contents = IOUtils.toString(new ClassPathResource("banner.txt").getInputStream());
			System.out.print(contents);

			System.out.println("Server Start: " + start.toString(DATE_FORMAT));
			System.out.println();
			System.out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void checkSettingsFile() {
		if (!SETTINGS_FILE.exists()) {
			try {
				String contents = IOUtils.toString(new ClassPathResource(SETTINGS_FILE.getName()).getInputStream());
				FileUtils.writeStringToFile(SETTINGS_FILE, contents);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Server has not yet been configured.");
			System.out.println("Default settings has been created in the root directory.");
			System.out.println("Please configure and then restart the server.");
			System.exit(-1);
		}
	}

	private static void setupDatabase() {
		DATABASE_MANAGER.init();
		DATABASE_MANAGER.load();
		DATABASE_MANAGER.connect();

		// Clear Messages because all previous session ids are invalid
		DATABASE_MANAGER.getDatabase().clear(Message.class);
	}

	public static Database getDatabase() {
		return DATABASE_MANAGER.getDatabase();
	}
}
