package ru.sfedu.opencv_project.model.requset;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;

/**
 * @author Saburov Aleksey
 * @date 14.06.2019 19:31
 */

public class MorfologyDilateForm {

    private Integer size;
    private String filename;
    private String mattype;
    private byte[] filebody;

    public MorfologyDilateForm() {
    }

    public Integer getSize() {
        return size;
    }

    @FormParam("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    @FormParam("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMattype() {
        return mattype;
    }

    @FormParam("mattype")
    public void setMattype(String mattype) {
        this.mattype = mattype;
    }

    public byte[] getFilebody() {
        return filebody;
    }

    @FormParam("fileBody")
    @PartType("application/octet-stream")
    public void setFilebody(byte[] filebody) {
        this.filebody = filebody;
    }

    @Override
    public String toString() {
        return "MorfologyDilateForm{" +
                "size=" + size +
                ", filename='" + filename + '\'' +
                ", mattype='" + mattype + '\'' +
                ", filebody=" + filebody.length +
                '}';
    }
}
