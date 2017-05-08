package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class RoomBaseInfo {
    @StructOrder(0)
    private int vcbid;  //房间ID
    @StructOrder(1)
    private int userid;                //操作者ID
    @StructOrder(2)
    private byte[] theme = new byte[64];//房间描述

    public int getVcbid() {
        return vcbid;
    }

    public void setVcbid(int vcbid) {
        this.vcbid = vcbid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setTheme(byte[] theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return Tools.ByteArray2StringGBK(theme);
    }

    public void setTheme(String theme) {
        Tools.String2ByteArrayGBK(this.theme, theme);
    }
}
