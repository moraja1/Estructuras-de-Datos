package cr.ac.una.util.service;

import javax.swing.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Georges Alfaro S.
 * @version 1.0
 */
public class PathUtils {
    private static final String IMAGES = "\\cr\\ac\\una\\images\\";

    public static String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    public static String getHomeDirectory() {
        return System.getProperty("user.home");
    }

    public static String getLocalPath(String newFileName) {
        return FileSystems.getDefault().getPath(getWorkingDirectory(), newFileName).toString();
    }

    public static String getResourcesDirectory() {
        return FileSystems.getDefault().getPath(getWorkingDirectory() + "\\src\\main\\resources").toString();
    }

    public static String getResourceDirectory(String fileName) {
        return FileSystems.getDefault().getPath(getWorkingDirectory() + "\\src\\main\\resources", fileName).toString();
    }

    public static String getImagesDirectory() {
        return FileSystems.getDefault().getPath(String.format("%s%s", getResourcesDirectory(), IMAGES)).toString();
    }

    public static String getUserPath(String newFileName) {
        return FileSystems.getDefault().getPath(getHomeDirectory(), newFileName).toString();
    }

    public static String getFileNameWithoutExtension(String fileName) {
        String r = null;
        try {
            r = fileName.replaceFirst("[.][^.]+$", "");
        } catch (Exception ex) {
            System.err.printf("Exception: '%s'%n", ex.getMessage());
        }
        return r;
    }

    public static String getFileNameExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    public static String appendToFilePath(String filePath, String suffix) {
        Path path = Paths.get(filePath);
        return String.format("%s%s.%s",
                PathUtils.getFileNameWithoutExtension(filePath), suffix,
                PathUtils.getFileNameExtension(filePath));
    }
}
