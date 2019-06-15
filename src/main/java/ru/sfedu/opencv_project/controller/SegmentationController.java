package ru.sfedu.opencv_project.controller;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import ru.sfedu.opencv_project.model.requset.FillFloodForm;
import ru.sfedu.opencv_project.model.requset.SegmentationForm;
import ru.sfedu.opencv_project.model.requset.SubtractForm;
import ru.sfedu.opencv_project.service.imageApi.ImageApi;
import ru.sfedu.opencv_project.service.imageApi.ImageApiImpl;
import ru.sfedu.opencv_project.service.segmentation.SegmentationService;
import ru.sfedu.opencv_project.service.segmentation.SegmentationServiceImpl;
import ru.sfedu.opencv_project.urils.fileWriter.FileWriterService;
import ru.sfedu.opencv_project.urils.fileWriter.FileWriterServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.nio.file.Paths;

/**
 * @author Saburov Aleksey
 * @date 14.06.2019 21:31
 */
@Path("/segment")
public class SegmentationController {

    private Logger logger = Logger.getLogger(SegmentationController.class);
    private SegmentationService segmentationService = new SegmentationServiceImpl();
    private FileWriterService fileWriterService = new FileWriterServiceImpl();
    private ImageApi imageApi = new ImageApiImpl();

    @POST
    @Path("/fillFlood")
    @Produces({"image/jpeg", "image/png"})
    @Consumes("multipart/form-data")
    public Response fillFlood(@MultipartForm FillFloodForm form) {
        logger.info("Start /fillFlood controller input value: " + form.toString());
        if(form != null){
            String filepath = fileWriterService.writeFile(form.getFilebody(), form.getFilename());
            logger.info("Save file: " + filepath);
            if(filepath != null){
                String savedFile = segmentationService.fillFlood(filepath, form.getInitValue(), form.getFilename());
                if(savedFile != null){
                    byte[] bytes = fileWriterService.getBytes(Paths.get(savedFile));
                    logger.info("After method: " + savedFile + ", response body: " + bytes.length);
                    boolean deleteFile = fileWriterService.deleteFile(savedFile);
                    boolean deleteFile1 = fileWriterService.deleteFile(filepath);
                    logger.info("Result bytes : " + bytes.length + ", deleted files : " + (deleteFile && deleteFile1));
                    return Response.ok(bytes).header("Content-Disposition", "attachment;filename=response.png").build();
                }
                fileWriterService.deleteFile(savedFile);
                fileWriterService.deleteFile(filepath);
            }
        }
        return Response.status(HttpStatus.SC_BAD_REQUEST).build();
    }

    @POST
    @Path("/subtract")
    @Produces({"image/jpeg", "image/png"})
    @Consumes("multipart/form-data")
    public Response subtract(@MultipartForm SubtractForm form) {
        logger.info("Start /subtract controller input value: " + form.toString());
        if(form != null){
            String filepath = fileWriterService.writeFile(form.getFilebody(), form.getFilename());
            logger.info("Filepath: " + filepath);
            if(filepath != null){
                String savedFile = segmentationService.subtract(filepath);
                logger.info("Saved file path: " + savedFile);
                if(savedFile != null){
                    byte[] bytes = fileWriterService.getBytes(Paths.get(savedFile));
                    boolean deleteFile = fileWriterService.deleteFile(savedFile);
                    boolean deleteFile1 = fileWriterService.deleteFile(filepath);
                    logger.info("Result bytes : " + bytes.length + ", deleted files : " + (deleteFile && deleteFile1));
                    return Response.ok(bytes).header("Content-Disposition", "attachment;filename=response.png").build();
                }
                fileWriterService.deleteFile(savedFile);
                fileWriterService.deleteFile(filepath);
            }
        }
        return Response.status(HttpStatus.SC_BAD_REQUEST).build();
    }

    @POST
    @Path("/segmentation")
    //@Produces({"image/jpeg", "image/png"})
    @Consumes("multipart/form-data")
    public Response segmentation(@MultipartForm SegmentationForm form) {
        logger.info("Start /segmentation controller input value: " + form.toString());
        if(form != null){
            String filepath = fileWriterService.writeFile(form.getFilebody(), form.getFilename());
            if(filepath != null){
                Mat mat = imageApi.createMat(filepath);
                Size min = new Size(form.getMin(), form.getMin());
                Size max = new Size(form.getMax(), form.getMax());
                int segmentation = segmentationService.segmentation(mat, min, max);
                boolean deleteFile = fileWriterService.deleteFile(filepath);
                logger.info("Size min: " + min + ", max " + max + ", segments: " + segmentation + ", delete file: " + deleteFile);
                return Response.ok("There are " + segmentation + " rectangles").build();
            }
            fileWriterService.deleteFile(filepath);
        }
        return Response.status(HttpStatus.SC_BAD_REQUEST).build();
    }

}
