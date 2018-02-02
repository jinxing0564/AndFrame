package com.vince.andframe.demo.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tianweixin on 2018-1-30.
 */

public class Book implements Parcelable {
    private String mName;
    private long mId;

    public Book(String name, long id) {
        mName = name;
        mId = id;
    }

    protected Book(Parcel in) {
        mName = in.readString();
        mId = in.readLong();
    }

    public String toString() {
        return mId + "    " + mName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeLong(mId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
