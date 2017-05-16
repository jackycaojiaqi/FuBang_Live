package com.fubang.live.entities;

import java.util.List;

/**
 * Created by dell on 2016/4/7.
 */
public class RoomDistanceEntity extends BaseEntity{


    /**
     * status : success
     * roomlist : [{"roomid":"103921876","roomname":"103921876","roompic":"","roomrs":"300","roompwd":"","rscount":"0","gateway":"121.40.140.100:14651;121.40.140.100:14651;121.40.140.100:14651","ctheme":"","nuserid":"103921876","cphoto":"20170425024422_252.jpg","bphoto":"20170425024422_335.jpg","calias":"曹佳琪","cidiograph":"是回家简陋哈哈哈","guanzhunum":"-1","state":"2","ngender":"1","dis":"0"},{"roomid":"175495572","roomname":"175495572","roompic":"","roomrs":"300","roompwd":"","rscount":"2","gateway":"121.40.140.100:10744;121.40.140.100:10744;121.40.140.100:10744","ctheme":"","nuserid":"175495572","cphoto":"20170425020503_729.jpg","bphoto":"20170425020503_297.jpg","calias":"小丸子","cidiograph":"人人好 大家好","guanzhunum":"1","state":"2","ngender":"1","dis":"20.7214"},{"roomid":"99888","roomname":"99888","roompic":"","roomrs":"300","roompwd":"","rscount":"16","gateway":"121.40.140.100:10358;121.40.140.100:10358;121.40.140.100:10358","ctheme":"","nuserid":"99888","cphoto":"20170511083813_183.jpg","bphoto":"20170511083813_620.jpg","calias":"sddss","cidiograph":"ppppppp","guanzhunum":"0","state":"2","ngender":"1","dis":"11829"}]
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

    public static class RoomlistBean {
        /**
         * roomid : 103921876
         * roomname : 103921876
         * roompic :
         * roomrs : 300
         * roompwd :
         * rscount : 0
         * gateway : 121.40.140.100:14651;121.40.140.100:14651;121.40.140.100:14651
         * ctheme :
         * nuserid : 103921876
         * cphoto : 20170425024422_252.jpg
         * bphoto : 20170425024422_335.jpg
         * calias : 曹佳琪
         * cidiograph : 是回家简陋哈哈哈
         * guanzhunum : -1
         * state : 2
         * ngender : 1
         * dis : 0
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
        private String dis;

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

        public String getDis() {
            return dis;
        }

        public void setDis(String dis) {
            this.dis = dis;
        }
    }
}
