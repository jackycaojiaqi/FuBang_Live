package com.sample.login;

import android.content.Context;


import com.fubang.live.util.StartUtil;
import com.xlg.android.LoginChannel;
import com.xlg.android.LoginHandler;
import com.xlg.android.protocol.LogonError;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.RegisterResponse;

import org.simple.eventbus.EventBus;


/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p/>
 * 项目名称：fubangzhibo
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-05-31 13:40
 * 修改人：dell
 * 修改时间：2016-05-31 13:40
 * 修改备注：
 */
public class LoginMain implements LoginHandler {
    private LoginChannel channel = new LoginChannel(this);
    private int id;
    private String pwd;
    private int flag;
    private int visitor;
    private String cldcode;
    private Context context;
    private int loginFlag;
    private LoginMain login;

    public LoginMain(int id, String pwd, String cldcode, int flag, int visitor, Context context) {
        this.id = id;
        this.pwd = pwd;
        this.cldcode = cldcode;
        this.flag = flag;
        this.visitor = visitor;
        this.context = context;
    }


    public void start(int userId, String userPwd, int userVisitor, String userCldcode, int userFlag, Context mContext) {
        id = userId;
        pwd = userPwd;
        visitor = userVisitor;
        cldcode = userCldcode;
        flag = userFlag;
        context = mContext;
        login = new LoginMain(userId, userPwd, cldcode, userFlag, userVisitor, context);
        loginFlag = 1;
        // 121.43.155.221:15518
        // 121.43.63.101:18517
        login.channel.Connect("121.40.140.100", 33111);
//        login.channel.Connect("42.121.57.170", 12211 );

//        loginFlag = 1;
//        login.channel.Connect("121.43.148.99", 18517);

        try {
            Thread.sleep(1000 * 50);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectSuccessed() {
        loginFlag++;
        // TODO Auto-generated method stub
        System.out.println("连接服务器成功");
//        android.util.Log.d("123",id+pwd);
        channel.SendHello();

        channel.SendLogonRequest(id, visitor, 1, pwd, StartUtil.getDeviceId(context), "", cldcode, flag);
//        Log.d("123",StartUtil.getDeviceId(context));
//        channel.SendLogonRequest(0, 2, 1, "", "android-test", "" ,cldcode,2);
    }

    @Override
    public void onConnectFailed() {
        if (loginFlag == 1) {
            login.channel.Connect("121.40.140.100", 33112);
            loginFlag++;
        } else {
            // TODO Auto-generated method stub
            System.out.println("连接服务器失败");
        }
    }

    @Override
    public void onLogonResponse(LogonResponse res) {
        // TODO Auto-generated method stub
        System.out.println("登录回应");
//        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
        System.out.println("Calias: " + res.getCalias());
        System.out.println("Cidiograph: " + res.getCidiograph());
        System.out.println("Cidiograph: " + res.getCidiograph());
        System.out.println("Decocolor: " + res.getDecocolor());
        System.out.println("Nb: " + res.getNb());
        System.out.println("Ndeposit: " + res.getNdeposit());
        System.out.println("Nk: " + res.getNk());
        System.out.println("Nlevel: " + res.getNlevel());
        System.out.println("Nverison: " + res.getNverison());
        System.out.println("Userid: " + res.getUserid());
        System.out.println("Gender: " + res.getGender());
        System.out.println("Headpic: " + res.getHeadpic());
        System.out.println("Online_stat: " + res.getOnline_stat());
        System.out.println("Reserve: " + res.getReserve());
        System.out.println("cvalue: " + res.getCvalue());
//        if (StartUtil.isFirst(context)) {
        EventBus.getDefault().post(res, "login_success");
//        }else {
        EventBus.getDefault().post(res, "splash_success");
//        }
        channel.Close();
    }

    @Override
    public void onLogonError(LogonError err) {
        // TODO Auto-generated method stub
        System.out.println("登录失败");
        EventBus.getDefault().post("0", "login_fail");
    }

    @Override
    public void onDisconnected() {
        // TODO Auto-generated method stub
        System.out.println("与服务器断开");
    }

    @Override
    public void onRegisterResponse(RegisterResponse res) {
        EventBus.getDefault().post(res, "registerSuccess");
        System.out.println("注册成功-----" + res.getUserid());
        System.out.println("错误id-----" + res.getErrid());
    }
}
