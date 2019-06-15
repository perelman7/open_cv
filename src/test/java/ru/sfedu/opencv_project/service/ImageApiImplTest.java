package ru.sfedu.opencv_project.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import ru.sfedu.opencv_project.service.imageApi.ImageApi;
import ru.sfedu.opencv_project.service.imageApi.ImageApiImpl;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;


public class ImageApiImplTest {

    private ImageApi api;
    private static final String FILE_NAME = "number.jpg";

    @Before
    public void open() {
        api = new ImageApiImpl();
    }

    @Test
    public void doTest() throws IOException {
        Mat mat = api.readBytes(FILE_NAME, 3);
        assertNotNull(mat);
    }

    @After
    public void close() {
        api = null;
    }
}
