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
    }

    public static class GsonTypes {
        public static final Type TYPE_MAP_STRING_OBJECT = new TypeToken<Map<String, Object>>() {
        }.getType();
    }

    public static class Results {
	    public static final Result SUCCESS = Result.wrap("success");
    }
}
