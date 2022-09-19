package com.hik.app.main.user.adapter;

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
public class UserAdapter extends ArrayAdapter<UserInfoShow> {
    private int resourceId;

    // 适配器的构造函数，把要适配的数据传入这里
    public UserAdapter(Context context, int textViewResourceId, List<UserInfoShow> objects){
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        UserInfoShow user = getItem(position); //获取当前项的UserInfoShow实例

        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        UserAdapter.ViewHolder viewHolder;
        if (convertView == null){
            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder = new ViewHolder();
            viewHolder.userId = view.findViewById(R.id.user_id);
            viewHolder.userName = view.findViewById(R.id.user_name);
            viewHolder.userImageFace = view.findViewById(R.id.user_image_face);
            viewHolder.userImageFp = view.findViewById(R.id.user_image_fp);
            viewHolder.userImageCard = view.findViewById(R.id.user_image_card);
            viewHolder.userArrowRight = view.findViewById(R.id.user_arrow_right);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view = convertView;
            viewHolder = (UserAdapter.ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        if (user != null) {
            viewHolder.userId.setText(user.getEmployeeNo());
        }
        if (user != null) {
            viewHolder.userName.setText(user.getName());
        }
        if (user != null) {
            viewHolder.userImageFace.setImageResource(user.getImageFaceId());
        }
        if (user != null) {
            viewHolder.userImageFp.setImageResource(user.getImageFpId());
        }
        if (user != null) {
            viewHolder.userImageCard.setImageResource(user.getImageCardId());
        }
        if (user != null) {
            viewHolder.userArrowRight.setImageResource(user.getImageArrowRightId());
        }

        return view;
    }

    // 定义一个内部类，用于对控件的实例进行缓存
    static class ViewHolder{
        TextView userId;
        TextView userName;
        ImageView userImageFace;
        ImageView userImageFp;
        ImageView userImageCard;
        ImageView userArrowRight;
    }
}
