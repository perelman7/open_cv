package ru.sfedu.opencv_project.service;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.opencv_project.constant.Constant;
import ru.sfedu.opencv_project.service.segmentation.SegmentationService;
import ru.sfedu.opencv_project.service.segmentation.SegmentationServiceImpl;
import ru.sfedu.opencv_project.service.imageApi.ImageApi;
import ru.sfedu.opencv_project.service.imageApi.ImageApiImpl;
import ru.sfedu.opencv_project.urils.ConfigurationUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;


public class SegmentationServiceImplTest {

    private ImageApi imageApiImpl = new ImageApiImpl();
    private SegmentationService segmentationServiceImpl;
    private Logger logger = Logger.getLogger(SegmentationServiceImplTest.class);

    private ConfigurationUtil configurationUtil = new ConfigurationUtil(Constant.PROPERTY_PATH);
    private String directory = configurationUtil.readConfig("dir_segment");
    private String filepath = configurationUtil.readConfig("image_path");
    private String srcImgName = "number.jpg";
    private String fileFigures = "fig.jpg";

    @Before
    public void before() {
        segmentationServiceImpl = new SegmentationServiceImpl();
    }

    @Test
    public void testFillFlood() {
        int initValue = 50;
        String returnedFilepath = segmentationServiceImpl.fillFlood(filepath + srcImgName, initValue, srcImgName);
        assertNotNull(returnedFilepath);
    }

    @Test
    public void testSubtract() {
        String subtract = segmentationServiceImpl.subtract(filepath + srcImgName);
        assertNotNull(subtract);
    }

    @Test
    public void testSegmentation() {
        Size min = new Size(50, 50);
        Size max = new Size(600, 600);
        Mat src = Imgcodecs.imread(filepath + fileFigures, Imgcodecs.IMREAD_COLOR);
        int number = segmentationServiceImpl.segmentation(src, min, max);
        logger.info(number);
    }

    @After
    public void after() {
        segmentationServiceImpl = null;
        File dir = new File(directory);
        if (dir.isDirectory()) {
            logger.info("Directory: " + dir.getAbsolutePath());
            try {
                FileUtils.cleanDirectory(dir);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
