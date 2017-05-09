package com.fubang.live.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jacky on 2017/4/24.
 */
public class UserInfoEntity implements Parcelable {


    /**
     * status : success
     * info : {"cphoto":"20170421044201_430.jpg","bphoto":null,"calias":"744445","cidiograph":"曹佳琪你好","guanzhunum":"0","state":"1","nk":"1200255280500"}
     */

    private String status;
    private InfoBean info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean implements Parcelable {
        /**
         * cphoto : 20170421044201_430.jpg
         * bphoto : null
         * calias : 744445
         * cidiograph : 曹佳琪你好
         * guanzhunum : 0
         * state : 1
         * nk : 1200255280500
         */

        private String cphoto;
        private String bphoto;
        private String calias;
        private String cidiograph;
        private String guanzhunum;
        private String state;
        private String nk;
        private String ngender = "1";
        private String micstate;
        private String location;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getGender() {
            return ngender;
        }

        public void setGender(String gender) {
            this.ngender = gender;
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

        public String getNk() {
            return nk;
        }

        public void setNk(String nk) {
            this.nk = nk;
        }

        public InfoBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cphoto);
            dest.writeString(this.bphoto);
            dest.writeString(this.calias);
            dest.writeString(this.cidiograph);
            dest.writeString(this.guanzhunum);
            dest.writeString(this.state);
            dest.writeString(this.nk);
            dest.writeString(this.ngender);
            dest.writeString(this.micstate);
            dest.writeString(this.location);
        }

        protected InfoBean(Parcel in) {
            this.cphoto = in.readString();
            this.bphoto = in.readString();
            this.calias = in.readString();
            this.cidiograph = in.readString();
            this.guanzhunum = in.readString();
            this.state = in.readString();
            this.nk = in.readString();
            this.ngender = in.readString();
            this.micstate = in.readString();
            this.location = in.readString();
        }

        public static final Creator<InfoBean> CREATOR = new Creator<InfoBean>() {
            @Override
            public InfoBean createFromParcel(Parcel source) {
                return new InfoBean(source);
            }

            @Override
            public InfoBean[] newArray(int size) {
                return new InfoBean[size];
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
        dest.writeParcelable(this.info, flags);
    }

    public UserInfoEntity() {
    }

    protected UserInfoEntity(Parcel in) {
        this.status = in.readString();
        this.info = in.readParcelable(InfoBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserInfoEntity> CREATOR = new Parcelable.Creator<UserInfoEntity>() {
        @Override
        public UserInfoEntity createFromParcel(Parcel source) {
            return new UserInfoEntity(source);
        }

        @Override
        public UserInfoEntity[] newArray(int size) {
            return new UserInfoEntity[size];
        }
    };
}
