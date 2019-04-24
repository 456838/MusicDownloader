package com.salton123.base.feature;

import com.salton123.util.EventBusUtil;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/23 17:24
 * ModifyTime: 17:24
 * Description:
 */
public class EventBusFeature implements IFeature {
    public Object mObject;

    public EventBusFeature(Object obj) {
        this.mObject = obj;
    }

    @Override
    public void onBind() {
        EventBusUtil.register(mObject);
    }

    @Override
    public void onUnBind() {
        EventBusUtil.unregister(mObject);
    }
}
