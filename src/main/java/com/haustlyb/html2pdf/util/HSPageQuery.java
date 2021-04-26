package com.haustlyb.html2pdf.util;


import com.haustlyb.html2pdf.component.HSPageSearch;
import com.haustlyb.html2pdf.entitys.BaseEntity;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kady on 17/7/27.
 *  1 分页模式化查询
 *  2 动态查询模式化
 *  3 session上下文直接测试，不需要启动tomcat容器进行测试
 */
public abstract class HSPageQuery<Entity extends BaseEntity> {

    private HSPageSearch pageSearch = null;
    private Map<String, Object> paramMap = new LinkedHashMap<>();

    public static <T extends HSPageQuery> T criteria(Class<T> queryClass){
        return criteria(queryClass,null);
    }

    public static <T extends HSPageQuery> T criteria(Class<T> queryClass, HSPageSearch pageSearch){

        HSPageQuery self = null;
        try {
            self = queryClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        self.pageSearch = pageSearch;

        return (T)self;
    }

    private Query getQuery(String hql){
        return HSUtil.query(hql);
    }

    public Integer count(){
        String hql = countHQL();
        Query query = getQuery(hql);
        fillParameters(query);
        return ((Long)query.uniqueResult()).intValue();
    }

    public List<Entity> list(){
        String hql = listHQL();
        hql = buildOrder(hql);
        Query query = getQuery(hql);
        if(pageSearch == null){
            //不进行分页
        }
        else{
            query.setFirstResult(pageSearch.getStart());
            query.setMaxResults(pageSearch.getLimit());
        }
        fillParameters(query);
        return query.list();
    }

    protected String buildOrder(String hql){
        if(hql.contains("order by") || hql.contains("ORDER BY")) {
            return hql;
        }
        else {
            return hql + " order by e.auditDate desc ";
        }
    }

    //为query设置参数
    protected void param(String name, Object object){
        paramMap.put(name,object);
    }

    protected abstract String listHQL();
    protected abstract String countHQL();

    //不强制直接覆盖，建议使用param函数直接进行设置
    protected void fillParameters(Query query){
        if(paramMap.size() > 0){
            for(Map.Entry<String, Object> objectEntry : paramMap.entrySet()){
                String paramName = objectEntry.getKey();
                Object paramValue = objectEntry.getValue();

                if(paramValue == null){
                    continue;
                }
                if(paramValue.getClass().isArray()){
                    query.setParameterList(paramName, (Object[]) paramValue);
                }
                if(paramValue instanceof Collection){
                    query.setParameterList(paramName, (Collection) paramValue);
                }
                else{
                    query.setParameter(paramName,paramValue);
                }
            }
        }
    }
}

