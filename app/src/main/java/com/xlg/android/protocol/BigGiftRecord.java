package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class BigGiftRecord {
    @StructOrder(0)
    private int vcbid;   //房间id
    @StructOrder(1)
    private int srcid;            //发送者ID
    @StructOrder(2)
    private byte[] srcalias = new byte[32];//发送者昵称
    @StructOrder(3)
    private int toid;        //接收者ID
    @StructOrder(4)
    private byte[] toalias = new byte[32];//接受者昵称
    @StructOrder(5)
    private int giftid;                //礼物ID
    @StructOrder(6)
    private int count;                //礼物数量
    @StructOrder(7)
    private int reserve;                //送出烟花礼物时，保存房间人数



    public int getVcbid() {
        return vcbid;
    }

    public void setVcbid(int vcbid) {
        this.vcbid = vcbid;
    }

    public int getSrcid() {
        return srcid;
    }

    public void setSrcid(int srcid) {
        this.srcid = srcid;
    }

    public String getSrcalias() {
        return Tools.ByteArray2StringGBK(srcalias);
    }

    public void setSrcalias(String srcalias) {
        Tools.String2ByteArrayGBK(this.srcalias, srcalias);
    }

    public int getToid() {
        return toid;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public String getToalias() {
        return Tools.ByteArray2StringGBK(toalias);
    }

    public void setToalias(String toalias) {
        Tools.String2ByteArrayGBK(this.toalias, toalias);
    }

    public int getGiftid() {
        return giftid;
    }

    public void setGiftid(int giftid) {
        this.giftid = giftid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }
}
