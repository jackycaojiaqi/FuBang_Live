package com.fubang.live.entities;

import java.util.List;

/**
 * Created by dell on 2016/4/7.
 */
public class RoomEntity  extends BaseEntity{

    /**
     * status : success
     * roomlist : []
     */

    private String status;
    private List<RoomListEntity> roomlist;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RoomListEntity> getRoomlist() {
        return roomlist;
    }

    public void setRoomlist(List<RoomListEntity> roomlist) {
        this.roomlist = roomlist;
    }
}
