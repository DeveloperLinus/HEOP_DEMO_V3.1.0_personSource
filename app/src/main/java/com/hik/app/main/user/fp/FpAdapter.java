package com.hik.app.main.user.fp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hik.app.R;

import java.util.List;

/**
 * Created by liuxu11 on 2021/3/19.
 * description:
 */
public class FpAdapter extends ArrayAdapter<FpInfoShow> {
    private int resourceId;

    // 适配器的构造函数，把要适配的数据传入这里
    public FpAdapter(Context context, int textViewResourceId, List<FpInfoShow> objects){
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        FpInfoShow fpInfo = getItem(position);

        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        FpAdapter.ViewHolder viewHolder;
        if (convertView == null){
            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder = new ViewHolder();
            viewHolder.fpId = view.findViewById(R.id.fp_id);
            viewHolder.fpDel = view.findViewById(R.id.fp_del);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view = convertView;
            viewHolder = (FpAdapter.ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        if (fpInfo != null) {
            viewHolder.fpId.setText(fpInfo.getFpId());
        }
        if (fpInfo != null) {
            viewHolder.fpDel.setImageResource(fpInfo.getImageDelId());
        }

        return view;
    }

    // 定义一个内部类，用于对控件的实例进行缓存
    static class ViewHolder{
        TextView fpId;
        ImageView fpDel;
    }
}
