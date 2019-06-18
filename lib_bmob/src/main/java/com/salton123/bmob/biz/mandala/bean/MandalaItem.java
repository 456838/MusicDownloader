package com.salton123.bmob.biz.mandala.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/3 11:29
 * ModifyTime: 11:29
 * Description:
 */
public class MandalaItem extends BmobObject implements Parcelable {
    public MandalaItem(String name, String localFilePath) {
        this.name = name;
        this.localFilePath = localFilePath;
    }

    public String url;
    public String localFilePath;
    public String name;

    protected MandalaItem(Parcel in) {
        url = in.readString();
        localFilePath = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(localFilePath);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MandalaItem> CREATOR = new Creator<MandalaItem>() {
        @Override
        public MandalaItem createFromParcel(Parcel in) {
            return new MandalaItem(in);
        }

        @Override
        public MandalaItem[] newArray(int size) {
            return new MandalaItem[size];
        }
    };
}
