package com.vince.andframe.base.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by by tianweixin on 2018/9/29.
 */
@Entity
public class PrivateStorage {
    @Unique
    private String key;
    private String value;
    @Generated(hash = 2135334927)
    public PrivateStorage(String key, String value) {
        this.key = key;
        this.value = value;
    }
    @Generated(hash = 1695842054)
    public PrivateStorage() {
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}

