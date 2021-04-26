package com.haustlyb.html2pdf.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 李一博 on 22:37 2020/9/21.
 */
@Configuration
public class BehindRunningCfg implements CommandLineRunner {


    @Value("${spring.datasource.url}")
    private String datasourceurl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.h2.console.path}")
    private String h2path;

    @Value("${server.port}")
    private String serverport;

    @Value("${server.servlet.context-path}")
    private String contextpath;






    @Override
    public void run(String... args) {
        System.out.println("#################################################################################################");
        System.out.println("# 项目管理平台访问地址：http://localhost:"+serverport+contextpath);
        System.out.println("# 项目swagger接口访问地址：http://localhost:"+serverport+contextpath+"/swagger-ui.html");
        System.out.println("# ###############################################################################################");
        System.out.println("# 数据库访问地址：http://localhost:"+serverport+contextpath+h2path);
        System.out.println("# 数据库datasourceurl："+datasourceurl);
        System.out.println("# 数据库username："+username);
        System.out.println("# 数据库password："+password);
        System.out.println("#################################################################################################");
    }
}
