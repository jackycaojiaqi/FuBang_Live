package com.fubang.live;

import android.app.Application;

import com.fubang.live.util.CrashHandler;
import com.fubang.live.util.LiteOrmDBUtil;
import com.qiniu.pili.droid.streaming.StreamingEnv;

/**
 * Created by jacky on 17/3/23.
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StreamingEnv.init(getApplicationContext());
        CrashHandler.getInstance().init(getApplicationContext());//本地统计日志
        LiteOrmDBUtil.createDb(getApplicationContext(), "live");
    }
}
