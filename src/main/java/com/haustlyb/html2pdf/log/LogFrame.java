package com.haustlyb.html2pdf.log;

import lombok.extern.slf4j.Slf4j;

//框架内部日志答应
@Slf4j
public class LogFrame {

    public static void info(String message){
        log.info("[框架日志]" + message);
    }
}
