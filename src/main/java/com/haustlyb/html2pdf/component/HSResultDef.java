package com.haustlyb.html2pdf.component;

import java.util.List;

/**
 * Created by kadyvivian on 2015/7/12.
 */
public interface HSResultDef {

    //结果为正常状态
    public static int STATUS_SUCCESS = 0;

    //结果为失败的状态
    public static int STATUS_FAILED = -1;

    //服务处理结果为多个错误的状态
    public static int STATUS_MUL_FAILED = -2;

    /**
     * 返回的状态
     */
    public int getStatus();

    /**
     * 设置返回结消息
     */
    public String getMessage();

    /**
     * 获取成功的消息
     */
    public String getSuccessMessage();

    /**
     * 获取错误的信息
     */
    public String getErrorMessage();

    /**
     * 获取错误消息列表
     */
    public List<String> getErrorList();

    /**
     * 返回操作的中心实体
     * @return
     */
    public Object getEntity();

}
