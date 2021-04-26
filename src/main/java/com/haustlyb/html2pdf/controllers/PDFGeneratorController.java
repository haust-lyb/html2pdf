package com.haustlyb.html2pdf.controllers;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.haustlyb.html2pdf.component.HSResult;
import com.haustlyb.html2pdf.entitys.LYB_Template;
import com.haustlyb.html2pdf.util.HSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

@Controller
@Api(value = "PDF生成", tags = "PDF生成")
public class PDFGeneratorController {

    @Value("${hsr.file.uploadPath}")
    String uploadPath;

    private static TimedCache<String, HashMap> timedCache = CacheUtil.newTimedCache(1000 * 60 * 5);

    @Autowired
    GroupTemplate groupTemplate;

    @Value("${server.port}")
    private String serverport;

    @Value("${server.servlet.context-path}")
    private String contextpath;

    @PostMapping ("generatePDF")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateId", value = "模板id", required = true),
            @ApiImplicitParam(name = "jsonData", value = "模板数据", required = true)
    })
    @ApiOperation(value = "生成PDF文件", notes = "返回PDF的系统磁盘路径")
    public HSResult onlineTemplateRendering(String templateId, String jsonData, HttpServletResponse response) {
        HashMap<String,String> target = new HashMap<>();
        target.put("templateId",templateId);
        target.put("jsonData",jsonData);
        String targetId = IdUtil.fastUUID();
        timedCache.put(targetId,target);
        try {
            String basePath = uploadPath + IdUtil.fastUUID() + "/";
            String pdfName = IdUtil.fastUUID()+".pdf";
            String pdfSourceUrl= "http://localhost:"+serverport+contextpath+"/rendTemplate?targetId="+targetId;
            String command = StrUtil.format("wkhtmltopdf {} {}", pdfSourceUrl, basePath + pdfName);
            if (!FileUtil.exist(basePath)) {
                FileUtil.mkdir(basePath);
            }
            System.out.println(command);
            Boolean execRs = executeCommand(command);
            if (execRs) {
                System.out.println("↑ command执行OK！");
            } else {
                System.out.println("↑ command执行异常ERROR！");
            }
            return HSResult.saySuccess("获取成功:"+basePath+pdfName);
        } catch (Exception e) {
            return HSResult.sayFail("获取失败");
        }
    }


    @GetMapping("rendTemplate")
    public void rendTemplate(String targetId,HttpServletResponse response){
        HashMap metadata = timedCache.get(targetId);
        System.out.println(metadata);
        LYB_Template template = (LYB_Template) HSUtil.query("from LYB_Template e where e.id = :id").setParameter("id", metadata.get("templateId")).uniqueResult();
        String html = template.getHtml();
        System.out.println(html);
        Template t = groupTemplate.getTemplate(html);
        JSONObject jsonObject = JSONUtil.parseObj(metadata.get("jsonData"));
        t.binding(jsonObject);
        try {
            response.setCharacterEncoding("UTF-8");
            t.renderTo(response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("previewTemplate")
    public void rendTemplate(String template,String jsonData,HttpServletResponse response){
        String html = template;
        Template t = groupTemplate.getTemplate(html);
        JSONObject jsonObject = JSONUtil.parseObj(jsonData);
        t.binding(jsonObject);
        try {
            response.setCharacterEncoding("UTF-8");
            t.renderTo(response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //执行系统命令
    public Boolean executeCommand(String command) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读取命令的输出信息
        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (p.exitValue() != 0) {
            System.out.println("执行失败");
            //说明命令执行失败
            //可以进入到错误处理步骤中
            return false;
        } else {
            System.out.println("执行成功");
            return true;
        }

    }

}
