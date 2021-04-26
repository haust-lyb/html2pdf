package com.haustlyb.html2pdf.controllers;

import com.haustlyb.html2pdf.component.HSResult;
import com.haustlyb.html2pdf.services.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "PDF生成", tags = "PDF生成")
public class TemplateController {
    @Autowired
    TemplateService templateService;

    @PostMapping("/demoDemplate")
    @ResponseBody
    @ApiOperation(value = "获取初始化的模板", notes = "获取初始化模板内容")
    public String demoDemplate() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>code-online</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <p>在此处编辑模板，数据占位符请使用如下格式${paramName}</p>\n" +
                "</body>\n" +
                "</html>";
    }


    @PostMapping("addTemplate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "html", value = "模板内容", required = true),
            @ApiImplicitParam(name = "name", value = "模板名", required = true),
            @ApiImplicitParam(name = "desc", value = "说明（备注）", required = false)
    })
    @ApiOperation(value = "添加模板", notes = "添加模板，模板为完整的HTML")
    public HSResult addTemplate(String html, String name, String desc) {
        return templateService.addTemplate(html, name, desc);
    }

    @PostMapping("editTemplate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模板内容id", required = true),
            @ApiImplicitParam(name = "html", value = "模板内容", required = true),
            @ApiImplicitParam(name = "name", value = "模板名", required = true),
            @ApiImplicitParam(name = "desc", value = "说明（备注）", required = false)
    })
    @ApiOperation(value = "修改模板", notes = "修改模板，模板为完整的HTML")
    public HSResult editTemplate(String id, String html, String name, String desc) {
        return templateService.editTemplate(id, html, name, desc);
    }

    @PostMapping("deleteTemplate")
    @ApiImplicitParam(name = "id", value = "模板id", required = true)
    @ApiOperation(value = "删除模板", notes = "删除模板，必须传入模板ID，删除后不可恢复")
    public HSResult deleteTemplate(String id) {
        return templateService.deleteTemplate(id);
    }


    @PostMapping("queryAllTemplate")
    @ApiOperation(value = "查询当前所有的模板", notes = "查询当前所有模板")
    public HSResult queryAllTemplate() {
        return templateService.queryAllTemplate();
    }

    @PostMapping("getDemplateById")
    @ApiImplicitParam(name = "id", value = "模板id", required = true)
    @ApiOperation(value = "查询当前的模板", notes = "查询当前模板")
    public HSResult getDemplateById(String id) {
        return templateService.getDemplateById(id);
    }

}
