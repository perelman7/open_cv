package ru.sfedu.opencv_project.model.requset;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;

/**
 * @author Saburov Aleksey
 * @date 15.06.2019 10:46
 */

public class SegmentationForm {

    private String filename;
    private byte[] filebody;
    private int min;
    private int max;

    public SegmentationForm() {
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

    public int getMin() {
        return min;
    }

    @FormParam("min")
    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    @FormParam("max")
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "SegmentationForm{" +
                "filename='" + filename + '\'' +
                ", filebody=" + filebody.length +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}
