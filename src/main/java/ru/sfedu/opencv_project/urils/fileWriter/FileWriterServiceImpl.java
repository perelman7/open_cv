package ru.sfedu.opencv_project.urils.fileWriter;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import ru.sfedu.opencv_project.constant.Constant;
import ru.sfedu.opencv_project.urils.ConfigurationUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Saburov Aleksey
 * @date 14.06.2019 0:14
 */

public class FileWriterServiceImpl implements FileWriterService {

    private static Logger logger = Logger.getLogger(FileWriterServiceImpl.class);
    private static ConfigurationUtil configurationUtil = new ConfigurationUtil(Constant.PROPERTY_PATH);
    private static String dirPath = configurationUtil.readConfig("image_path");

    @Override
    public String writeFile(byte[] body, String filename) {
        logger.info("Start write file method");
        String pathToFile = dirPath + filename;
        boolean result = false;
        try {
            FileUtils.writeByteArrayToFile(new File(pathToFile), body);
            result = true;
        } catch (IOException e) {
            logger.error("Can`t write file");
        }
        return result ? pathToFile : null;
    }

    @Override
    public boolean deleteFile(String filepath) {
        if (filepath != null && !filepath.isEmpty()) {
            logger.info("Start method deleteFile(), input param: " + filepath);
            boolean result = false;
            Path path = Paths.get(filepath);
            try {
                result = Files.deleteIfExists(path);
            } catch (IOException e) {
                logger.error("Can`t delete file with path: " + path);
            }
            return result;
        }
        logger.info("File path is empty or null");
        return false;
    }

    @Override
    public byte[] getBytes(Path path) {
        if (path != null) {
            byte[] result = null;
            try {
                result = Files.readAllBytes(path);
            } catch (IOException e) {
                logger.error("Can`t read bytes from file");
            }
            return result;
        }
        return null;
    }
}
