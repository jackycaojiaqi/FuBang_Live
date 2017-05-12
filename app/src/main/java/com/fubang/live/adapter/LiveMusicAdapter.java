package com.fubang.live.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.live.R;
import com.fubang.live.entities.MusicListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by jacky on 17/3/10.
 */
public class LiveMusicAdapter extends BaseQuickAdapter<MusicListEntity.MusicBean, BaseViewHolder> {
    private List<MusicListEntity.MusicBean> list;
    private List<String> list_music = new ArrayList<>();
    private List<String> list_lrc = new ArrayList<>();

    public LiveMusicAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    public void setLocalFileInfo(List<String> list_music, List<String> list_lrc) {
        this.list_music = list_music;
        this.list_lrc = list_lrc;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicListEntity.MusicBean item) {
        helper.setText(R.id.tv_music_name, item.getTitle())
                .setText(R.id.tv_music_singer, item.getName())
                .setText(R.id.tv_music_time, item.getAll_time())
                .addOnClickListener(R.id.tv_music_pick)
                .addOnClickListener(R.id.tv_music_download);

        if (item.getState() == 2) {//表示本地有该文件
            helper.setVisible(R.id.tv_music_download, false);
            helper.setVisible(R.id.pb_music, false);
            helper.setVisible(R.id.tv_music_pick, true);
        } else if (item.getState() == 0) {//没有下载
            helper.setVisible(R.id.tv_music_pick, false);
            helper.setVisible(R.id.pb_music, false);
            helper.setVisible(R.id.tv_music_download, true);
        } else if (item.getState() == 1) {//下载中
            helper.setVisible(R.id.tv_music_pick, false);
            helper.setVisible(R.id.pb_music, true);
            helper.setVisible(R.id.tv_music_download, false);
        }


//        if (item.get() >= 0) {
//            helper.setImageResource(R.id.iv_mic_people_pic, R.drawable.head0);
//        }
    }
}
