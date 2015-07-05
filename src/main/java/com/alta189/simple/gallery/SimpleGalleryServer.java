package com.alta189.simple.gallery;

import com.alta189.auto.spark.AutoSpark;
import com.alta189.simple.gallery.api.Images;
import com.alta189.simple.gallery.objects.Result;
import com.alta189.simple.gallery.utils.DateTimeTypeConverter;
import com.alta189.simple.gallery.utils.HashUtils;
import com.alta189.simple.gallery.utils.TempDirectory;
import com.alta189.simplesave.Database;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.lingala.zip4j.core.ZipFile;
import org.aeonbits.owner.ConfigFactory;
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
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SimpleGalleryServer {
    public static AboutInfo ABOUT;
    public static ServerSettings SETTINGS;
    public static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .setDateFormat("MM/dd/yyyy hh:mm:ss aa")
            .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
            .create();
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss aa");
    public static final TempDirectory TEMP_DIRECTORY = new TempDirectory("simple-gallery-");
    public static final File RESOURCES_DIRECTORY = new File("resources");
	public static final File IMAGES_DIRECTORY = new File("images");
	public static final DatabaseManager DATABASE_MANAGER = new DatabaseManager();
    private static final Logger logger = LoggerFactory.getLogger(SimpleGalleryServer.class);
	public static Map<String, String> VERSION_INFO;
	public static String VERSION_INFO_JSON;

    static {
        ABOUT = ConfigFactory.create(AboutInfo.class);
        SETTINGS = ConfigFactory.create(ServerSettings.class);

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

	    DateTimeZone zone = DateTimeZone.forID(SETTINGS.timezone());
	    DateTimeZone.setDefault(zone);

        TEMP_DIRECTORY.getPath().toFile().mkdirs();
        TEMP_DIRECTORY.deleteOnExit();

	    IMAGES_DIRECTORY.mkdir();

	    final File uploadDir = new File(TEMP_DIRECTORY.getPath().toFile(), "upload");
	    uploadDir.mkdir();

	    Images.setInstance(new Images(uploadDir));

        processResources();


	    setupDatabase();

        Spark.port(SETTINGS.port());
	    Spark.externalStaticFileLocation("resources");

	    AutoSpark autoSpark = new AutoSpark();
	    autoSpark.run();

	    Spark.before("/api/albums/create", ((request, response) -> {
		    System.out.println("FILTER");
		    request.params().forEach(logger::info);
	    }));

        Spark.awaitInitialization();
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
        File settingsFile = new File("server.properties");
        if (!settingsFile.exists()) {
            try {
                String contents = IOUtils.toString(new ClassPathResource("server.properties").getInputStream());
                FileUtils.writeStringToFile(settingsFile, contents);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Server has not yet been configured.");
            System.out.println("Default settings has been created in the root directory.");
            System.out.println("Please configure and then restart the server.");
            System.exit(-1);
        }
    }

    private static void processResources() {
        if (!checkResources()) {
            logger.info("Setting up resources");
            RESOURCES_DIRECTORY.delete();
            downloadFoundation();
            logger.info("Finished setting up resources");
        }
    }

    private static boolean checkResources() {
        logger.info("Checking resources");

        if (!RESOURCES_DIRECTORY.exists() || !RESOURCES_DIRECTORY.isDirectory()) {
            return false;
        }

        Map<String, Object> expected = GSON.fromJson(SimpleGalleryConstants.Resources.RESOURCES_SHA1_MAP, SimpleGalleryConstants.GsonTypes.TYPE_MAP_STRING_OBJECT);
        Map<String, Object> actual = HashUtils.getDirectoryHashes(RESOURCES_DIRECTORY);
	    return actual.equals(expected);
    }

    private static void downloadFoundation() {
        int tries = 0;
        boolean finished = false;

        while (tries < 5 && !finished) {
            if (tries != 0) {
                logger.warn("Downloading Foundation failed");
            }
            File tempDownload = null;
            logger.info("Trying to download Foundation.");
            try {
                tempDownload = File.createTempFile("zurb-foundation-5.2.2-", ".zip", TEMP_DIRECTORY.getPath().toFile());
                FileUtils.copyURLToFile(new URL(SimpleGalleryConstants.Resources.ZURB_FOUNDATION_5_2_2_DOWNLOAD), tempDownload);
                String downloadedFileHash = HashUtils.getSHA1(tempDownload);
                if (SimpleGalleryConstants.Resources.ZURB_FOUNDATION_5_2_2_SHA1.equals(downloadedFileHash)) {
                    ZipFile zipFile = new ZipFile(tempDownload);
                    zipFile.extractAll("resources");
                    new File(RESOURCES_DIRECTORY, "humans.txt").delete();
                    new File(RESOURCES_DIRECTORY, "index.html").delete();
                    new File(RESOURCES_DIRECTORY, "robots.txt").delete();

                    Map<String, Object> expected = GSON.fromJson(SimpleGalleryConstants.Resources.RESOURCES_SHA1_MAP, SimpleGalleryConstants.GsonTypes.TYPE_MAP_STRING_OBJECT);
                    Map<String, Object> actual = HashUtils.getDirectoryHashes(RESOURCES_DIRECTORY);

                    if (actual.equals(expected)) {
                        finished = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (tempDownload != null) {
                    tempDownload.delete();
                }
            }
            tries ++;
        }
        logger.info("Finished downloading Foundation");
        if (!finished) {
            logger.error("Could not download Foundation; Exiting");
            System.exit(-1);
        }
    }

    private static void setupDatabase() {
	    DATABASE_MANAGER.init();
	    DATABASE_MANAGER.load();
	    DATABASE_MANAGER.connect();
    }

	public static Database getDatabase() {
		return DATABASE_MANAGER.getDatabase();
	}
}
