package com.xlg.android.protocol;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomUserInfoNew implements Parcelable {


    /**
     * alias : 曹佳琪
     * carname :
     * cphoto : 20170425024422_252.jpg
     * decocolor : 0
     * expend : 282581286
     * gender : 1
     * level1 : 64
     * micindex : -1
     * nb : 3728469080
     * reserve : 0
     * userid : 103921876
     * userstate : 0
     */

    private String alias;
    private String carname;
    private String cphoto;
    private int decocolor;
    private String expend;
    private int gender;
    private int level1;
    private int micindex;
    private String nb;
    private int reserve;
    private int userid;
    private int userstate;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getCphoto() {
        return cphoto;
    }

    public void setCphoto(String cphoto) {
        this.cphoto = cphoto;
    }

    public int getDecocolor() {
        return decocolor;
    }

    public void setDecocolor(int decocolor) {
        this.decocolor = decocolor;
    }

    public String getExpend() {
        return expend;
    }

    public void setExpend(String expend) {
        this.expend = expend;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getMicindex() {
        return micindex;
    }

    public void setMicindex(int micindex) {
        this.micindex = micindex;
    }

    public String getNb() {
        return nb;
    }

    public void setNb(String nb) {
        this.nb = nb;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserstate() {
        return userstate;
    }

    public void setUserstate(int userstate) {
        this.userstate = userstate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.alias);
        dest.writeString(this.carname);
        dest.writeString(this.cphoto);
        dest.writeInt(this.decocolor);
        dest.writeString(this.expend);
        dest.writeInt(this.gender);
        dest.writeInt(this.level1);
        dest.writeInt(this.micindex);
        dest.writeString(this.nb);
        dest.writeInt(this.reserve);
        dest.writeInt(this.userid);
        dest.writeInt(this.userstate);
    }

    public RoomUserInfoNew() {
    }

    protected RoomUserInfoNew(Parcel in) {
        this.alias = in.readString();
        this.carname = in.readString();
        this.cphoto = in.readString();
        this.decocolor = in.readInt();
        this.expend = in.readString();
        this.gender = in.readInt();
        this.level1 = in.readInt();
        this.micindex = in.readInt();
        this.nb = in.readString();
        this.reserve = in.readInt();
        this.userid = in.readInt();
        this.userstate = in.readInt();
    }

    public static final Parcelable.Creator<RoomUserInfoNew> CREATOR = new Parcelable.Creator<RoomUserInfoNew>() {
        @Override
        public RoomUserInfoNew createFromParcel(Parcel source) {
            return new RoomUserInfoNew(source);
        }

        @Override
        public RoomUserInfoNew[] newArray(int size) {
            return new RoomUserInfoNew[size];
        }
    };
}
