package com.haustlyb.html2pdf.component;



import lombok.Data;

import java.util.*;
import java.util.function.Function;

/**
 * Created by kady on 19/6/16.
 */
@Data
public class HSPageResult<T> {

    private Integer pageSize;//每页记录数
    private Integer currPage;//当前页码
    private Integer totalPages;//总页数
    private List<T> rows;  //jqgrid-table
    private Integer total; //等效于totalRecords

    private List<Map<String,Object>> footer = new LinkedList<>();
    private Map<String, Object> extra = new LinkedHashMap<>();


    public static <ENTITY,T> HSPageResult build(HSPageSearch pageSearch, Integer count, Collection<ENTITY> collection, Function<ENTITY,T> map){

        if(map == null){
            map = p -> (T)p;
        }

        List<T> dtoList = new LinkedList<>();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()){
            ENTITY entity = (ENTITY)iterator.next();
            T dto = map.apply(entity);
            dtoList.add(dto);
        }
        return new HSPageResult(pageSearch,count,dtoList);
    }


    /**
     * 总页数是根据传入的基本参数自动设置的
     */
    private void setTotalPages() {
        int temp=total % pageSize;

        if(temp==0)
            totalPages=total/pageSize;
        else
            totalPages=total/pageSize+1;
    }


    public HSPageResult(HSPageSearch hsPageSearch, Integer totalRecords, List<T> rows) {
        this.pageSize = hsPageSearch.getLimit();
        this.total = totalRecords;
        this.currPage = hsPageSearch.getCurrentPage();
        this.rows = rows;
        setTotalPages();
    }


}
