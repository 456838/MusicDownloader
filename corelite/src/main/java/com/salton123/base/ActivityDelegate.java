package com.salton123.base;

import android.app.Activity;

/**
 * User: newSalton@outlook.com
 * Date: 2019/3/19 10:12
 * ModifyTime: 10:12
 * Description:
 */
public class ActivityDelegate extends LifeDelegate {

    private Activity mHost;

    public ActivityDelegate(IComponentLife componentLife) {
        super(componentLife);
        if (componentLife instanceof Activity) {
            mHost = (Activity) componentLife;
        } else {
            throw new RuntimeException("instance must Activity");
        }
    }

    @Override
    Activity activity() {
        return mHost;
    }

}