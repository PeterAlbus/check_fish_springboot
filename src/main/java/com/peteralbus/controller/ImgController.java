package com.peteralbus.controller;

import com.peteralbus.domain.Img;
import com.peteralbus.domain.Result;
import com.peteralbus.service.ImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Api(tags = "识别图片数据管理")
@RestController
@RequestMapping("/img")
@CrossOrigin
public class ImgController
{
    @Autowired
    private ImgService imgService;
    @ApiOperation("获取所有识别图片的信息列表")
    @GetMapping("/getimglist")
    public List<Img> getImgList()
    {
        return imgService.findAll();
    }
    @ApiOperation("获取指定id识别图片的信息")
    @GetMapping("/img/{id}")
    public Img SelectImg(@PathVariable("id") int id){
        return imgService.findByID(id);
    }
    @ApiOperation("获取所有识别图片（原图片）的路径列表")
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
    @ApiOperation("获取上传时间的列表")
    @GetMapping("/getTimeList")
    public List<Date> getTimeList()
    {
        List<Img> imgList=imgService.findAll();
        List<Date> timeList = new ArrayList<>();
        for(Img img:imgList)
        {
            timeList.add(img.getUploadTime());
        }
        return timeList;
    }
    @ApiOperation("获取所有识别图片（画框后）的路径列表")
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
    @ApiOperation("删除指定id的识别图片及其信息")
    @DeleteMapping("/delete/{id}")
    public HashMap<String, Object> DeleteImg(@PathVariable("id") int id){
        Img detection = imgService.findByID(id);
        if(File_delete(detection.getImgPath()))
        {
            if(File_delete(detection.getTarPath()))
            {
                imgService.deleteImg(id);
                HashMap<String, Object> res_delete = new HashMap<>();
                Result result = Result.succ(true);
                res_delete.put("data", result);
                return res_delete;
            }
        }
        HashMap<String, Object> res_delete = new HashMap<>();
        Result result = Result.fail("删除失败");
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
    @ApiOperation("对指定图片列表调用模型")
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
    @ApiOperation("获取上传时间在指定区间内的时间")
    @PostMapping("/getImgByTime")
    public List<Img> getImgByTime(@RequestParam("sTime") String start,@RequestParam("eTime") String end)
    {
        return imgService.findImgByTime(start,end);
    }
}
