package com.peteralbus.service.impl;

import com.peteralbus.domain.Img;
import com.peteralbus.mapper.ImgMapper;
import com.peteralbus.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    @Override
    public List<Img> findImgByTime(String start, String end)
    {
        List<Img> imgList=new ArrayList<>();
        List<Img> all=imgMapper.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(start.equals("") && end.equals(""))
        {
            Date eTime=new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(eTime);
            calendar.add(Calendar.DATE, -1);
            Date sTime=calendar.getTime();
            for(Img img:all)
            {
                if(img.getUploadTime().after(sTime)&&img.getUploadTime().before(eTime))
                {
                    imgList.add(img);
                }
            }
            return imgList;
        }
        try
        {
            Date sTime=sdf.parse(start);
            Date eTime=sdf.parse(end);
            for(Img img:all)
            {
                if(img.getUploadTime().after(sTime)&&img.getUploadTime().before(eTime))
                {
                    imgList.add(img);
                }
            }
        }
        catch (ParseException e)
        {
            Img img=new Img();
            img.setImgName("日期格式不正确！");
            imgList.add(img);
            e.printStackTrace();
        }
        return imgList;
    }
}
