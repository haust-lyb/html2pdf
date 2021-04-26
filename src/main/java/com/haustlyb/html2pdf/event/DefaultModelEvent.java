package com.haustlyb.html2pdf.event;

import com.haustlyb.html2pdf.entitys.BaseEntity;
import com.haustlyb.html2pdf.log.LogFrame;
import org.springframework.stereotype.Component;

@Component
public class DefaultModelEvent implements ModelEvent {
    @Override
    public void onEvent(ModelEventType eventType, BaseEntity entity) {
        LogFrame.info("[" + entity.getClass().getSimpleName() + "]" + eventType.name() + " ID:" + entity.getId());
    }
}
