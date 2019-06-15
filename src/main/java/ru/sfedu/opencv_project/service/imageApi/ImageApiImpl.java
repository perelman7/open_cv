package ru.sfedu.opencv_project.service.imageApi;

import nu.pattern.OpenCV;
import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.opencv_project.constant.Constant;
import ru.sfedu.opencv_project.urils.ConfigurationUtil;
import ru.sfedu.opencv_project.urils.MatchOS;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * @author Saburov Aleksey
 * @date 16.03.2019 11:50
 */

public class ImageApiImpl implements ImageApi{

    private ConfigurationUtil configurationUtil = new ConfigurationUtil(Constant.PROPERTY_PATH);
    private Logger logger = Logger.getLogger(ImageApiImpl.class);

    public ImageApiImpl() {
        logger.info("Start ImageApiImpl class");
        try {
            switch (MatchOS.getOperatingSystemType()) {
                case WINDOWS:
                    System.load(configurationUtil.readConfig("pathToNativeLib"));
                    logger.info("The OS is Windows");
                    break;
                default:
                    logger.info("The OS is other");
                    break;
            }
        } catch (Throwable ex) {
            logger.error(ex.getMessage());
            OpenCV.loadLocally();
        }

    }

    @Override
    public Mat readBytes(String filepath, int chanel) {
        if(chanel < 3){
            Mat srcImage = Imgcodecs.imread(filepath);
            int totalBytes = (int) (srcImage.total() * srcImage.elemSize());
            byte buffer[] = new byte[totalBytes];
            srcImage.get(0, 0, buffer);
            for (int i = 0; i < totalBytes; i++) {
                if (i % srcImage.channels() == 0) {
                    buffer[i] = 0;
                }
            }
            srcImage.put(0, 0, buffer);
            return srcImage;
        }
        return null;
    }

    @Override
    public Mat createMat(String filepath){
        if(filepath != null){
            return Imgcodecs.imread(filepath);
        }
        return null;
    }

    @Override
    public void showImg(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] b = new byte[bufferSize];
        mat.get(0, 0, b);

        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        ImageIcon icon = new ImageIcon(image);

        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth(null) + 50, image.getHeight(null) + 50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
