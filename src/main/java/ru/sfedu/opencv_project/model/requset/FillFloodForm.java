package ru.sfedu.opencv_project.model.requset;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import java.util.Arrays;

/**
 * @author Saburov Aleksey
 * @date 15.06.2019 10:06
 */

public class FillFloodForm {

    private String filename;
    private int initValue;
    private byte[] filebody;

    public FillFloodForm() {
    }

    public String getFilename() {
        return filename;
    }

    @FormParam("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getInitValue() {
        return initValue;
    }

    @FormParam("initValue")
    public void setInitValue(int initValue) {
        this.initValue = initValue;
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
        return "FillFloodForm{" +
                "filename='" + filename + '\'' +
                ", initValue=" + initValue +
                ", filebody=" + filebody.length +
                '}';
    }
}
