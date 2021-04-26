package com.haustlyb.html2pdf.dtos;

import cn.hutool.core.date.DateUtil;
import com.haustlyb.html2pdf.entitys.LYB_Template;
import lombok.Data;

@Data
public class TemplateDTO {
    private String id;
    private String name;
    private String html;
    private String desc;
    private String testData;
    private String createDateTimeStr;

    public static TemplateDTO toDTO(LYB_Template entity){
        TemplateDTO dto = new TemplateDTO();
        dto.setDesc(entity.getDesc());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreateDateTimeStr(DateUtil.formatDateTime(entity.getAuditDate()));
        return dto;
    }

    public static TemplateDTO toInfoDTO(LYB_Template entity) {
        TemplateDTO dto = new TemplateDTO();
        dto.setDesc(entity.getDesc());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setHtml(entity.getHtml());
        dto.setTestData(entity.getTestData());
        dto.setCreateDateTimeStr(DateUtil.formatDateTime(entity.getAuditDate()));
        return dto;
    }
}
