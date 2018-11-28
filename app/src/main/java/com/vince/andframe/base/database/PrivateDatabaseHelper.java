package com.vince.andframe.base.database;


import android.content.Context;
import android.text.TextUtils;

import com.vince.andframe.app.AFApp;
import com.vince.andframe.base.database.dbgen.DaoMaster;
import com.vince.andframe.base.database.dbgen.DaoSession;
import com.vince.andframe.base.tools.MD5Util;
import com.vince.andframe.base.tools.YDLog;

import net.sqlcipher.database.SQLiteDatabase;

import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

/**
 * Created by by tianweixin on 2018/9/29.
 */
public class PrivateDatabaseHelper {
    private static PrivateDatabaseHelper instanse;

    private Database privateDB;
    private DaoMaster daoMaster;
    private String userId;


    public static PrivateDatabaseHelper getInstanse() {
        if (instanse == null) {
            instanse = new PrivateDatabaseHelper();
        }
        return instanse;
    }

    public synchronized boolean openDatabase(String userId, String password) {
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(password)) {
            throw new RuntimeException("userId or password is empty");
        }
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(AFApp.getAppContext(), getDBName(userId));
        privateDB = helper.getEncryptedWritableDb(password);
        if (privateDB != null) {
            this.userId = userId;
        }
        return isDbOpened();
    }

    public synchronized AbstractDaoSession getDaoSession() {
        if (!isDbOpened()) {
            throw new RuntimeException("db not opened!");
        }
        if (daoMaster == null) {
            daoMaster = new DaoMaster(privateDB);
        }

        return daoMaster.newSession();
    }

    private void clean() {
        privateDB = null;
        daoMaster = null;
        userId = null;
    }

    public synchronized void rekey(String newKey) {
        ((SQLiteDatabase) privateDB.getRawDatabase()).changePassword(newKey);
    }

    public synchronized void closeDatabase() {
        if (isDbOpened()) {
            privateDB.close();
            YDLog.d("private db closed!");
        }
        clean();
    }

    public synchronized boolean isDbOpened() {
        return (privateDB != null && ((SQLiteDatabase) privateDB.getRawDatabase()).isOpen());
    }

    public static String getDBPath(String userId) {
        return AFApp.getAppContext().getDatabasePath(getDBName(userId)).getPath();
    }

    private static String getDBName(String userId) {
        try {
            return "privateDB_" + MD5Util.to32Str(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "privateDB_" + userId;
    }
}
