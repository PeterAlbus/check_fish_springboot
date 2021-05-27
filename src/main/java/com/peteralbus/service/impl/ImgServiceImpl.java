package com.peteralbus.service.impl;

import com.peteralbus.domain.Img;
import com.peteralbus.mapper.ImgMapper;
import com.peteralbus.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImgServiceImpl implements ImgService
{
    @Autowired
    private ImgMapper imgMapper;

    @Override
    public List<Img> findAll() {
        return imgMapper.findAll();
    }

    @Override
    public void updateImage(Img img) {
        imgMapper.updateImage(img);
    }

    @Override
    public Img findByID(int id) {
        return imgMapper.findByID(id);
    }

    @Override
    public int insertImage(Img img) {
        return imgMapper.insertImage(img);
    }

    @Override
    public void deleteImg(int id) {
        imgMapper.deleteImg(id);
    }
}
