package com.fubang.live;

import java.io.File;

/**
 * 常量工具类
 * Created by dell on 2016/4/5.
 */
public class AppConstant {
    //    public static final String BASE_URL = "http://115.231.24.84:96";//网红
    public static final String BASE_RTMP_URL = "http://120.26.10.198:88/";//获取rtmp地址
    public static final String BASE_URL = "http://120.26.127.210:9419";//富邦直播
    public static final String BASE_IMG_URL = "http://120.26.127.210:9419/user_pic/";//图片服务器前缀地址
    public static final String BASE_DOWNLOAD_MUSIC = "http://120.26.127.210:333/mp3_ge/";//音乐下载链接前缀
    public static final String BASE_DOWNLOAD_LRC = "http://120.26.127.210:333/mp3_lrc/";//音乐下载链接前缀
    public static final String USER_ID = "userid";
    public static final String COUNT = "count";
    public static final String USERID = "nuserid";
    public static final String CONTENT = "content";
    public static final String PAGE = "page";
    public static final String GROUP = "group";
    public static final String ROOMID = "roomid";
    public static final String ROOMIP = "roomip";
    public static final String PORT = "port";
    public static final String ROOMPWD = "roompwd";
    public static final String NVCBID = "nvcbid";


    public static final String MSG_GET_ROOM_INFO = "/index.php/app/roomlist?";//获取房间信息       （首页热门）
    public static final String MSG_UP_AUTH_INFO = "/index.php/app/info_up";//上传认证信息
    public static final String MSG_GET_USER_INFO = "/index.php/app/get_info?";//获取用户信息       （个人中心获取用户信息）
    public static final String MSG_MODIFY_USER_INFO = "/index.php/app/upload_info";//更新用户信息 包括直播类型（才艺、好声音、帅哥、美女） （个人中心更新用户信息）
    public static final String MSG_MODIFY_USER_PIC = "/index.php/app/upload_pic";//更新用户图片信息    （个人中心上传用户头像）
    public static final String MSG_GET_SEE_HISTORY = "/index.php/app/get_history?";//获取历史查看信息
    public static final String MSG_DELETE_ONE_HISTORY = "/index.php/app/delete_history?";//清除单个历史
    public static final String MSG_DELETE_ALL_HISTORY = "/index.php/app/clear_history?";//清除所有历史
    public static final String MSG_DELETE_ONE_FAV = "/index.php/app/delete_fav?";//清除单个关注
    public static final String MSG_GET_FAV_LIST = "/index.php/app/room_fav?";//获取关注注列表
    public static final String MSG_GET_DISTANCE_LIST = "/index.php/app/dis_list?";//获取根据距离排序的列表


    public static final String MSG_GET_ROOM_BY_KEY = "/index.php/app/ser_list?";//根据关键字模糊查询   （首页搜索页面、首页才艺、好声音、帅哥、美女列表）

    public static final String MSG_UPLOAD_LATLON = "/index.php/app/upload_place?";//上传位置信息       （首页、主播上麦前、刷新距离列表）
    public static final String MSG_GET_MP3 = "/index.php/app/get_mp3?";//获取Mp3列表
    public static final String MSG_GET_AD_INFO = "/index.php/app/ad_info?";//获取广告图和链接
    public static final String MSG_GET_FANS_LIST = "/index.php/app/fans_list?";//获取粉丝列表
}
