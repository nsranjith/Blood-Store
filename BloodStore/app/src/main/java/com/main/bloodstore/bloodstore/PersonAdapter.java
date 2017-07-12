package com.main.bloodstore.bloodstore;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Sai Teja on 05-01-2017.
 */

public class PersonAdapter extends BaseAdapter {
    Context context;
    ArrayList<Person> list;

    public PersonAdapter(Context c, ArrayList<Person> list)
    {
        this.context=c;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View v=inflater.inflate(R.layout.activity_base_adapter_main,null,true);
        TextView txtName = (TextView) v.findViewById(R.id.name_list);
        TextView txtPhone = (TextView) v.findViewById(R.id.phone_list);
        TextView txtGender = (TextView) v.findViewById(R.id.gender_list);
        ImageView availabilty = (ImageView) v.findViewById(R.id.availability);
        txtName.setText("Name: " + list.get(i).getName());
        txtPhone.setText("Phone: " + list.get(i).getPhone());
        txtGender.setText("Gender: " + list.get(i).getGender());
        if (list.get(i).getStatus().equalsIgnoreCase("yes")) {
            availabilty.setImageDrawable(v.getResources().getDrawable(R.drawable.green_dot));
        } else if (list.get(i).getStatus().equalsIgnoreCase("no")) {
            availabilty.setImageDrawable(v.getResources().getDrawable(R.drawable.red_dot));
        }
        return v;
    }
}
