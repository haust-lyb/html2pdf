package com.haustlyb.html2pdf.component;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kadyvivian on 2015/7/12.
 */
@Data
public class HSResult<ENTITY> implements HSResultDef {

    private int status = HSResultDef.STATUS_SUCCESS;
    private String message = "";
    private String code = ""; //错误代码
    private String successMessage = "";
    private String errorMessage = "";
    private ENTITY entity = null;
    private List<String> errorList = new LinkedList<>();

    //构造函数私有化，不允许外部创建对象
    private HSResult() {}

    public boolean success() {
        return status == 0;
    }

    //说成功
    public static HSResult saySuccess(String message) {

        HSResult rs = new HSResult();

        rs.setMessage(message);
        rs.setSuccessMessage(message);
        rs.setStatus(HSResult.STATUS_SUCCESS);
        rs.setCode("SUCCESS");
        return rs;
    }

    public static HSResult sayFail(String message) {
        return sayFail(message, false);
    }

    public static HSResult sayFail(String message, String code) {

        HSResult rs = getResult(message);
        rs.setCode(code);
        return rs;
    }

    private static HSResult getResult(String message) {

        HSResult rs = new HSResult();

        rs.setMessage(message);
        rs.setErrorMessage(message);
        rs.setStatus(HSResultDef.STATUS_FAILED);
        return rs;
    }

    //说失败（说一个失败，说多个失败）
    public static HSResult sayFail(String message, boolean hasTrans) {

        try {
            if (hasTrans == true) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return getResult(message);
    }


    //configure
    public HSResult confCode(String code) {
        this.setCode(code);
        return this;
    }

    public HSResult confEntity(ENTITY entity) {
        this.setEntity(entity);
        return this;
    }

    //创建
    public static HSResult create() {
        return new HSResult();
    }

    //收集错误
    public HSResult collectionError(String errorMsg) {
        setStatus(HSResult.STATUS_MUL_FAILED);
        errorList.add(errorMsg);
        return this;
    }

    //将自己转换成json格式输出
    public String toJsonString() {
        return JSONUtil.toJsonStr(this);
    }

    public HSResult setCode(String code){
        this.code = code;
        return this;
    }

    public HSResult setEntity(ENTITY entity) {
        this.entity = entity;
        return this;
    }
}
