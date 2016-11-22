package com.ycc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ycc.bodyguard.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 16-11-21.
 */
public class LinkmanArrayAdapter extends ArrayAdapter<HashMap<String ,String>>{


    private final int layout;
    private final Context context;

    public LinkmanArrayAdapter(Context context,  int linkman_item,List<HashMap<String, String>> list) {
        super(context,linkman_item,list);
        layout=linkman_item;
       this.context= context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, String> map = getItem(position);
        ViewHolder holder=null;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(layout,null);
            holder=new ViewHolder();
            holder.tvPhone= (TextView) convertView.findViewById(R.id.tv_phone);
            holder.tvName= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        holder.tvName.setText(map.get("name"));
        holder.tvPhone.setText(map.get("number"));
        return convertView;
    }



    class ViewHolder{
        TextView tvPhone;
        TextView tvName;
    }
}
