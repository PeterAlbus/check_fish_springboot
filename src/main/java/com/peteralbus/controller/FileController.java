package com.peteralbus.controller;

import com.peteralbus.domain.Img;
import com.peteralbus.domain.Result;
import com.peteralbus.service.ImgService;
import com.peteralbus.service.image_detection.image_detection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags = "文件上传")
@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController
{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private ImgService imgService;
    private String filePath = getUploadPath();
    private List<String> File_list = null;

    // 文件上传 （可以多文件上传）
    @ApiOperation("文件的上传")
    @ApiImplicitParams({@ApiImplicitParam(name="file",value = "jpg图片文件",dataType = "MultipartFile",dataTypeClass = MultipartFile.class,required = true,paramType = "form")})
    @PostMapping("/upload")
    public Result fileUploads(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException
    {
        System.out.println("fileUpload");
        // 得到格式化后的日期
        String format = sdf.format(new Date());
        // 获取上传的文件名称
        String fileName = file.getOriginalFilename();
        // 时间 和 日期拼接
        String newFileName = format + "_" + fileName;
        // 得到文件保存的位置以及新文件名
        System.out.println(newFileName);
        File dest = new File(filePath + newFileName);
//        log.info(String.valueOf(file.getSize()));
        try {
            // 上传的文件被保存了
            file.transferTo(dest);
            //图片识别
            Img detection = image_detection.performInference(filePath + newFileName);
            detection.setImgName(newFileName);
            String imgPath="http://47.117.160.245:8080/pictrue/"+newFileName;
            String tarPath=imgPath.substring(0, imgPath.length()-4) + "_detection.jpg";
            detection.setImgPath(imgPath);
            detection.setTarPath(tarPath);
            imgService.insertImage(detection);
            // 打印日志
            //log.info("上传成功，当前上传的文件保存在 {}",filePath + newFileName);
            System.out.println("上传成功，当前上传的文件保存在"+filePath + newFileName);
            // 自定义返回的统一的 JSON 格式的数据，可以直接返回这个字符串也是可以的。
            return Result.succ("上传成功");
        } catch (IOException e) {
            //log.error(e.toString());
        }
        // 待完成 —— 文件类型校验工作
        return Result.fail("上传错误");
    }

    @GetMapping("/uploadpath")
    private String getUploadPath()
    {
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        String path=jarF.getParentFile().toString();
        File file=new File(path+"/upload/","this_is_upload_folder.txt");
        try {
            boolean newFile = file.createNewFile();
        } catch (IOException e) {
            System.out.println("upload目录未创建");
        }
        //System.out.println(jarF.getParentFile().toString());
        return path+"/upload/";
    }
}
