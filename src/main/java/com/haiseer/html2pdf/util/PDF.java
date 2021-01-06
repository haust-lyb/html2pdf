package com.haiseer.html2pdf.util;

import lombok.Data;

/**
 * @author haust_lyb
 * email : 1570194845@qq.com
 * @date 2020/11/30 下午4:01
 */
@Data
public class PDF {
    String name;
    String htmlUrl;
    public PDF(){}
    public PDF(String name,String htmlUrl){
        this.name = name;
        this.htmlUrl = htmlUrl;
    }
}
