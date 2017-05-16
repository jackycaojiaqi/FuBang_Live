package com.fubang.live.entities;

import java.util.List;

/**
 * Created by jacky on 2017/5/16.
 */
public class RoomFollowEntity {


    /**
     * status : success
     * datalist : [{"roomid":"175495572","roomname":"175495572","roompic":"","roomrs":"300","roompwd":"","rscount":"1","gateway":"121.40.140.100:10744;121.40.140.100:10744;121.40.140.100:10744","ctheme":"","nuserid":"175495572","cphoto":"20170425020503_729.jpg","bphoto":"20170425020503_297.jpg","calias":"小丸子","cidiograph":"人人好 大家好","guanzhunum":"1","state":"2","ngender":"1"}]
     */

    private String status;
    private List<DatalistBean> datalist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DatalistBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DatalistBean> datalist) {
        this.datalist = datalist;
    }

    public static class DatalistBean {
        /**
         * roomid : 175495572
         * roomname : 175495572
         * roompic :
         * roomrs : 300
         * roompwd :
         * rscount : 1
         * gateway : 121.40.140.100:10744;121.40.140.100:10744;121.40.140.100:10744
         * ctheme :
         * nuserid : 175495572
         * cphoto : 20170425020503_729.jpg
         * bphoto : 20170425020503_297.jpg
         * calias : 小丸子
         * cidiograph : 人人好 大家好
         * guanzhunum : 1
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
        private String ctheme;
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

        public String getCtheme() {
            return ctheme;
        }

        public void setCtheme(String ctheme) {
            this.ctheme = ctheme;
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
    }
}
