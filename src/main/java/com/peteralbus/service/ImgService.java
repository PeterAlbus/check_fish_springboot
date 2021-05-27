package com.peteralbus.service;

import com.peteralbus.domain.Img;

import java.util.List;

public interface ImgService
{
    public List<Img> findAll();
    public void updateImage(Img img);
    public Img findByID(int id);
    public int insertImage(Img img);
    void deleteImg(int id);
}
