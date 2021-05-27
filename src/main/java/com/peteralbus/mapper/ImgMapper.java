package com.peteralbus.mapper;

import com.peteralbus.domain.Img;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ImgMapper
{
    List<Img> findAll();
    void updateImage(Img img);
    Img findByID(Integer id);
    int insertImage(Img img);
    void deleteImg(int id);
}
