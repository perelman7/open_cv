package ru.sfedu.opencv_project.urils.fileWriter;

import java.nio.file.Path;

/**
 * @author Saburov Aleksey
 * @date 15.06.2019 9:57
 */
public interface FileWriterService {

    String writeFile(byte[] body, String filename);

    boolean deleteFile(String filepath);

    byte[] getBytes(Path path);
}
