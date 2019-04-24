package com.salton123.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;

import java.util.logging.Level;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/23 17:25
 * ModifyTime: 17:25
 * Description:
 */
public class EventBusUtil {
    private static final String TAG = "EventBusUtil";
    private static EventBus mEventBus = EventBus
            .builder()
            .logger(new Logger() {
                @Override
                public void log(Level level, String msg) {
                    Log.i(TAG, "level:" + level + ",msg:" + msg);
                }

                @Override
                public void log(Level level, String msg, Throwable th) {
                    Log.i(TAG, "level:" + level + ",msg:" + msg + ",th:" + th);
                }
            })
            .build();

    public static void register(Object context) {
        if (!mEventBus.isRegistered(context)) {
            mEventBus.register(context);
        }
    }

    public static void unregister(Object context) {
        if (mEventBus.isRegistered(context)) {
            mEventBus.unregister(context);
        }
    }

    public static void post(Object obj) {
        mEventBus.post(obj);
    }
}
