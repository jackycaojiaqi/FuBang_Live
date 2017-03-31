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
 * 创建时间：2016-09-30 09:40
 * 修改人：zhuyunjian
 * 修改时间：2016-09-30 09:40
 * 修改备注：
 */
public class RegisterRequest {
    @StructOrder(0)
    private short	deviceid;			//设备id
    @StructOrder(1)
    private byte	visitor;				//0表示默认,2-qq,3-微信,4-微博,5-手机号码
    @StructOrder(2)
    private byte []	cuserpwd = new byte[32];		//密码
    @StructOrder(3)
    private byte[] cldcode = new byte[40];			//qqId

    public byte getVisitor() {
        return visitor;
    }

    public short getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(short deviceid) {
        this.deviceid = deviceid;
    }

    public void setVisitor(byte visitor) {
        this.visitor = visitor;
    }
    public String getCuserpwd() {
        byte [] data = new byte[cuserpwd.length];
        System.arraycopy(cuserpwd, 0, data, 0, cuserpwd.length);
        SimpSecret.SimpDecrypt(data);
        return Tools.ByteArray2StringGBK(data);
    }

    public void setCuserpwd(String pwd) {
        Tools.String2ByteArrayGBK(cuserpwd, pwd);
        SimpSecret.SimpEncrypt(cuserpwd);
    }


    public String getCldcode() {
        return Tools.ByteArray2StringGBK(cldcode);
    }

    public void setCldcode(String cldcode) {
        Tools.String2ByteArrayGBK(this.cldcode,cldcode);
    }
}

