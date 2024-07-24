package com.haustlyb.html2pdf.controllers;

import cn.hutool.core.util.StrUtil;
import com.haustlyb.html2pdf.component.HSResult;
import com.haustlyb.html2pdf.services.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(value = "模板api", tags = "模板api")
public class ApiController {

    @Autowired
    TemplateService templateService;

    @PostMapping("/addTemplate")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "html", value = "模板内容", required = true),
            @ApiImplicitParam(name = "name", value = "模板名", required = true),
            @ApiImplicitParam(name = "desc", value = "说明（备注）", required = false),
            @ApiImplicitParam(name = "testData", value = "模板测试数据", required = false)
    })
    @ApiOperation(value = "添加模板", notes = "添加模板，模板为完整的HTML")
    public HSResult addTemplate(String html, String name, String desc,String testData) {
        if (StrUtil.isBlank(html) || StrUtil.isBlank(name)){
            return HSResult.sayFail("添加失败，模板的内容和名字不能为空");
        }
        return templateService.addTemplate(html, name, desc,testData);
    }

    @PostMapping("/deleteTemplate")
    @ApiImplicitParam(name = "id", value = "模板id", required = true)
    @ApiOperation(value = "删除模板", notes = "删除模板，必须传入模板ID，删除后不可恢复")
    public HSResult deleteTemplate(String id) {
        return templateService.deleteTemplate(id);
    }


    @PostMapping("/queryAllTemplate")
    @ApiOperation(value = "查询当前所有的模板", notes = "查询当前所有模板")
    public HSResult queryAllTemplate() {
        return templateService.queryAllTemplate();
    }

    @PostMapping("/getDemplateById")
    @ApiImplicitParam(name = "id", value = "模板id", required = true)
    @ApiOperation(value = "查询当前的模板", notes = "查询当前模板")
    public HSResult getDemplateById(String id) {
        return templateService.getDemplateById(id);
    }

}
