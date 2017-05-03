package com.fubang.live.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky on 2017/5/3.
 */
public class SearchListEntity implements Parcelable {

    /**
     * status : success
     * roomlist : [{"roomid":"103921876","roomname":"103921876","roompic":"","roomrs":"300","roompwd":"","rscount":"0","gateway":"121.40.140.100:14651;121.40.140.100:14651;121.40.140.100:14651","nuserid":"103921876","cphoto":"20170425024422_252.jpg","bphoto":"20170425024422_335.jpg","calias":"曹佳琪","cidiograph":"是回家简陋哈哈哈","guanzhunum":"0","state":"2","ngender":"1"}]
     */

    private String status;
    private List<RoomlistBean> roomlist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RoomlistBean> getRoomlist() {
        return roomlist;
    }

    public void setRoomlist(List<RoomlistBean> roomlist) {
        this.roomlist = roomlist;
    }

    public static class RoomlistBean implements Parcelable {
        /**
         * roomid : 103921876
         * roomname : 103921876
         * roompic :
         * roomrs : 300
         * roompwd :
         * rscount : 0
         * gateway : 121.40.140.100:14651;121.40.140.100:14651;121.40.140.100:14651
         * nuserid : 103921876
         * cphoto : 20170425024422_252.jpg
         * bphoto : 20170425024422_335.jpg
         * calias : 曹佳琪
         * cidiograph : 是回家简陋哈哈哈
         * guanzhunum : 0
         * state : 2
         * ngender : 1
         */

        private String roomid;
        private String roomname;
        private String roompic;
        private String roomrs;
        private String roompwd;
        private String rscount;
        private String gateway;
        private String nuserid;
        private String cphoto;
        private String bphoto;
        private String calias;
        private String cidiograph;
        private String guanzhunum;
        private String state;
        private String ngender;

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

        public String getNuserid() {
            return nuserid;
        }

        public void setNuserid(String nuserid) {
            this.nuserid = nuserid;
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
            dest.writeString(this.nuserid);
            dest.writeString(this.cphoto);
            dest.writeString(this.bphoto);
            dest.writeString(this.calias);
            dest.writeString(this.cidiograph);
            dest.writeString(this.guanzhunum);
            dest.writeString(this.state);
            dest.writeString(this.ngender);
        }

        public RoomlistBean() {
        }

        protected RoomlistBean(Parcel in) {
            this.roomid = in.readString();
            this.roomname = in.readString();
            this.roompic = in.readString();
            this.roomrs = in.readString();
            this.roompwd = in.readString();
            this.rscount = in.readString();
            this.gateway = in.readString();
            this.nuserid = in.readString();
            this.cphoto = in.readString();
            this.bphoto = in.readString();
            this.calias = in.readString();
            this.cidiograph = in.readString();
            this.guanzhunum = in.readString();
            this.state = in.readString();
            this.ngender = in.readString();
        }

        public static final Creator<RoomlistBean> CREATOR = new Creator<RoomlistBean>() {
            @Override
            public RoomlistBean createFromParcel(Parcel source) {
                return new RoomlistBean(source);
            }

            @Override
            public RoomlistBean[] newArray(int size) {
                return new RoomlistBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeList(this.roomlist);
    }

    public SearchListEntity() {
    }

    protected SearchListEntity(Parcel in) {
        this.status = in.readString();
        this.roomlist = new ArrayList<RoomlistBean>();
        in.readList(this.roomlist, RoomlistBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<SearchListEntity> CREATOR = new Parcelable.Creator<SearchListEntity>() {
        @Override
        public SearchListEntity createFromParcel(Parcel source) {
            return new SearchListEntity(source);
        }

        @Override
        public SearchListEntity[] newArray(int size) {
            return new SearchListEntity[size];
        }
    };
}
