package com.fubang.live.modle;


import com.fubang.live.modle.impl.RoomListModelImpl;
import com.fubang.live.modle.impl.RtmpUrlModelImpl;

/**
 * model工厂类
 * Created by dell on 2016/4/5.
 */
public class ModelFactory {
    private static volatile ModelFactory instance = null;

    private ModelFactory(){
    }

    public static ModelFactory getInstance() {
        if (instance == null) {
            synchronized (ModelFactory.class) {
                if (instance == null) {
                    instance = new ModelFactory();
                }
            }
        }
        return instance;
    }

    public RoomListModelImpl getRoomListModelImpl(){
        return RoomListModelImpl.getInstance();
    }
    public RtmpUrlModelImpl getRtmpUrlImpl(){
        return RtmpUrlModelImpl.getInstance();
    }
}
