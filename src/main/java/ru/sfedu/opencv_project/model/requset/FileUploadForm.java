package ru.sfedu.opencv_project.model.requset;

/**
 * @author Saburov Aleksey
 * @date 13.06.2019 23:49
 */

import javax.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUploadForm {

    public FileUploadForm() {
    }

    private byte[] data;
    private Integer chanel;

    public byte[] getData() {
        return data;
    }

    @FormParam("uploadedFile")
    @PartType("application/octet-stream")
    public void setData(byte[] data) {
        this.data = data;
    }

    public Integer getChanel() {
        return chanel;
    }

    @FormParam("chanel")
    public void setChanel(Integer chanel) {
        this.chanel = chanel;
    }
}
