package com.haustlyb.html2pdf.event;


import com.haustlyb.html2pdf.entitys.BaseEntity;

public interface ModelEvent {
    //WARN:使用该事件的地方禁止操作数据库防止发生循环事件
    public void onEvent(ModelEventType eventType, BaseEntity entity);
}
