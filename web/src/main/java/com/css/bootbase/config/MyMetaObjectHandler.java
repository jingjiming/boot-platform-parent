package com.css.bootbase.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private static final Logger log = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            //this.strictInsertFill(metaObject, "creator", String.class, null);
            this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        } catch (Exception e) {
            log.warn("insert fill fail!");
            e.printStackTrace();
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());

            boolean lastTime = metaObject.hasSetter("lastTime");
            boolean lastEditor = metaObject.hasSetter("lastEditor");
            //获取实体中，是否手动设置了值，如果设置了值，则不进行自动填充
            Object valLastTime = getFieldValByName("lastTime", metaObject);
            Object valLastEditor = getFieldValByName("lastEditor", metaObject);

            if (valLastTime !=null && lastTime) {
                this.strictUpdateFill(metaObject, "lastTime", Date.class, new Date());
            }

            if (valLastEditor != null && lastEditor) {
                //TODO 当前登陆人
                this.strictUpdateFill(metaObject, "lastEditor", Date.class, new Date());
            }
        } catch (Exception e) {
            log.warn("update fill fail!");
            e.printStackTrace();
        }
    }
}