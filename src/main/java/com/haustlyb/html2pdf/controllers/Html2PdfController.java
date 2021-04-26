package com.haustlyb.html2pdf.controllers;


import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.haustlyb.html2pdf.dtos.PDF;
import com.haustlyb.html2pdf.dtos.PDFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

@Controller
@ApiIgnore
public class Html2PdfController {

    @Value("${hsr.file.uploadPath}")
    String uploadPath;

    private static TimedCache<String, String> timedCache = CacheUtil.newTimedCache(1000 * 60 * 5);

    @RequestMapping("bindData")
    @ResponseBody
    public String bindData(String jsonData) {
        String key = IdUtil.fastUUID();
        timedCache.put(key, jsonData);
        return key;
    }

    /**
     * @param jsonData 模板绑定的数据
     * @param request
     * @return
     */
    @RequestMapping("renderTemplate")
    public String renderTemplate(String jsonData, String templateName, HttpServletRequest request) {
        System.out.println("jsonData:" + jsonData);
        System.out.println("templateName:" + templateName);
        JSONObject jsonObject = JSONUtil.parseObj(jsonData);
        request.setAttribute("data", jsonObject);
        return templateName + ".html";
    }

    /**
     * @param key     绑定的数据
     * @param request
     * @return
     */
    @RequestMapping("renderTemplateByKey")
    public String renderTemplateByKey(String key, String templateName, HttpServletRequest request) {
        System.out.println("key:" + key);
        System.out.println("keyvalue:" + timedCache.get(key));
        System.out.println("templateName:" + templateName);
        JSONObject jsonObject = JSONUtil.parseObj(timedCache.get(key));
        request.setAttribute("data", jsonObject);
        return templateName + ".html";
    }

    @RequestMapping("generatePDFs")
    @ResponseBody
    public HashMap generatePDFs(@RequestBody PDFS pdfs) throws InterruptedException, IOException {

        try {
            String basePath = uploadPath + IdUtil.fastUUID() + "/";
            String basePath_tmp = uploadPath + IdUtil.fastUUID() + "/";
            for (PDF pdf : pdfs.getPdfs()) {
                String pdfName = pdf.getName();
                String pdfSourceUrl = pdf.getHtmlUrl();
                String command = StrUtil.format("wkhtmltopdf --post aaaa 1111 {} {}", pdfSourceUrl, basePath + pdfName);
                if (!FileUtil.exist(basePath)) {
                    FileUtil.mkdir(basePath);
                }
                if (!FileUtil.exist(basePath_tmp)) {
                    FileUtil.mkdir(basePath_tmp);
                }
                System.out.println(command);
                Boolean execRs = executeCommand(command);
                if (execRs) {
                    System.out.println("↑ command执行OK！");
                } else {
                    System.out.println("↑ command执行异常ERROR！");
                }
            }

            File file = ZipUtil.zip(basePath, basePath_tmp + pdfs.getZipName(), false);
            return saySucccess("获取成功", file.getAbsolutePath());
        } catch (Exception e) {
            return saySucccess("获取失败");
        }
    }

    /**
     * 单个PDF生成
     *
     * @param pdf
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    @RequestMapping("generatePDF")
    @ResponseBody
    public HashMap generatePDF(@RequestBody PDF pdf) throws InterruptedException, IOException {

        try {
            String basePath = uploadPath + IdUtil.fastUUID() + "/";
            String pdfName = pdf.getName();
            String pdfSourceUrl = pdf.getHtmlUrl();

            String command = StrUtil.format("wkhtmltopdf --post aaaa 1111 {} {}", pdfSourceUrl, basePath + pdfName);
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


            return saySucccess("获取成功", basePath + pdfName);
        } catch (Exception e) {
            return saySucccess("获取失败");
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


    //封装下载功能 filename为用户下载时候浏览器下载的文件的文件名

    /**
     * @param file     源文件
     * @param filename 用户下载时候浏览器下载的文件的文件名
     * @param response 当前请求的相应response
     * @throws UnsupportedEncodingException
     */

    @Deprecated
    public void downLoad(File file, String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        if (file.exists()) { //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public HashMap<String, Object> saySucccess(String msg, Object entity) {
        HashMap rs = new HashMap();
        rs.put("code", "SUCCESS");
        rs.put("status", "0");
        rs.put("entity", entity);
        return rs;
    }

    public HashMap<String, Object> saySucccess(String msg) {
        HashMap rs = new HashMap();
        rs.put("code", "SUCCESS");
        rs.put("status", "0");
        return rs;
    }

    public HashMap<String, Object> sayFail(String msg) {
        HashMap rs = new HashMap();
        rs.put("code", "ERROR");
        rs.put("status", "-1");
        return rs;
    }
}
