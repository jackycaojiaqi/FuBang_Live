package com.xlg.android.protocol;

public class UserPayResponse {
    @StructOrder(0)
    private int vcbid;                //房间ID
    @StructOrder(1)
    private int userid;                //用户ID
    @StructOrder(2)
    private int toid;                //对手ID(用于用户转账)
    @StructOrder(3)
    private long balance;            //用户金币
    @StructOrder(4)
    private long giftbalance;        //解释为用户经验
    @StructOrder(5)
    private int type;                //奖励提示类型
    @StructOrder(6)
    private long money;                //变化金额
    @StructOrder(7)
    private int reserve1;            //保留1
    @StructOrder(8)
    private int reserve2;            //保留2
    @StructOrder(9)
    private long expend;                //用户消费

    public int getToid() {
        return toid;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public long getExpend() {
        return expend;
    }

    public void setExpend(long expend) {
        this.expend = expend;
    }

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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getGiftbalance() {
        return giftbalance;
    }

    public void setGiftbalance(long giftbalance) {
        this.giftbalance = giftbalance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getReserve1() {
        return reserve1;
    }

    public void setReserve1(int reserve1) {
        this.reserve1 = reserve1;
    }

    public int getReserve2() {
        return reserve2;
    }

    public void setReserve2(int reserve2) {
        this.reserve2 = reserve2;
    }


}
