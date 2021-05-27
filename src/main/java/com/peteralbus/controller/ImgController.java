package com.peteralbus.controller;

import com.peteralbus.domain.Img;
import com.peteralbus.domain.Result;
import com.peteralbus.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/img")
@CrossOrigin
public class ImgController
{
    @Autowired
    private ImgService imgService;
    @GetMapping("/getimglist")
    public List<Img> getImgList()
    {
        return imgService.findAll();
    }
    @GetMapping("/img/{id}")
    public Img SelectImg(@PathVariable("id") int id){
        return imgService.findByID(id);
    }
    @GetMapping("/getpathlist")
    public List<String> getPathList()
    {
        List<Img> imgList=imgService.findAll();
        List<String> pathList = new ArrayList<>();
        for(Img img:imgList)
        {
            pathList.add(img.getImgPath());
        }
        return pathList;
    }
    @GetMapping("/gettarpathlist")
    public List<String> getTarPathList()
    {
        List<Img> imgList=imgService.findAll();
        List<String> pathList = new ArrayList<>();
        for(Img img:imgList)
        {
            pathList.add(img.getTarPath());
        }
        return pathList;
    }
    @DeleteMapping("/delete/{id}")
    public HashMap<String, Object> DeleteImg(@PathVariable("id") int id){
        Img detection = imgService.findByID(id);
        File_delete(detection.getImgPath());
        File_delete(detection.getTarPath());
        imgService.deleteImg(id);
        HashMap<String, Object> res_delete = new HashMap<>();
        Result result = Result.succ(true);
        res_delete.put("data", result);
        return res_delete;
    }
    public static Boolean File_delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean delete = file.delete();
            System.out.println("===========删除成功=================");
            return true;
        } else {
            System.out.println("===============删除失败==============");
            return false;
        }
    }
    @PostMapping("/call_model")
    public String callModel(@RequestBody Integer[] needCall,HttpServletRequest request)
    {
        System.out.println(request.getServletContext().getRealPath("/"));
        System.out.println("post->call_model");
        for (Integer integer : needCall) {
            System.out.println(integer);
        }
        return "success";
    }
}
