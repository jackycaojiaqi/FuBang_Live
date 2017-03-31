package com.xlg.android.protocol;

import com.xlg.android.utils.SimpSecret;
import com.xlg.android.utils.Tools;

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
 * <p>
 * 项目名称：newfubangzhibo
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2016-09-30 09:44
 * 修改人：zhuyunjian
 * 修改时间：2016-09-30 09:44
 * 修改备注：
 * 20*12.5 = 250
 * 15*300 4500= 500
 * 60*1.2 720 = 100
 * 1200 * 4 4800 = 600
 * 250 * 8 2000 = 200
 * 80 * 2 160 = 20   12000J 1650R
 *
 * 5*300 1500 = 160
 * 10*12 120 = 20
 * 3*12.5 = 40  14000J   1850R
 * 2000R  2000R   18000J  20000J
 */
public class RegisterResponse {
    @StructOrder(0)
    private int	userid;						//用户ID
    @StructOrder(1)
    private byte[]			cuserpwd = new byte[32];//用户密码
    @StructOrder(2)
    private short	errid;						//错误ID
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getCuserpwd() {
        byte [] data = new byte[cuserpwd.length];
        System.arraycopy(cuserpwd, 0, data, 0, cuserpwd.length);
        SimpSecret.SimpDecrypt(data);
        return Tools.ByteArray2StringGBK(data);
    }

    public void setCuserpwd(String pwd) {
        Tools.String2ByteArrayGBK(cuserpwd,pwd);
        SimpSecret.SimpEncrypt(cuserpwd);
    }
    public short getErrid() {
        return errid;
    }

    public void setErrid(short errid) {
        this.errid = errid;
    }
}
