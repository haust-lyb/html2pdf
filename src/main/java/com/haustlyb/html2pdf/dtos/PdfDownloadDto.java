package com.haustlyb.html2pdf.dtos;


import lombok.Data;

@Data
public class PdfDownloadDto {
    private String templateId;
    private String jsonData;
}
