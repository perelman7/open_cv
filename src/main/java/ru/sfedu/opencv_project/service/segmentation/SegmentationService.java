package ru.sfedu.opencv_project.service.segmentation;

import org.opencv.core.Mat;
import org.opencv.core.Size;

/**
 * @author Saburov Aleksey
 * @date 15.06.2019 9:51
 */
public interface SegmentationService {

    String fillFlood(String path, int initVal, String filename);

    String subtract(String path);

    int segmentation(Mat srcImage, Size min, Size max);
}
