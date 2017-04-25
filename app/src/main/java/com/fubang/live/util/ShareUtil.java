package com.fubang.live.util;

import android.content.Context;

import com.socks.library.KLog;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 第三方分享工具类
 * Created by dell on 2016/4/11.
 */
public class ShareUtil {

    private static volatile ShareUtil instance = null;

    private ShareUtil() {
    }

    public static ShareUtil getInstance() {
        if (instance == null) {
            synchronized (ShareUtil.class) {
                if (instance == null) {
                    instance = new ShareUtil();
                }
            }
        }
        return instance;
    }


//    public void showShare(Context context, String platform) {
//        final OnekeyShare oks = new OnekeyShare();
//        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
//        if (platform != null) {
//            oks.setPlatform(platform);
//        }
//
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("富邦直播");
//        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("富邦直播");
//        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("富邦直播");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("ShareSDK");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
//        //启动分享
//        oks.show(context);
//    }

    public void showShareNew(final Context context, Platform platform) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("富邦直播");
        sp.setTitleUrl("http://61.153.104.118:9418/download/fbzb.apk"); // 标题的超链接
        sp.setText("富邦直播，你值得拥有！");
        sp.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492651940&di=66d41148d98384b03f7297bfce9442bd&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.bjxifu.cn%2Ftupian%2Fbd9977330.jpg.jpg");
        sp.setSite("富邦直播");
        sp.setSiteUrl("富邦直播");
        sp.setUrl("http://61.153.104.118:9418/download/fbzb.apk");
        sp.setShareType(Platform.SHARE_WEBPAGE);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        platform.setPlatformActionListener(new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                KLog.e("分享失败" + arg2.getMessage() + arg2.getLocalizedMessage());
                ToastUtil.show(context, "分享失败");
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
            }

            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                ToastUtil.show(context, "分享成功");
                //分享成功的回调
            }

            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
                ToastUtil.show(context, "分享取消");
            }
        });
        // 执行图文分享
        platform.SSOSetting(false);
        if (!platform.getName().contains("Wechat")) {
            platform.authorize();
        }
        platform.share(sp);
    }
}
