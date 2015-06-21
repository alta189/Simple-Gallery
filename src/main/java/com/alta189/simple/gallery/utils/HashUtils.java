package com.alta189.simple.gallery.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class HashUtils {
    public static String getSHA1(File file) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

            try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
                final byte[] buffer = new byte[1024];
                for (int read = 0; (read = is.read(buffer)) != -1;) {
                    messageDigest.update(buffer, 0, read);
                }
            }

            // Convert the byte to hex format
            try (Formatter formatter = new Formatter()) {
                for (final byte b : messageDigest.digest()) {
                    formatter.format("%02x", b);
                }
                return formatter.toString().toUpperCase();
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> getDirectoryHashes(File directory) {
        Map<String, Object> result = new HashMap<>();
        if (directory == null || !directory.isDirectory() || directory.listFiles() == null || directory.listFiles().length < 1) {
            return null;
        }
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                result.put(file.getName(), getDirectoryHashes(file));
            } else {
                result.put(file.getName(), HashUtils.getSHA1(file));
            }
        }
        return result;
    }
}
