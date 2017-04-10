package com.fubang.live;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fubang.live.util.CrashHandler;
import com.fubang.live.util.LiteOrmDBUtil;
import com.qiniu.pili.droid.streaming.StreamingEnv;
import com.umeng.analytics.MobclickAgent;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by jacky on 17/3/23.
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        ShareSDK.initSDK(this);
        StreamingEnv.init(getApplicationContext());
        CrashHandler.getInstance().init(getApplicationContext());//本地统计日志
        LiteOrmDBUtil.createDb(getApplicationContext(), "live");
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
    }
}
