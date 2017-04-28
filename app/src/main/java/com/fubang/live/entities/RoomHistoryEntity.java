package com.fubang.live.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jacky on 2017/4/28.
 */
public class RoomHistoryEntity implements Parcelable {

    /**
     * roomid : 99888
     * roomname : 99888
     * roompic :
     * roomrs : 300
     * roompwd :
     * rscount : 16
     * gateway : 121.40.140.100:10358;121.40.140.100:10358;121.40.140.100:10358;115.231.24.226:10358
     * cphoto : null
     * bphoto : null
     * calias : sddss
     * cidiograph : ppppppp
     * guanzhunum : 0
     * state : 0
     * ngender : 1
     * micstate : 0
     */

    private String roomid;
    private String roomname;
    private String roompic;
    private String roomrs;
    private String roompwd;
    private String rscount;
    private String gateway;
    private String cphoto;
    private String bphoto;
    private String calias;
    private String cidiograph;
    private String guanzhunum;
    private String state;
    private String ngender;
    private String micstate;

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getRoompic() {
        return roompic;
    }

    public void setRoompic(String roompic) {
        this.roompic = roompic;
    }

    public String getRoomrs() {
        return roomrs;
    }

    public void setRoomrs(String roomrs) {
        this.roomrs = roomrs;
    }

    public String getRoompwd() {
        return roompwd;
    }

    public void setRoompwd(String roompwd) {
        this.roompwd = roompwd;
    }

    public String getRscount() {
        return rscount;
    }

    public void setRscount(String rscount) {
        this.rscount = rscount;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getCphoto() {
        return cphoto;
    }

    public void setCphoto(String cphoto) {
        this.cphoto = cphoto;
    }

    public String getBphoto() {
        return bphoto;
    }

    public void setBphoto(String bphoto) {
        this.bphoto = bphoto;
    }

    public String getCalias() {
        return calias;
    }

    public void setCalias(String calias) {
        this.calias = calias;
    }

    public String getCidiograph() {
        return cidiograph;
    }

    public void setCidiograph(String cidiograph) {
        this.cidiograph = cidiograph;
    }

    public String getGuanzhunum() {
        return guanzhunum;
    }

    public void setGuanzhunum(String guanzhunum) {
        this.guanzhunum = guanzhunum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNgender() {
        return ngender;
    }

    public void setNgender(String ngender) {
        this.ngender = ngender;
    }

    public String getMicstate() {
        return micstate;
    }

    public void setMicstate(String micstate) {
        this.micstate = micstate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.roomid);
        dest.writeString(this.roomname);
        dest.writeString(this.roompic);
        dest.writeString(this.roomrs);
        dest.writeString(this.roompwd);
        dest.writeString(this.rscount);
        dest.writeString(this.gateway);
        dest.writeString(this.cphoto);
        dest.writeString(this.bphoto);
        dest.writeString(this.calias);
        dest.writeString(this.cidiograph);
        dest.writeString(this.guanzhunum);
        dest.writeString(this.state);
        dest.writeString(this.ngender);
        dest.writeString(this.micstate);
    }

    public RoomHistoryEntity() {
    }

    protected RoomHistoryEntity(Parcel in) {
        this.roomid = in.readString();
        this.roomname = in.readString();
        this.roompic = in.readString();
        this.roomrs = in.readString();
        this.roompwd = in.readString();
        this.rscount = in.readString();
        this.gateway = in.readString();
        this.cphoto = in.readString();
        this.bphoto = in.readString();
        this.calias = in.readString();
        this.cidiograph = in.readString();
        this.guanzhunum = in.readString();
        this.state = in.readString();
        this.ngender = in.readString();
        this.micstate = in.readString();
    }

    public static final Parcelable.Creator<RoomHistoryEntity> CREATOR = new Parcelable.Creator<RoomHistoryEntity>() {
        @Override
        public RoomHistoryEntity createFromParcel(Parcel source) {
            return new RoomHistoryEntity(source);
        }

        @Override
        public RoomHistoryEntity[] newArray(int size) {
            return new RoomHistoryEntity[size];
        }
    };
}
