package ru.sfedu.opencv_project.service.morfology;

import org.opencv.core.Mat;

/**
 * @author Saburov Aleksey
 * @date 15.06.2019 9:49
 */
public interface MorphologyExecutorService {

    String morfologyErode(Mat element, String prefix);

    String morfologyDilate(Mat element, String prefix);

    Mat getMatBySize(int size, String mattype);
}
