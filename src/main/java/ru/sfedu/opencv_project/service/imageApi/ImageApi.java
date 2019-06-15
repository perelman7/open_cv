package ru.sfedu.opencv_project.service.imageApi;

import org.opencv.core.Mat;

/**
 * @author Saburov Aleksey
 * @date 15.06.2019 9:47
 */

public interface ImageApi {

    Mat readBytes(String filepath, int chanel);

    Mat createMat(String filepath);

    void showImg(Mat mat);
}
