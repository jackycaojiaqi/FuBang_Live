package com.fubang.live.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 判断是否第一次启动app
 * Created by dell on 2016/3/4.
 */
public class StartUtil {
    public static final String APP_START_KEY = "isFirst";
    public static final String APP_START_FIRST = "manhua_first";
    public static final String APP_THEME_LIGHT = "isLight";
    public static final String APP_LIGHT_KEY = "light_model";
    public static final String DOWN_COUNT = "down_count";
    public static final String DOWN_COUNT_KEY = "down_count_key";
    public static final String VERSION = "version";
    public static final String VERSION_KEY = "version_key";
    public static final String USER_INFO = "user_info";
    public static final String USER_ICON = "user_icon";
    public static final String USER_NAME = "user_name";
    public static final String USER_ID = "user_id";
    public static final String USER_PWD = "user_pwd";
    public static final String USER_LEVEL = "user_level";
    public static final String USER_DEPOSIT = "user_deposit";
    public static final String USER_KBI = "user_kbi";
    public static final String USER_SCORE = "user_score";
    public static final String USER_CIDIOGRAPH = "user_cidiograph";
    public static final String USER_INFO_OTHER = "user_info_other";
    public static final String QQ_ID = "qq_id";
    public static final String QQ = "qq";
    public static final String IP_PORT = "ip_port";
    public static final String CVALUE = "cvalue";
    public static final String DEVICEID = "deviceid";
    public static final String DEVICE = "device";
    public static final String CITY = "city";
    public static final String LAT = "lat";//纬度
    public static final String LNG = "lng";//经度
    public static final String USERPIC = "userpic";
    public static final String LIVE_TYPE = "live_type";

    public static void putLiveType(Context context, String type) {
        SharedPreferences preferences = context.getSharedPreferences(LIVE_TYPE, Context.MODE_PRIVATE);
        preferences.edit().putString(CVALUE, type).commit();
    }

    public static String getLiveType(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(LIVE_TYPE, Context.MODE_PRIVATE);
        String type = preferences.getString(CVALUE, "0");
        return type;
    }


    public static void putLNG(Context context, String city) {
        SharedPreferences preferences = context.getSharedPreferences(LNG, Context.MODE_PRIVATE);
        preferences.edit().putString(CVALUE, city).commit();
    }

    public static String getLNG(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(LNG, Context.MODE_PRIVATE);
        String city = preferences.getString(CVALUE, "116.40712");
        return city;
    }

    public static void putLAT(Context context, String city) {
        SharedPreferences preferences = context.getSharedPreferences(LAT, Context.MODE_PRIVATE);
        preferences.edit().putString(CVALUE, city).commit();
    }

    public static String getLAT(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(LAT, Context.MODE_PRIVATE);
        String city = preferences.getString(CVALUE, "40.017349");
        return city;
    }

    public static void putCity(Context context, String city) {
        SharedPreferences preferences = context.getSharedPreferences(CITY, Context.MODE_PRIVATE);
        preferences.edit().putString(CVALUE, city).commit();
    }

    public static String getCity(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(CITY, Context.MODE_PRIVATE);
        String city = preferences.getString(CVALUE, "绍兴");
        return city;
    }

    public static void putUserPic(Context context, String city) {
        SharedPreferences preferences = context.getSharedPreferences(USERPIC, Context.MODE_PRIVATE);
        preferences.edit().putString(CVALUE, city).commit();
    }

    public static String getUserPic(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USERPIC, Context.MODE_PRIVATE);
        String city = preferences.getString(CVALUE, "null");
        return city;
    }

    public static void putIpPort(Context context, String ip) {
        SharedPreferences preferences = context.getSharedPreferences(IP_PORT, Context.MODE_PRIVATE);
        preferences.edit().putString(CVALUE, ip).commit();
    }

    public static String getIpPort(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(IP_PORT, Context.MODE_PRIVATE);
        String ip = preferences.getString(CVALUE, "121.41.112.232:9418");
        return ip;
    }

    public static void deleteIp(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(IP_PORT, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

    public static boolean isFirst(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APP_START_FIRST, Context.MODE_PRIVATE);
        boolean isFirst = preferences.getBoolean(APP_START_KEY, true);
        if (isFirst) {
            preferences.edit().putBoolean(APP_START_KEY, false).commit();
        }
        return isFirst;
    }

    //public static boolean isFirst(Context context){
//        SharedPreferences preferences = context.getSharedPreferences("first",Context.MODE_PRIVATE);
//        boolean isFirst = preferences.getBoolean("isFirst",true);
//        if (isFirst){
//            preferences.edit().putBoolean("isFirst",false);
//        }
//        return isFirst;
//}
    public static void putQQid(Context context, String qqid) {
        SharedPreferences preferences = context.getSharedPreferences(QQ, Context.MODE_PRIVATE);
        preferences.edit().putString(QQ_ID, qqid).commit();
    }

    public static String getQQid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(QQ, Context.MODE_PRIVATE);
        String QQid = preferences.getString(QQ_ID, "");
        return QQid;
    }

    public static boolean isLight(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(APP_THEME_LIGHT, Context.MODE_PRIVATE);
        boolean isLight = preferences.getBoolean(APP_LIGHT_KEY, true);
        return isLight;
    }

    public static void editLight(Context context, boolean isNight) {
        SharedPreferences preferences = context.getSharedPreferences(APP_THEME_LIGHT, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(APP_LIGHT_KEY, isNight).commit();
    }

    public static int getCount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DOWN_COUNT, Context.MODE_PRIVATE);
        int count = preferences.getInt(DOWN_COUNT_KEY, 0);
        return count;
    }

    public static void putVersion(Context context, String version) {
        SharedPreferences preferences = context.getSharedPreferences(VERSION, Context.MODE_PRIVATE);
        preferences.edit().putString(VERSION_KEY, version).commit();
    }

    public static String getVersion(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(VERSION, Context.MODE_PRIVATE);
        String version = preferences.getString(VERSION_KEY, "1");
        return version;
    }

    public static void putCount(Context context, int count) {
        SharedPreferences preferences = context.getSharedPreferences(DOWN_COUNT, Context.MODE_PRIVATE);
        preferences.edit().putInt(DOWN_COUNT_KEY, count).commit();
    }

    public static void editInfo(Context context, String userName, String userId, String userIcon, String userPwd) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        preferences.edit().putString(USER_NAME, userName).putString(USER_ICON, userIcon).putString(USER_ID, userId).putString(USER_PWD, userPwd).commit();
    }

    public static String getUserIcon(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        String userIcon = preferences.getString(USER_ICON, "");
        return userIcon;
    }

    public static void putUserName(Context context, String content) {
        SharedPreferences preferences = context.getSharedPreferences(DOWN_COUNT, Context.MODE_PRIVATE);
        preferences.edit().putString(USER_NAME, content).commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        String userIcon = preferences.getString(USER_NAME, "");
        return userIcon;
    }

    public static String getUserPwd(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        String userPwd = preferences.getString(USER_PWD, "");
        return userPwd;
    }

    public static String getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        String userIcon = preferences.getString(USER_ID, "");
        return userIcon;
    }

    public static void deleteLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
        SharedPreferences preferences1 = context.getSharedPreferences(USER_INFO_OTHER, Context.MODE_PRIVATE);
        preferences1.edit().clear().commit();
        SharedPreferences preferences2 = context.getSharedPreferences(QQ, Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
    }

    public static void editUserInfo(Context context, String userLevel, String userDeposit, String userKbi, String userScore, String userCidiograph) {
        SharedPreferences perferences = context.getSharedPreferences(USER_INFO_OTHER, Context.MODE_PRIVATE);
        perferences.edit().putString(USER_LEVEL, userLevel).putString(USER_DEPOSIT, userDeposit).putString(USER_KBI, userKbi)
                .putString(USER_SCORE, userScore).putString(USER_CIDIOGRAPH, userCidiograph).commit();
    }

    public static String getUserLevel(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO_OTHER, Context.MODE_PRIVATE);
        String userLevel = preferences.getString(USER_LEVEL, "");
        return userLevel;
    }

    public static String getUserDeposit(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO_OTHER, Context.MODE_PRIVATE);
        String userLevel = preferences.getString(USER_DEPOSIT, "");
        return userLevel;
    }

    public static String getUserKbi(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO_OTHER, Context.MODE_PRIVATE);
        String userLevel = preferences.getString(USER_KBI, "");
        return userLevel;
    }

    public static String getUserScore(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO_OTHER, Context.MODE_PRIVATE);
        String userLevel = preferences.getString(USER_SCORE, "");
        return userLevel;
    }

    public static String getUserCidiograph(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_INFO_OTHER, Context.MODE_PRIVATE);
        String userLevel = preferences.getString(USER_CIDIOGRAPH, "");
        return userLevel;
    }

    public static void editDeviceId(Context context, String deviceId) {
        SharedPreferences preferences = context.getSharedPreferences(DEVICE, Context.MODE_PRIVATE);
        preferences.edit().putString(DEVICEID, deviceId).commit();
    }

    public static String getDeviceId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(DEVICE, Context.MODE_PRIVATE);
        String deviceid = preferences.getString(DEVICEID, "android");
        return deviceid;
    }
}
