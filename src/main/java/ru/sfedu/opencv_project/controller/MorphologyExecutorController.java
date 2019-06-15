package ru.sfedu.opencv_project.controller;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.opencv.core.Mat;
import ru.sfedu.opencv_project.model.requset.MorfologyDilateForm;
import ru.sfedu.opencv_project.service.morfology.MorphologyExecutorServiceImpl;
import ru.sfedu.opencv_project.urils.fileWriter.FileWriterServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * @author Saburov Aleksey
 * @date 14.06.2019 18:14
 */

@Path("/morfology")
public class MorphologyExecutorController {

    private Logger logger = Logger.getLogger(MorphologyExecutorController.class);
    private FileWriterServiceImpl fileWriter = new FileWriterServiceImpl();
    private MorphologyExecutorServiceImpl service;

    @POST
    @Path("/dilate")
    @Produces({"image/jpeg", "image/png"})
    @Consumes("multipart/form-data")
    public Response morfologyDilate(@MultipartForm MorfologyDilateForm form) {
        logger.info("Input size: " + form.toString());
        if (form != null) {

            String filepath = fileWriter.writeFile(form.getFilebody(), form.getFilename());
            service = new MorphologyExecutorServiceImpl(filepath, form.getFilename());
            Mat matBySize = service.getMatBySize(form.getSize(), form.getMattype());
            logger.info("Mat: " + matBySize);
            logger.info("filepath:" + filepath);

            if (matBySize != null && filepath != null) {

                logger.info("service: " + service);
                String savedFilePath = service.morfologyDilate(matBySize, form.getMattype());
                logger.info("savedFilePath: " + savedFilePath);
                File file = new File(savedFilePath);
                logger.info("Filename: " + file.getName());
                byte[] bytes = fileWriter.getBytes(file.toPath());
                fileWriter.deleteFile(filepath);
                fileWriter.deleteFile(savedFilePath);
                return Response.ok(bytes).header("Content-Disposition", "attachment;filename=response.png").build();
            }
        }
        return Response.status(HttpStatus.SC_BAD_REQUEST).build();
    }

    @POST
    @Path("/erode")
    @Produces({"image/jpeg", "image/png"})
    @Consumes("multipart/form-data")
    public Response morfologyErode(@MultipartForm MorfologyDilateForm form) {
        logger.info("Input size: " + form.toString());
        if (form != null) {

            String filepath = fileWriter.writeFile(form.getFilebody(), form.getFilename());
            service = new MorphologyExecutorServiceImpl(filepath, form.getFilename());
            Mat matBySize = service.getMatBySize(form.getSize(), form.getMattype());

            if (matBySize != null && filepath != null) {
                logger.info("service: " + service);
                String savedFilePath = service.morfologyErode(matBySize, form.getMattype());

                File file = new File(savedFilePath);

                byte[] bytes = fileWriter.getBytes(file.toPath());
                fileWriter.deleteFile(filepath);
                fileWriter.deleteFile(savedFilePath);
                return Response.ok(bytes).header("Content-Disposition", "attachment;filename=response.png").build();
            }
        }
        return Response.status(HttpStatus.SC_BAD_REQUEST).build();
    }
}
