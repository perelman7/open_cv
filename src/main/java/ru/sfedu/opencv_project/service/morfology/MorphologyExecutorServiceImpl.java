package ru.sfedu.opencv_project.service.morfology;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import ru.sfedu.opencv_project.constant.Constant;
import ru.sfedu.opencv_project.model.enums.MatType;
import ru.sfedu.opencv_project.service.imageApi.ImageApiImpl;
import ru.sfedu.opencv_project.urils.ConfigurationUtil;

import java.util.Arrays;

/**
 * @author Saburov Aleksey
 * @date 13.04.2019 10:45
 */

public class MorphologyExecutorServiceImpl implements MorphologyExecutorService {

    private Logger logger = Logger.getLogger(MorphologyExecutorServiceImpl.class);

    private ConfigurationUtil configurationUtil = new ConfigurationUtil(Constant.PROPERTY_PATH);
    private String destDirPath = configurationUtil.readConfig("image_path");
    private ImageApiImpl api = new ImageApiImpl();

    private static String FILENAME = "number.jpg";
    private static final String PRFNAME = "mrf_";

    private Mat src;
    private Mat dst;

    public MorphologyExecutorServiceImpl() {
        src = Imgcodecs.imread(destDirPath + FILENAME, Imgcodecs.IMREAD_COLOR);
    }

    public MorphologyExecutorServiceImpl(String filepath, String filename) {
        src = Imgcodecs.imread(filepath, Imgcodecs.IMREAD_COLOR);
        FILENAME = filename;
    }

    @Override
    public String morfologyErode(Mat element, String prefix) {
        if (element != null && prefix != null && !prefix.isEmpty()) {
            String newFilePath = destDirPath + "/test/" + PRFNAME + prefix + FILENAME;
            try {
                dst = src.clone();
                Imgproc.erode(src, dst, element);
                Imgcodecs.imwrite(newFilePath, dst);
            } catch (Exception ex) {
                logger.error(this.getClass().getName() + " : " + Arrays.toString(ex.getStackTrace()));
            }
            return newFilePath;
        }
        return null;
    }

    @Override
    public String morfologyDilate(Mat element, String prefix) {
        if (element != null && prefix != null && !prefix.isEmpty()) {
            String newFilePath = destDirPath + "/test/" + PRFNAME + prefix + FILENAME;
            try {
                dst = src.clone();
                Imgproc.dilate(src, dst, element);
                Imgcodecs.imwrite(newFilePath, dst);
            } catch (Exception ex) {
                logger.error(this.getClass().getName() + " : " + Arrays.toString(ex.getStackTrace()));
            }
            return newFilePath;
        }
        return null;
    }

    @Override
    public Mat getMatBySize(int size, String mattype) {
        logger.info("Start mat by size: " + size + " mattype: " + mattype);
        if (size <= 10) {
            MatType matType = MatType.valueOf(mattype);
            logger.info("mat type : " + matType.name());
            switch (MatType.valueOf(mattype)) {
                case MORPH_RECT:
                    return Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(size, size));
                case MORPH_CROSS:
                    return Imgproc.getStructuringElement(Imgproc.MARKER_CROSS, new Size(size, size));
                case MORPH_ELLIPSE:
                    return Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(size, size));
            }
        }
        return null;
    }
}
