package ru.sfedu.opencv_project.service.segmentation;

import org.apache.log4j.Logger;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import ru.sfedu.opencv_project.constant.Constant;
import ru.sfedu.opencv_project.service.imageApi.ImageApiImpl;
import ru.sfedu.opencv_project.urils.ConfigurationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Saburov Aleksey
 * @date 25.05.2019 10:18
 */

public class SegmentationServiceImpl implements SegmentationService {

    private ImageApiImpl imageApiImpl = new ImageApiImpl();
    private Logger logger = Logger.getLogger(ImageApiImpl.class);
    private ConfigurationUtil configurationUtil = new ConfigurationUtil(Constant.PROPERTY_PATH);
    private String directory = configurationUtil.readConfig("dir_segment");

    @Override
    public String fillFlood(String path, int initVal, String filename) {
        if (path != null && !path.isEmpty() && filename != null && !filename.isEmpty()) {
            logger.info("Start fillFlood, input path: " + path + ", initValue: " + initVal);
            Mat srcImage = Imgcodecs.imread(path);
            Point seedPoint = new Point(0, 0);
            Scalar newVal = new Scalar(0, 255, 0);
            Scalar loDiff = new Scalar(initVal, initVal, initVal);
            Scalar upDiff = new Scalar(initVal, initVal, initVal);
            Mat mask = new Mat();

            Imgproc.floodFill(srcImage, mask, seedPoint, newVal, new Rect(), loDiff, upDiff,
                    Imgproc.FLOODFILL_FIXED_RANGE);
            String returnPath = directory + "/" + filename;
            logger.info("Saved file is : " + returnPath);
            Imgcodecs.imwrite(returnPath, srcImage);
            return returnPath;
        }
        return null;
    }

    @Override
    public String subtract(String path) {
        Mat srcImage = Imgcodecs.imread(path);
        logger.info(srcImage);
        Mat mask = new Mat();
        Imgproc.pyrDown(srcImage, mask);
        Imgproc.pyrUp(mask, mask);
        if (srcImage.width() % 2 == 0 && srcImage.height() % 2 == 0) {
            logger.info("Inside");
            Core.subtract(srcImage, mask, mask);

            String returnPath = directory + "/fillFood.jpg";
            Imgcodecs.imwrite(returnPath, mask);
            return returnPath;
        }
        return null;
    }

    @Override
    public int segmentation(Mat srcImage, Size min, Size max) {

        Mat grayImage = new Mat();
        Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);

        Mat denoisingImage = new Mat();
        Photo.fastNlMeansDenoising(grayImage, denoisingImage);

        Mat histogramEqualizationImage = new Mat();
        Imgproc.equalizeHist(denoisingImage, histogramEqualizationImage);

        Mat morphologicalOpeningImage = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.morphologyEx(histogramEqualizationImage, morphologicalOpeningImage, Imgproc.MORPH_RECT, kernel);

        Mat subtractImage = new Mat();
        Core.subtract(histogramEqualizationImage, morphologicalOpeningImage, subtractImage);

        Mat thresholdImage = new Mat();
        double threshold = Imgproc.threshold(subtractImage, thresholdImage, 50, 255, Imgproc.THRESH_OTSU);
        thresholdImage.convertTo(thresholdImage, CvType.CV_16SC1);

        Mat edgeImage = new Mat();
        thresholdImage.convertTo(thresholdImage, CvType.CV_8U);

        Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);
        Mat dilatedImage = new Mat();
        Imgproc.dilate(thresholdImage, dilatedImage, kernel);

        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(dilatedImage, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));

        Mat mm = Mat.zeros(dilatedImage.size(), CvType.CV_8UC3);
        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(Math.random() * 255, Math.random() * 255, Math.random() * 255);
            Imgproc.drawContours(mm, contours, i, color);
        }

        List<Mat> mats = new ArrayList<>();

        for (MatOfPoint contour : contours) {
            MatOfPoint2f point2f = new MatOfPoint2f();
            MatOfPoint2f approxContour2f = new MatOfPoint2f();
            MatOfPoint approxContour = new MatOfPoint();
            contour.convertTo(point2f, CvType.CV_32FC2);

            double arcLength = Imgproc.arcLength(point2f, true); // длина кривой - true - кривая замкнутая
            Imgproc.approxPolyDP(point2f, approxContour2f, 0.03 * arcLength, true);
            approxContour2f.convertTo(approxContour, CvType.CV_32S);

            if (approxContour.total() == 4) {
                List<Point> points = approxContour.toList();

                double d1 = Math.sqrt(Math.pow((points.get(0).x - points.get(1).x), 2) + Math.pow((points.get(0).y - points.get(1).y), 2));
                double d2 = Math.sqrt(Math.pow((points.get(2).x - points.get(3).x), 2) + Math.pow((points.get(2).y - points.get(3).y), 2));

                Rect rect = Imgproc.boundingRect(approxContour);

                if (rect.width >= min.width && rect.width <= max.width
                        && rect.height >= min.height && rect.height <= max.height
                        && (Math.abs(d1 - d2) < 5)) {

                    // for output captures
                    Mat mcontour = Mat.zeros(dilatedImage.size(), CvType.CV_8UC3);
                    Scalar color = new Scalar(Math.random() * 255, Math.random() * 255, Math.random() * 255);
                    ArrayList<MatOfPoint> aa = new ArrayList<>();
                    aa.add(approxContour);
                    Imgproc.drawContours(mcontour, aa, 0, color);

                    double ratio = (double) rect.height / rect.width;
                    Mat submat = srcImage.submat(rect);
                    Imgproc.resize(submat, submat, new Size(400, 400 * ratio));
                    mats.add(submat);
                }
            }
        }
        return mats.size();
    }
}
