package com.haustlyb.html2pdf.component.init;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author haust_lyb
 * email : 1570194845@qq.com
 * @date 2020/9/18 下午12:12
 */
@Component
public class InitDataBean {

    @Transactional
    public void initData(){
        System.out.println("项目初始化数据");
    }
}
