package com.peteralbus.domain;

import org.apache.ibatis.type.Alias;

@Alias("img")
public class Img
{
    private Integer id=0;
    private String imgName;
    private String imgPath;
    private String tarPath;
    private float tarClass;
    private float tarLength;
    private float tarScore;

    public Img() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTarPath() {
        return tarPath;
    }

    public void setTarPath(String tarPath) {
        this.tarPath = tarPath;
    }

    public float getTarClass() {
        return tarClass;
    }

    public void setTarClass(float tarClass) {
        this.tarClass = tarClass;
    }

    public float getTarLength() {
        return tarLength;
    }

    public void setTarLength(float tarLength) {
        this.tarLength = tarLength;
    }

    public float getTarScore() {
        return tarScore;
    }

    public void setTarScore(float tarScore) {
        this.tarScore = tarScore;
    }

    public Img(Integer id, String imgName, String imgPath, String tarPath, float tarClass, float tarLength, float tarScore) {
        this.id = id;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.tarPath = tarPath;
        this.tarClass = tarClass;
        this.tarLength = tarLength;
        this.tarScore = tarScore;
    }
}
