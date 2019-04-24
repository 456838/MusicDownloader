package com.salton123.utils;

import com.salton123.musicdownloader.R;

/**
 * User: newSalton@outlook.com
 * Date: 2019/4/23 16:53
 * ModifyTime: 16:53
 * Description:
 */
public class MaterialColors {

    public static final int[] colors = new int[]{
            R.color.color_f44336_material,
            R.color.color_e91e63_material,
            R.color.color_9c27b0_material,
            R.color.color_673ab7_material,
            R.color.color_3f51b5_material,
            R.color.color_2196f3_material,
            R.color.color_03a9f4_material,
            R.color.color_00bcd4_material,
            R.color.color_009688_material,
            R.color.color_4caf50_material,
            R.color.color_cddc39_material,
            R.color.color_ffeb3b_material,
            R.color.color_ffc107_material,
            R.color.color_ff9800_material,
            R.color.color_ff5722_material,
            R.color.color_795548_material,
            R.color.color_9e9e9e_material,
            R.color.color_607d8b_material
    };

    public static int random() {
        int index = (int) (Math.random() * colors.length);
        System.out.println("MaterialColors,random:"+index);
        return colors[index];
    }
}
