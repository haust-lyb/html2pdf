package com.haustlyb.html2pdf.component;

import lombok.Data;

/**
 * Created by kadyvivian on 2015/10/3.
 */
@Data
public class HSPageSearch{

    private Integer currentPage;
    private Integer pageSize;

    private Integer offset; //offset
    private Integer limit;  //limit


    public Integer getStart() {
        if(offset != null){
            currentPage = ( offset / limit + (offset % limit == 0 ? 0:1) ) + 1 ;
            pageSize = limit;
        }
        else {
            currentPage = (currentPage == null ? 1 : currentPage);
            pageSize = (pageSize == null ? 20 : pageSize);
        }
        return (currentPage - 1) * pageSize;
    }


    public Integer getLimit() {
        if(offset != null) {
            pageSize = limit;
        }
        else{
            pageSize = (pageSize == null ? 20: pageSize);
        }
        return pageSize;
    }
}
