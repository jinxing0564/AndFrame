package com.vince.andframe.base.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.vince.andframe.app.AFApp;

import java.io.File;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by dfqin on 2018/4/26.
 */


public class DeviceUtil {

    private static Context mContext = AFApp.getInstance();
    private static final String SP_NAME = "device_util";
    private static final String SP_KEY_UID = "deviceId";

    private static String deviceId; //设备唯一标识



    private static String macAddr;

    /**
     * 获取设备的型号
     */
    public static String getDeviceModel() {
        String model = Build.MODEL;

        if (model == null) {
            return "";
        } else {
            return model;
        }
    }

    /**
     * 获取设备 SDK版本名
     */
    public static String getSDKVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备 SDK版本号
     */
    public static int getSDKVersionInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断是否存在SD卡
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

//    /**
//     * 获取设备的IMEI号
//     */
//    public static String getIMEI(Context context) {
//        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        String imei = "";
//        try {
//            imei = teleMgr.getDeviceId();
//        } catch (Exception e) {
//        }
//        return imei;
//    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    public static String getAppVersion() {
        String version = "";
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info;
            info = manager.getPackageInfo(mContext.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 判断当前设备的数据服务是否有效
     *
     * @return true - 有效，false - 无效
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) {
            return false;
        }

        NetworkInfo nwInfo = connectMgr.getActiveNetworkInfo();
        if (nwInfo == null || !nwInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatuBar() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = mContext.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    private static DisplayMetrics sMetrics;
    private static DisplayMetrics getDisplayMetrics() {
        if (sMetrics == null) {
            sMetrics = Resources.getSystem().getDisplayMetrics();
        }
        return sMetrics;
    }


    /**
     * 获取屏幕宽度
     * @return
     */
    @Deprecated
    public static int getDisplayWidthPixels() {
        return getScreenWidth();
    }

    public static int getScreenWidth() {
        return getDisplayMetrics() != null ? getDisplayMetrics().widthPixels : 0;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    @Deprecated
    public static int getDisplayheightPixels() {
        return getScreenHeight();
    }

    public static int getScreenHeight() {
        return getDisplayMetrics() != null ? getDisplayMetrics().heightPixels : 0;
    }


    /**
     * 判断是否是WiFi
     *
     * @return
     */
    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }


    /**
     * 设备唯一识别码(androidid > imei > mac > random)
     * 内存中缓存，可以频繁调用
     * @return
     */
    public static String deviceID() {

        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }

        deviceId = getCacheDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = geneDeviceId();
            cacheDeviceId(deviceId);
        }
        return deviceId;
    }

    //获取缓存的deviceId
    private static String getCacheDeviceId() {
        String deviceId = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getString(SP_KEY_UID, "");
        return deviceId;
    }

    public static String getAndroidId() {
        String androidId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    //生成 deviceId
    private static String geneDeviceId() {

        String deviceId = null;
        //android id
        String androidId = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId)) {
            deviceId = androidId;
        }

        if (TextUtils.isEmpty(deviceId)) {
            deviceId = getIMEI(); //TODO 6.0以上不申请授权，拿不到，申请授权只能异步回调
        }

        if (TextUtils.isEmpty(deviceId)) {
            deviceId = getMacAddr();
        }

        if (TextUtils.isEmpty(deviceId)) {
            String time = String.valueOf(System.currentTimeMillis());
            String random = String.valueOf((int)(Math.random() * 10000));
            deviceId = time + random;
        }

        try {
            deviceId = MD5Util.to32Str(deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deviceId;
    }

    //持久化 device id
    private static void cacheDeviceId(String deviceId) {
        mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit().putString(SP_KEY_UID, deviceId).commit();

    }

    private static String imei = null;
    public static String getIMEI() {
        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        try {
            //IMEI
            TelephonyManager tel = (TelephonyManager) mContext.getSystemService(
                    Context.TELEPHONY_SERVICE);
            if (tel != null) {
                imei = tel.getDeviceId();
                if (!TextUtils.isEmpty(imei)) {
                    return imei;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMacAddr() {

        if (!TextUtils.isEmpty(macAddr)) {
            return macAddr;
        }

        if (Build.VERSION.SDK_INT >= 23) {
            try {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    macAddr = res1.toString();
                    return macAddr;
                }
            } catch (Exception ex) {
            }
        } else {
            WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) {
                    macAddr = info.getMacAddress();
                }
            }
        }
        return macAddr;
    }


    /**
     * @Title: ${enclosing_method}
     * @Description: ${安装apk}
     * @author liu zheng yang
     */
    public static void installApk(String apkPath) {
        File apkFile = new File(apkPath);
        Uri uri = Uri.fromFile(apkFile);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(install);
    }


    private static String wifiIP = null;
    public static String getWIFIIPAddress() {

        if (!TextUtils.isEmpty(wifiIP)) {
            return wifiIP;
        }

        WifiManager wifiManager = (WifiManager)mContext.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        if (wifiInfo == null) {
            return "";
        }
        int ipAddress = wifiInfo.getIpAddress();

        //返回整型地址转换成“*.*.*.*”地址
        wifiIP = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return wifiIP;
    }

    private static String ip3G = null;
    public static String get3GIpAddress() {

        if (!TextUtils.isEmpty(ip3G)) {
            return ip3G;
        }

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        ip3G = inetAddress.getHostAddress().toString();
                        return ip3G;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getIp() {

        String ip = null;
        if (isWifi()) {
            ip = getWIFIIPAddress();
        }
        if (TextUtils.isEmpty(ip)) {
            ip = get3GIpAddress();
        }
        if (TextUtils.isEmpty(ip)) {
            ip = "0.0.0.0";
        }
        return ip;
    }

    /****
     * 判断app 是前台运行还是切换到后台
     * @return
     */
    public static boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = mContext.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
