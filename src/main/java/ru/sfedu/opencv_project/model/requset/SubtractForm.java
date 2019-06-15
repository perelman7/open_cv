package ru.sfedu.opencv_project.model.requset;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;

/**
 * @author Saburov Aleksey
 * @date 15.06.2019 10:37
 */

public class SubtractForm {

    private String filename;
    private byte[] filebody;

    public SubtractForm() {
    }

    public String getFilename() {
        return filename;
    }

    @FormParam("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFilebody() {
        return filebody;
    }

    @FormParam("filebody")
    @PartType("application/octet-stream")
    public void setFilebody(byte[] filebody) {
        this.filebody = filebody;
    }

    @Override
    public String toString() {
        return "SubtractForm{" +
                "filename='" + filename + '\'' +
                ", filebody=" + filebody.length +
                '}';
    }
}
