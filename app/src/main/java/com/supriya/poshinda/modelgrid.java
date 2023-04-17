package com.supriya.poshinda;

public class modelgrid {

    private String textimg;
    private int imgid;

    public modelgrid(String textimg, int imgid) {
        this.textimg = textimg;
        this.imgid = imgid;
    }

    public String getTextimg() {
        return textimg;
    }

    public void setTextimg(String textimg) {
        this.textimg = textimg;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
