package com.haustlyb.html2pdf.services;

import com.haustlyb.html2pdf.component.HSResult;
import com.haustlyb.html2pdf.dtos.TemplateDTO;
import com.haustlyb.html2pdf.entitys.LYB_Template;
import com.haustlyb.html2pdf.util.HSUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TemplateService {

    public HSResult addTemplate(String html,String name,String desc,String testData){
        LYB_Template._create(LYB_Template.class,e->{
            e.setName(name);
            e.setHtml(html);
            e.setDesc(desc);
            e.setTestData(testData);
        });
        return HSResult.saySuccess("添加成功");
    }

    public HSResult deleteTemplate(String id) {
        LYB_Template template = (LYB_Template) HSUtil.query("from LYB_Template e where e.id = :id")
                .setParameter("id", id).uniqueResult();
        template.delete();
        return HSResult.saySuccess("删除成功");
    }

    public HSResult queryAllTemplate() {
        List<LYB_Template> templates = HSUtil.query("FROM LYB_Template e ").list();
        List<TemplateDTO> templateDTOS = new ArrayList<>();
        for (LYB_Template template : templates) {
            templateDTOS.add(TemplateDTO.toDTO(template));
        }
        return HSResult.saySuccess("查询成功").confEntity(templateDTOS);
    }

    public HSResult getDemplateById(String id) {
        LYB_Template template = (LYB_Template) HSUtil.query("FROM LYB_Template e where e.id = :id").setParameter("id", id).uniqueResult();
        return HSResult.saySuccess("查询成功").confEntity(TemplateDTO.toInfoDTO(template));
    }

    public HSResult editTemplate(String id, String html, String name, String desc,String testData) {
        LYB_Template template = (LYB_Template) HSUtil.query("FROM LYB_Template e where e.id = :id").setParameter("id", id).uniqueResult();
        template.update(e->{
            e.setHtml(html);
            e.setName(name);
            e.setDesc(desc);
            e.setTestData(testData);
        });
        return HSResult.saySuccess("修改成功");
    }
}
