package com.wlg.Controller;

import com.wlg.Util.Contant;
import com.wlg.Util.FileUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by LvLiangFeng on 2016/11/30.
 */
@Controller
@RequestMapping(value="test")
public class TestController extends BaseController{


    @RequestMapping(value="/testCanceAjax.do",method = RequestMethod.POST)
    @ResponseBody
    public String testCanceAjax(){
        Thread thread = Thread.currentThread();
        try {
            thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "do it";
    }
    @RequestMapping(value="/uploadtest.do",method = RequestMethod.POST)
    public String add_LocationData(@RequestParam(value = "file", required = false) MultipartFile file, String param) throws IOException {
        JSONObject json = new JSONObject();
        if(param==null | ("").equals(param)){
            json.put("status",0);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(json.toString());
            return null;
        }else {
            //文件处理
            if (file.getSize() > 0) {
                String path = (Contant.uploadPath + "test/");

                //先删除所有文件
                FileUtil.deleteAllFiles(path);

                String fileName = file.getOriginalFilename();
                //重命名文件名
                //        String fileName = new Date().getTime()+".jpg";
                String extName = fileName.substring(fileName.lastIndexOf("."));
                String newName = "test" + "_" + System.currentTimeMillis() + extName;
                File targetFile = new File(path, newName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }

                try {
                    file.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            json.put("status",1);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().write(json.toString());
            return null;
        }
    }
}
