package com.fubang.live.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import com.fubang.live.R;
import com.fubang.live.entities.FaceEntity;
import com.fubang.live.util.FaceUtil;
import com.fubang.live.util.GlobalOnItemClickManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by dss886 on 15/9/22.
 */
public class TuzkiFragment extends Fragment {
    private List<FaceEntity> list = FaceUtil.getFaces();
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emotion_gird, container, false);
        BaseAdapter adapter = new TuzkiEmojiAdapter(mContext);
        GridView grid = (GridView) view.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(GlobalOnItemClickManager.getInstance().getOnItemClickListener(2));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private class TuzkiEmojiAdapter extends BaseAdapter {

        private Context mContext;

        public TuzkiEmojiAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.emotion_gird_item, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

//            Log.d("123",path+"---->"+mContext.getResources().getDrawable(getResourceId(path)));
//            Picasso.with(mContext).load(getResourceId(path)).into(holder.image);
            holder.image.setImageDrawable(mContext.getResources().getDrawable(list.get(position).getFaceId()));
//            Picasso.with(mContext).load(new File(path)).into(holder.image);
//            Picasso.with(mContext).load(path).into(holder.image);
            return convertView;
        }

        private class ViewHolder {
            public ImageView image;
        }
    }
    public int getResourceId(String name){
        try {
            Field field = R.drawable.class.getField(name);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.drawable.mr701;
    }
}
