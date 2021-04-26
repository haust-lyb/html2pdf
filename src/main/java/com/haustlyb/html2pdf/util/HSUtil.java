package com.haustlyb.html2pdf.util;

import cn.hutool.core.util.StrUtil;
import com.haustlyb.html2pdf.entitys.BaseEntity;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @author haust_lyb
 * email : 1570194845@qq.com
 * @date 2020/9/18 上午11:25
 */
public class HSUtil{


    //获取classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用
    public static String getClassPath(String path) {

        String classPath = HSUtil.class.getClassLoader().getResource("").getPath();
        if(classPath.endsWith("/")){
            classPath = classPath + "/";
        }
        if(StrUtil.isBlank(path)){
            return classPath;
        }
        else{
            if(path.startsWith("/")){
                return classPath + path.substring(1);
            }
            else{
                return classPath + path;
            }
        }
    }



    //过滤字符串中的html标签
    public static String filterHtml(String str){
        //过滤字符串中的html标签
        if(StrUtil.isBlank(str)){
            return "";
        }
        return str.replaceAll("<[.[^<]]*>","");
    }

    //首字母改成大写:传入的参数不发生变化
    public static String firstLetterToUpper(String target){
        if(target == null || target.trim().equals("")){
            return target;
        }
        String rs = new String(target);
        return rs.substring(0,1).toUpperCase() + rs.substring(1);
    }

    public static Object firstLetterToLower(String target) {
        if(target == null || target.trim().equals("")){
            return target;
        }
        String rs = new String(target);
        return rs.substring(0,1).toLowerCase() + rs.substring(1);
    }



    public static HttpServletRequest getRequest(){
        HttpServletRequest curRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return curRequest;
    }

    public static HttpSession getSession(){
        HttpServletRequest request = getRequest();
        return request.getSession();
    }

    public static <T> T getSessionPre(String pre){
        HttpSession httpSession = getSession();
        String sessionId = httpSession.getId();
        return (T)getSession().getAttribute(pre + sessionId);
    }

    public static <T> void setSessionPre(String pre, T target){
        HttpSession httpSession = getSession();
        String sessionId = httpSession.getId();
        getSession().setAttribute(pre + sessionId,target);
    }

    /* -----------------------------------------------------------------------------------------------------------------
     *  Util 对接spring 上下文信息 ：只有完成对接后相关的util中相关bean操作才有效
    ------------------------------------------------------------------------------------------------------------------*/
    public static void setApplicationContext(ApplicationContext context){
        BeanHelper.setApplicationContext(context);
    }


    /* -----------------------------------------------------------------------------------------------------------------
     *  bean 相关操作
    ------------------------------------------------------------------------------------------------------------------*/
    public static <T> T getBean(Class<T> clazz){
        return BeanHelper.getBean(clazz);
    }


    public static Session hibernateSession(){
        return HSUtil.getBean(EntityManager.class).unwrap(Session.class);
    }

    public static Query query(String hqlStr) {
        return hibernateSession().createQuery(hqlStr);
    }

    public static NativeQuery querySQL(String sqlStr) {
        return hibernateSession().createSQLQuery(sqlStr);
    }

    public static <T extends BaseEntity> void persist(T entity) {
        hibernateSession().persist(entity);
    }

    public static <T extends BaseEntity> void update(BaseEntity entity) {
        hibernateSession().update(entity);
    }

    public static <T extends BaseEntity> void delete(BaseEntity entity) {
        hibernateSession().delete(entity);
    }

    /* -----------------------------------------------------------------------------------------------------------------
     *  杂项
    ------------------------------------------------------------------------------------------------------------------*/

}
