package com.haustlyb.html2pdf.dtos;

import lombok.Data;

/**
 * @author haust_lyb
 * email : 1570194845@qq.com
 * @date 2020/11/30 下午4:01
 */
@Data
public class PDFS {
    String zipName;
    java.util.List<PDF> pdfs;
}
