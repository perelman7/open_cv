package ru.sfedu.opencv_project.service;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import ru.sfedu.opencv_project.constant.Constant;
import ru.sfedu.opencv_project.service.imageApi.ImageApi;
import ru.sfedu.opencv_project.service.imageApi.ImageApiImpl;
import ru.sfedu.opencv_project.service.morfology.MorphologyExecutorService;
import ru.sfedu.opencv_project.service.morfology.MorphologyExecutorServiceImpl;
import ru.sfedu.opencv_project.urils.ConfigurationUtil;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;


public class MorphologyExecutorServiceImplTest {


    /**
     * Load library OpenCV
     */
    private ImageApi imageApiImpl = new ImageApiImpl();
    private MorphologyExecutorService executorService;
    private Logger logger = Logger.getLogger(MorphologyExecutorServiceImplTest.class);

    private static final String ECODE_10 = "erode_10_";
    private static final String ECODE_01 = "erode_01_";
    private static final String ECODE_05 = "erode_05_";
    private static final String DILATE_10 = "dilate_10_";
    private static final String DILATE_01 = "dilate_01_";
    private static final String DILATE_05 = "dilate_05_";

    private Mat element_10 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10, 10));
    private Mat element_01 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
    private Mat element_05 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));

    private ConfigurationUtil configurationUtil = new ConfigurationUtil(Constant.PROPERTY_PATH);
    private String directory = configurationUtil.readConfig("dir_test");


    @Before
    public void before() {
        executorService = new MorphologyExecutorServiceImpl();
    }

    @Test
    public void doTest1() {
        String pathToFile = executorService.morfologyErode(element_10, ECODE_10);
        assertNotNull(pathToFile);
    }

    @Test
    public void doTest2() {
        String pathToFile = executorService.morfologyErode(element_01, ECODE_01);
        assertNotNull(pathToFile);
    }

    @Test
    public void doTest3() {
        String pathToFile = executorService.morfologyErode(element_05, ECODE_05);
        assertNotNull(pathToFile);
    }

    @Test
    public void doTest4() {
        String pathToFile = executorService.morfologyDilate(element_10, DILATE_10);
        assertNotNull(pathToFile);
    }

    @Test
    public void doTest5() {
        String pathToFile = executorService.morfologyDilate(element_01, DILATE_01);
        assertNotNull(pathToFile);
    }

    @Test
    public void doTest6() {
        String pathToFile = executorService.morfologyDilate(element_05, DILATE_05);
        assertNotNull(pathToFile);
    }

    @After
    public void after() {
        executorService = null;
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
