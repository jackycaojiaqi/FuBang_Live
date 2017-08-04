package com.fubang.live.entities;

/**
 * Created by jacky on 17/4/1.
 */
public class RtmpUrlEntity extends BaseEntity {

    /**
     * state : 200
     * publishUrl : rtmp://pili-publish.fbyxsp.com/wanghong/wh_10088_58888?e=1491040368&token=rgNGguFNzZb47-3LXCxtm4H5iMjbMG-5dhhOR512:JeY71fUZZyr6E9iZteATFqYmtlE=
     * RTMPPlayURL : rtmp://pili-live-rtmp.fbyxsp.com/wanghong/wh_10088_58888
     */

    private int state;
    private String publishUrl;
    private String RTMPPlayURL;
    private String M3U8PlayURL;

    public String getM3U8PlayURL() {
        return M3U8PlayURL;
    }

    public void setM3U8PlayURL(String m3U8PlayURL) {
        M3U8PlayURL = m3U8PlayURL;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public String getRTMPPlayURL() {
        return RTMPPlayURL;
    }

    public void setRTMPPlayURL(String RTMPPlayURL) {
        this.RTMPPlayURL = RTMPPlayURL;
    }
}
