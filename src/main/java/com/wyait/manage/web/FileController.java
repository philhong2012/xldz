package com.wyait.manage.web;

import com.wyait.manage.utils.Result;
import com.wyait.manage2.other.entity.Attachment;
import com.wyait.manage2.other.service.IAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by phil hong
 * User: wind
 * Date: 2018/6/27
 * Time: 下午9:57
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    IAttachmentService attachmentService;

    @Value("${upload.url}")
    private String basePath;
    //SystemProperties.getProperty("upload.url");
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value="/upload")
    @ResponseBody
    public Result<List>  upload(@RequestParam MultipartFile[] file
                                 ) {
        List<Attachment> arrFiles = new ArrayList<>(10);
        for(MultipartFile file1 : file) {
            if(file1.getSize() > 0) {
                //String fileName = file.getOriginalFilename();
                //201408\180710_1407511489281.jpg
                String datePath = createFolderByDate(basePath);
                String  randomName =UUID.randomUUID().toString();
                String fileName = Paths.get(datePath,randomName).toString();
                String ext = file1.getOriginalFilename().substring(file1.getOriginalFilename().lastIndexOf(".")+1);
                fileName = fileName +"."+ ext;
                Attachment a = new Attachment();
                a.setFileName(file1.getOriginalFilename());
                a.setFilePath(fileName.replace(basePath,""));
                arrFiles.add(a);
                File f = new File(fileName);

                try {
                    file1.transferTo(f);

                } catch (IOException e) {
                    //e.printStackTrace();
                    logger.error("上传出错",e);
                    return new Result<List>("-1","上传失败",arrFiles);
                }
            }
        }
        return new Result<List>("1","上传成功",arrFiles);
    }


    public static void main(String[] args) {
        createFolderByDate("/Users/wind/work");
    }



    @RequestMapping("/showImg")
    public void showImg(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=UTF-8");
            response.setContentType("image/jpeg");
            String fileId = request.getParameter("fileId");
            Attachment a = attachmentService.getById(fileId);
            String absolutePath = Paths.get(basePath,a.getFilePath()).toString();
            FileInputStream fis = new FileInputStream(absolutePath);
            OutputStream os = response.getOutputStream();
            try {
                int count = 0;
                byte[] buffer = new byte[1024 * 1024];
                while ((count = fis.read(buffer)) != -1)
                    os.write(buffer, 0, count);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (os != null)
                    os.close();
                if (fis != null)
                    fis.close();
            }
        } catch (Exception e) {
            logger.error("出错",e);
        }
    }

    @RequestMapping("/delImg")
    @ResponseBody
    public String showImg(String fileId) {

           attachmentService.removeById(fileId);
           return "ok";

    }


    private static String createFolderByDate(String basePath) {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        //String fileName = format.format(calendar.getTime());
        //String fullPath = basePath+ File.separator + fileName;
        Integer year = calendar.get(calendar.YEAR);
        //String yearPath = basePath + File.separator + year.toString()+ File.separator;
        //createFolder(yearPath);
        Integer month = calendar.get(calendar.MONTH) + 1;
        Integer day = calendar.get(calendar.DATE);
        //String datePath = basePath + File.separator + year.toString() +
                //File.separator + month.toString() + "-" + day.toString() + File.separator;
        //createFolder(datePath);

        String yearPath = Paths.get(basePath,year.toString()/*,month.toString() + "-" + day.toString()*/).toString();
        String datePath = Paths.get(basePath,year.toString(),month.toString() + "-" + day.toString()).toString();
        createFolder(yearPath);
        createFolder(datePath);
        //String p = "/wind//fwe/fwef";
        //String p1 = Paths.get(p).toString();
        return datePath;
    }


    private static String createFolder(String path) {
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdir();
        }
        return path;
    }
}
