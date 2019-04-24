package com.salton123.utils;

import android.app.Fragment;
import android.app.FragmentManager;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/24 12:49
 * ModifyTime: 12:49
 * Description:
 */
public class FragmentUtil {
    public static void add(FragmentManager manager, Fragment fragment, int containerId, String tag) {
        manager.beginTransaction().add(containerId, fragment, tag).commitAllowingStateLoss();
    }

    public static void show(FragmentManager manager, String tag) {
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            manager.beginTransaction().show(fragment).commitAllowingStateLoss();
        }
    }

    public static void hide(FragmentManager manager, String tag) {
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            manager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }
}
