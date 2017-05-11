package com.fubang.live.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky on 2017/5/11.
 */
public class MusicListEntity implements Parcelable {


    /**
     * status : success
     * total : 1
     * web_mp3 : http://120.26.127.210:333/mp3_ge/
     * web_lrc : http://120.26.127.210:333/mp3_lrc/
     * list : [{"nid":"8","title":"演员","name":"薛之谦","all_time":"00:40:00","lrc":"yanyuan-xuezhiqian.lrc","mp3_path":"yanyuan-xuezhiqian.mp3"}]
     */

    private String status;
    private String total;
    private String web_mp3;
    private String web_lrc;
    private List<ListBean> list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWeb_mp3() {
        return web_mp3;
    }

    public void setWeb_mp3(String web_mp3) {
        this.web_mp3 = web_mp3;
    }

    public String getWeb_lrc() {
        return web_lrc;
    }

    public void setWeb_lrc(String web_lrc) {
        this.web_lrc = web_lrc;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * nid : 8
         * title : 演员
         * name : 薛之谦
         * all_time : 00:40:00
         * lrc : yanyuan-xuezhiqian.lrc
         * mp3_path : yanyuan-xuezhiqian.mp3
         */

        private String nid;
        private String title;
        private String name;
        private String all_time;
        private String lrc;
        private String mp3_path;

        public String getNid() {
            return nid;
        }

        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAll_time() {
            return all_time;
        }

        public void setAll_time(String all_time) {
            this.all_time = all_time;
        }

        public String getLrc() {
            return lrc;
        }

        public void setLrc(String lrc) {
            this.lrc = lrc;
        }

        public String getMp3_path() {
            return mp3_path;
        }

        public void setMp3_path(String mp3_path) {
            this.mp3_path = mp3_path;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.nid);
            dest.writeString(this.title);
            dest.writeString(this.name);
            dest.writeString(this.all_time);
            dest.writeString(this.lrc);
            dest.writeString(this.mp3_path);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.nid = in.readString();
            this.title = in.readString();
            this.name = in.readString();
            this.all_time = in.readString();
            this.lrc = in.readString();
            this.mp3_path = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
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
        dest.writeString(this.total);
        dest.writeString(this.web_mp3);
        dest.writeString(this.web_lrc);
        dest.writeList(this.list);
    }

    public MusicListEntity() {
    }

    protected MusicListEntity(Parcel in) {
        this.status = in.readString();
        this.total = in.readString();
        this.web_mp3 = in.readString();
        this.web_lrc = in.readString();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MusicListEntity> CREATOR = new Parcelable.Creator<MusicListEntity>() {
        @Override
        public MusicListEntity createFromParcel(Parcel source) {
            return new MusicListEntity(source);
        }

        @Override
        public MusicListEntity[] newArray(int size) {
            return new MusicListEntity[size];
        }
    };
}
