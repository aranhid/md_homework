package com.example.mycustomadapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class UserListAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> users;

    public UserListAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date begin = new Date();
        User user = users.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        ImageView ivUserPic = convertView.findViewById(R.id.userpic);
        ivUserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.RED);
            }
        });
        TextView tvName = convertView.findViewById(R.id.name);
        TextView tvPhone = convertView.findViewById(R.id.phone);

        tvName.setText(user.name);
        tvPhone.setText(user.phoneNumber);
        switch (user.gender){
            case MAN:
                ivUserPic.setImageResource(R.drawable.user_man);
                break;
            case WOMAN:
                ivUserPic.setImageResource(R.drawable.user_woman);
                break;
            case UNKNOWN:
                ivUserPic.setImageResource(R.drawable.user_unknown);
                break;
        }
        Date finish = new Date();
        Log.d("MyTag", "getView time: " + (finish.getTime() - begin.getTime()));
        return convertView;
    }
}
