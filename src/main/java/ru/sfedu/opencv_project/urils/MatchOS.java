package ru.sfedu.opencv_project.urils;

import org.apache.log4j.Logger;
import ru.sfedu.opencv_project.model.enums.OSType;

import java.util.Locale;

/**
 * @author Saburov Aleksey
 * @date 13.04.2019 10:27
 */

public class MatchOS {

    private static Logger logger = Logger.getLogger(MatchOS.class);

    public static OSType getOperatingSystemType() {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        logger.info("System property return " + OS);

        if ((OS.toLowerCase().contains("mac")) || (OS.toLowerCase().contains("darwin"))) {
            return OSType.MACOS;
        } else if (OS.toLowerCase().contains("win")) {
            return OSType.WINDOWS;
        } else if (OS.toLowerCase().contains("nux")) {
            return OSType.LINUX;
        } else {
            return OSType.OTHER;
        }
    }
}
