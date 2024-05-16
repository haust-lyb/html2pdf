package com.haustlyb.html2pdf;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.haustlyb.html2pdf.dtos.PDF;
import com.haustlyb.html2pdf.dtos.PDFS;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author haust_lyb
 * email : 1570194845@qq.com
 * @date 2020/11/30 下午4:26
 */
public class MainTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(SystemUtil.getUserInfo().getHomeDir());
        test_pdf();
    }

    public static void test_pdf(){

        //模板的基础rest接口地址
        String templateBaseAddress = "http://localhost:16666/html2pdf/renderTemplate";
        //生成PDF的rest接口地址
        String serverAddress = "http://localhost:16666/html2pdf/generatePDF";

        //PDF1 所使用的数据
        HashMap data1 = new HashMap();
        data1.put("title","标题1");
        data1.put("name","患者姓名1");
        data1.put("patientName","患者姓名1");
        data1.put("users", CollUtil.newArrayList("张三","李四","王五"));
        //数据的构建
        PDF pdf = new PDF("a.pdf", encodeURI(templateBaseAddress + "?templateName=test&jsonData=" + JSONUtil.toJsonStr(data1)));



        String jsonData = JSONUtil.toJsonStr(pdf);

        //调用生成
        System.out.println(HttpUtil.post(serverAddress, jsonData));
    }

    /**
     * {
     * "zipName":"张三的PDF文件.zip",
     * "pdfs":[
     * {"name":"a.pdf","htmlUrl":"http://localhost:9002/renderTemplate?templateName=demo&jsonData=%7B%22title%22:%22PDF%E7%94%9F%E6%88%90%E7%9A%84%E6%A0%87%E9%A2%98%22%7D"},
     * {"name":"b.pdf","htmlUrl":"http://localhost:9002/renderTemplate?templateName=demo&jsonData=%7B%22title%22:%22PDF%E7%94%9F%E6%88%90%E7%9A%84%E6%A0%87%E9%A2%982%22%7D"}
     * ]
     * }
     */
    public static void test_pdfs(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("titile","pdf标题");
        System.out.println(encodeURI("http://localhost:10002/renderTemplate?templateName=demo&jsonData="+JSONUtil.toJsonStr(map)));


        //模板的基础rest接口地址
        String templateBaseAddress = "http://localhost:10002/renderTemplate";
        //生成PDF的rest接口地址
        String serverAddress = "http://localhost:10002/generatePDFs";

        //PDF1 所使用的数据
        HashMap data1 = new HashMap();
        data1.put("title","标题1");
        data1.put("name","患者姓名1");
        data1.put("users", CollUtil.newArrayList("张三","李四","王五"));
        //PDF2 所使用的数据
        HashMap data2 = new HashMap();
        data2.put("title","标题2");
        data2.put("name","患者姓名2");
        data2.put("users", CollUtil.newArrayList("小红","晓明","小刚"));

        //数据的构建
        java.util.List<PDF> _pdfs = new ArrayList<>();
        _pdfs.add(new PDF("a.pdf",encodeURI(templateBaseAddress+"?templateName=demo&jsonData="+ JSONUtil.toJsonStr(data1))));
        _pdfs.add(new PDF("b.pdf",encodeURI(templateBaseAddress+"?templateName=demo&jsonData="+ JSONUtil.toJsonStr(data2))));
        PDFS pdfs = new PDFS();
        pdfs.setZipName("压缩包1.zip");
        pdfs.setPdfs(_pdfs);


        String jsonData = JSONUtil.toJsonStr(pdfs);

        //调用生成
        System.out.println(HttpUtil.post(serverAddress, jsonData));
    }


    /**
     * 对URL进行编码 转义一些特殊符号比如{、}等
     *
     * @param path 要转码的URL
     * @return
     */
    public static String encodeURI(String path) {
        // return URLUtil.encode(path);
        //下边是通过Java中的js引擎实现的和js一样的encodeURI方法 已经被上边的方法URLUtil.encode(path)方法所取代
       ScriptEngineManager manager = new ScriptEngineManager();
       javax.script.ScriptEngine engine = manager.getEngineByName("javascript");
       try {
           String str = "encodeURI('" + path + "')";
           String tmp = (String) engine.eval(str);
           System.out.println(path + " ==> " + tmp);
           path = tmp;
       } catch (ScriptException ex) {
           ex.printStackTrace();
       }

       return path;
    }
}
