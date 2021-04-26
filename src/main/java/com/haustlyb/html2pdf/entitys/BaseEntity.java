package com.haustlyb.html2pdf.entitys;

import cn.hutool.core.util.IdUtil;
import com.haustlyb.html2pdf.event.ModelEventType;
import com.haustlyb.html2pdf.event.NativeEventListener;
import com.haustlyb.html2pdf.util.HSUtil;
import lombok.Data;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.Date;
import java.util.function.Consumer;

/**
 * @author haust_lyb
 * email : 1570194845@qq.com
 * @date 2020/9/18 上午11:21
 */
@Data
@MappedSuperclass
@EntityListeners(NativeEventListener.class)
public abstract class BaseEntity<Entity extends BaseEntity> {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id = String.valueOf(IdUtil.getSnowflake(1,1).nextId());

    @Version
    @Column(name = "OPTLOCK")
    private Long version;

    //审计日志
    @Column(nullable = false)
    private Date auditDate = new Date();

    //是否为内置数据
    @Column(nullable = false)
    private Boolean buildIn = false;

    //是否假删
    @Column(nullable = false)
    private Boolean isDeleted = false;


    private static void modelEvent(ModelEventType eventType, BaseEntity entity){
//        ModelEvent modelEvent = HSUtil.getBean(ModelEvent.class);
//        if(modelEvent != null){
//            modelEvent.onEvent(eventType,entity);
//        }
    }

    public static <T extends BaseEntity> T _create(Class<T> tClass, Consumer<T> init){
        T entity = null;
        try {
            entity = tClass.newInstance();
            init.accept(entity);
            HSUtil.persist(entity);
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        modelEvent(ModelEventType.ENTITY_CREATE,entity);
        return entity;
    }

    public Entity update(Consumer<Entity> updater){
        updater.accept((Entity)this);
        HSUtil.update(this);
//        modelEvent(ModelEventType.ENTITY_UPDATE,this);
        return (Entity)this;
    }

    public void delete(){
//        modelEvent(ModelEventType.ENTITY_DELETE,this);
        HSUtil.delete(this);
    }

    public Query query(String hql){
        return HSUtil.query(hql);
    }
}

