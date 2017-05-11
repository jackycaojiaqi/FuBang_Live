package com.xlg.android.protocol;

import android.os.Parcel;
import android.os.Parcelable;

import com.xlg.android.utils.ByteBuffer;
import com.xlg.android.utils.Tools;

public class RoomInfoList {


    @StructOrder(0)
    private ByteBuffer content = new ByteBuffer();            //聊天内容


    public String getContent() {
        return content.toStringGBK();
    }

    public void setContent(String content) {
        this.content.clear();
        short textlen = 0;

        // 添加新值
        this.content.addStringGBK(content);
        textlen = (short) this.content.size();
    }

}
