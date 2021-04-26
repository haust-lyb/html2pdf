package com.haustlyb.html2pdf.component.init;

import com.haustlyb.html2pdf.util.HSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitApplication implements ApplicationContextAware {


    @Autowired
    InitDataBean initDataBean;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        HSUtil.setApplicationContext(applicationContext);

        initDataBean.initData();
    }
}
