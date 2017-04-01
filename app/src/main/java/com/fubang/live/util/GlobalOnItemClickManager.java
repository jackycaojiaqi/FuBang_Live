package com.fubang.live.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

/**
 * Created by dss886 on 15/9/22.
 */
public class GlobalOnItemClickManager {

    private static GlobalOnItemClickManager instance;
    private EditText mEditText;

    public static GlobalOnItemClickManager getInstance() {
        if (instance == null) {
            instance = new GlobalOnItemClickManager();
        }
        return instance;
    }

    public void attachToEditText(EditText editText) {
        mEditText = editText;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener(final int emojiType) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StringBuilder sb = new StringBuilder();
                switch (emojiType) {
                    case 0:
                        if (position<9) {
                            sb.append("/mr70").append(String.valueOf(position + 1));
                        }else {
                            sb.append("/mr7").append(String.valueOf(position + 1));
                        }
                        break;
                    case 1:
                        if (position<9) {
                            sb.append("/mr70").append(String.valueOf(position + 1));
                        }else {
                            sb.append("/mr7").append(String.valueOf(position + 1));
                        }
                        break;
                    case 2:
                        if (position<9) {
                            sb.append("/mr70").append(String.valueOf(position + 1));
                        }else {
                            sb.append("/mr7").append(String.valueOf(position + 1));
                        }
                        break;
                    case 3:
                        if (position<9) {
                            sb.append("/mr70").append(String.valueOf(position + 1));
                        }else {
                            sb.append("/mr7").append(String.valueOf(position + 1));
                        }
                        break;
                }
                // 得到随机表情图片的Bitmap
                Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(),FaceUtil.getFaces().get(position).getFaceId());
                // 得到SpannableString对象,主要用于拆分字符串
                SpannableString spannableString = new SpannableString(sb);
                // 得到ImageSpan对象
                ImageSpan imageSpan = new ImageSpan(parent.getContext(), bitmap);
                // 调用spannableString的setSpan()方法
                spannableString.setSpan(imageSpan, 0, 6,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                // 给EditText追加spannableString
                mEditText.append(spannableString);
//                mEditText.append(sb.toString());
            }
        };
    }
}
