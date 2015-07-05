package com.alta189.simple.gallery.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class HashUtils {
    public static String getSHA1(File file) {
        try {
            try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
                return DigestUtils.sha1Hex(is);
            }
        } catch (IOException e) {
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
