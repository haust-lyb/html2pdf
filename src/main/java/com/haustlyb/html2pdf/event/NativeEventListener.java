package com.haustlyb.html2pdf.event;



import com.haustlyb.html2pdf.entitys.BaseEntity;

import javax.annotation.PreDestroy;
import javax.persistence.*;

public class NativeEventListener {

    @PrePersist
    public void PrePersist(BaseEntity entity){
    }

    @PreRemove
    public void PreRemove(BaseEntity entity){
    }

    @PreUpdate
    public void PreUpdate(BaseEntity entity){
    }

    @PreDestroy
    public void PreDestroy(BaseEntity entity){
    }


    @PostPersist
    public void PostPersist(BaseEntity entity){
    }

    @PostLoad
    public void PostLoad(BaseEntity entity){
    }

    @PostUpdate
    public void PostUpdate(BaseEntity entity){
    }

    @PostRemove
    public void PostRemove(BaseEntity entity){
    }
}
