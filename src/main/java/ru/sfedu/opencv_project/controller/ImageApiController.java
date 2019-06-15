package ru.sfedu.opencv_project.controller;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.opencv_project.model.requset.FileUploadForm;
import ru.sfedu.opencv_project.service.imageApi.ImageApi;
import ru.sfedu.opencv_project.service.imageApi.ImageApiImpl;
import ru.sfedu.opencv_project.urils.fileWriter.FileWriterService;
import ru.sfedu.opencv_project.urils.fileWriter.FileWriterServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * @author Saburov Aleksey
 * @date 25.05.2019 11:48
 */

@Path("/imageApiImpl")
public class ImageApiController {

    private Logger logger = Logger.getLogger(ImageApiController.class);
    private FileWriterService fileWriter = new FileWriterServiceImpl();
    private static final String FILENAME = "temporarily.png";

    private ImageApi imageApiImpl = new ImageApiImpl();

    @POST
    @Path("")
    @Produces({"image/jpeg", "image/png"})
    @Consumes("multipart/form-data")
    public Response upload(@MultipartForm FileUploadForm form) {
        logger.info("---------------------------------\nStart upload method");
        byte[] data = form.getData();

        String filepath = fileWriter.writeFile(data, FILENAME);
        logger.info("File path is : " + filepath);
        logger.info("Chanel is: " + form.getChanel());

        Mat mat = imageApiImpl.readBytes(filepath, form.getChanel());
        Imgcodecs.imwrite(filepath, mat);

        File file = new File(filepath);
        logger.info("Filename: " + file.getName());
        byte[] bytes = fileWriter.getBytes(file.toPath());

        boolean deleteFile = fileWriter.deleteFile(filepath);
        logger.info("Result delete : " + deleteFile);

        return Response.ok(bytes).header("Content-Disposition", "attachment;filename=" + "test.png").build();
    }

}
