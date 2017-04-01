package com.sample.room;

public class RoomMain {
    private MyRoom room;
    private MicNotify micNotify;

    public RoomMain(MicNotify micNotify) {
        this.micNotify = micNotify;
    }

    public MyRoom getRoom() {
        return room;
    }

    public void setRoom(MyRoom room) {
        this.room = room;
    }

    public void Start(int roomId, int userId, String userPwd, String ip, int port, String pwd) {
        room = new MyRoom(micNotify);

//		room.getChannel().setRoomID(10000);
//		room.getChannel().setUserID(18);
//		room.getChannel().setUserPwd("723105");
//		room.getChannel().Connect("121.43.63.101", 10927);
        room.getChannel().setRoomID(roomId);
        room.getChannel().setUserID(userId);
        room.getChannel().setUserPwd(userPwd);
        room.getChannel().setRoomPwd(pwd);
        room.getChannel().Connect(ip, port);

        try {
            for (int i = 0; true; i++) {
                // 暂停10秒
                Thread.sleep(1000 * 10);
                if (room.isOK()) {
                    // 发起心跳
                    room.getChannel().SendKeepAliveReq();
//					发送聊天消息
//					room.getChannel().sendChatMsg(0, (byte)0, (byte)0, "这是测试");
                } else {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
