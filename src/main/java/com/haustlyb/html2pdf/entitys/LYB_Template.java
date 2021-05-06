package com.haustlyb.html2pdf.entitys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Data
public class LYB_Template extends BaseEntity<LYB_Template>{

    @Lob
    @Column(columnDefinition="text")
    private String html;//模板内容
    private String name;//模板名
    @Lob
    @Column(columnDefinition="text")
    private String desc;//模板备注
    @Lob
    @Column(columnDefinition="text")
    private String testData;
}
