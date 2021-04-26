package com.haustlyb.html2pdf.util;


import com.haustlyb.html2pdf.component.HSPageSearch;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by kady on 17/7/27.
 *  1 分页模式化查询
 *  2 动态查询模式化
 *  3 session上下文直接测试，不需要启动tomcat容器进行测试
 */
public abstract class HSSQLPageQuery<T>{

    private HSPageSearch pageSearch = null;

    public static <T extends HSSQLPageQuery> T criteria(Class<T> queryClass){
        return criteria(queryClass,null);
    }
    public static <T extends HSSQLPageQuery> T criteria(Class<T> queryClass, HSPageSearch pageSearch){

        HSSQLPageQuery self = null;
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

    private NativeQuery getQuery(String hql){
        return HSUtil.querySQL(hql);
    }

    public Integer count(){
        String sql = countSQL();
        NativeQuery query = getQuery(sql);
        fillParameters(query);
        return ((BigInteger)query.uniqueResult()).intValue();
    }

    public List<T> list(){

        String sql = listSQL();
        NativeQuery query = getQuery(sql);
        if(pageSearch == null){
            //不进行分页
        }
        else{
            query.setFirstResult(pageSearch.getStart());
            query.setMaxResults(pageSearch.getLimit());
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        fillParameters(query);

        if(resultClass() != null){
            return transfer(query.list());
        }
        else{
            return query.list();
        }
    }

    private List<T> transfer(List<Map> list){
        List rs = new LinkedList();
        Class<T> clazz = resultClass();

        for(Map<String, Object> item :list){
            T object = null;
            try {
                object = clazz.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            Map<String, Object> tempMap = new LinkedHashMap<>();
            for(Map.Entry<String, Object> itemEntry : item.entrySet()){
                tempMap.put(itemEntry.getKey().toUpperCase(),itemEntry.getValue());
            }

            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                ReflectionUtils.setField(field, object, convertValue(field, tempMap));
            }
            rs.add(object);
        }
        return rs;
    }


    private Object convertValue(Field field , Map<String, Object> valueMap){
        String name = field.getName().toUpperCase();
        Object value = valueMap.get(name);
        //
        Class targetType = field.getType();
        Object rs = null;
        switch (targetType.getSimpleName()){
            case "String":{
                if(value.getClass().getSimpleName().equals("String")){
                    rs = value;
                }
                else{
                    throw new RuntimeException("unknown convert [ " + targetType.getName() + " => " + value.getClass().getName() +  "] ");
                }
                break;
            }
            case "Long":{
                if(value.getClass().getSimpleName().equals("BigInteger")){
                    rs = ((BigInteger)value).longValue();
                }
                else{
                    throw new RuntimeException("unknown convert [ " + targetType.getName() + " => " + value.getClass().getName() +  "] ");
                }
                break;
            }
            case "Date":{
                if(value.getClass().getSimpleName().equals("Timestamp")){
                    rs = new Date(((Timestamp)value).getTime());
                }
                else{
                    throw new RuntimeException("unknown convert [ " + targetType.getName() + " => " + value.getClass().getName() +  "] ");
                }
                break;
            }
            default: throw new RuntimeException("unknown convert [ " + targetType.getName() + " => " + value.getClass().getName() +  "] ");
        }
        return rs;
    }

    protected abstract String countSQL();
    protected abstract String listSQL();
    protected abstract Class<T> resultClass();
    protected abstract void fillParameters(SQLQuery query);
}

