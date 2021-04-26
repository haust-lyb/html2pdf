package com.haustlyb.html2pdf.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by kady on 17/4/30.
 */
class BeanHelper {

    //spring
    private static ApplicationContext applicationContext = null;
    public static void setApplicationContext(ApplicationContext context){
        applicationContext = context;
    }
    public static <T> T getBean(Class<T> clazz){
        if(applicationContext == null){
            throw new RuntimeException("cannot inject applicationContext");
        }
        return applicationContext.getBean(clazz);
    }

    public static List<Class> scanPackage(String basePackage, Class annotationClass){
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(annotationClass));
        Set<BeanDefinition> components = provider.findCandidateComponents(basePackage);

        List<Class> rs = new LinkedList<>();
        for(BeanDefinition component : components){
            String className = component.getBeanClassName();
            try {
                Class clazz = Class.forName(className);
                rs.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public static void refresh(){
        //WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
        //((AbstractRefreshableApplicationContext) context).refresh();
    }
}
