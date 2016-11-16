package com.ycc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycc.bodyguard.HoemActivity;
import com.ycc.bodyguard.R;

/**
 * Created by Administrator on 16-11-16.
 */
public class gridViewAdapter extends BaseAdapter {

    HoemActivity hoemActivity;
    int[] arrayImage;
    String[] arrayString;

    public gridViewAdapter(HoemActivity hoemActivity, int[] arrayImage, String[] arrayString) {
        this.hoemActivity = hoemActivity;
        this.arrayImage = arrayImage;
        this.arrayString = arrayString;
    }

    @Override
    public int getCount() {
        return arrayImage.length;
    }

    @Override
    public Object getItem(int position) {
        return arrayString[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(hoemActivity).inflate(R.layout.gridview_item,null);
            viewHolder=new  ViewHolder();
            viewHolder.iv= (ImageView) convertView.findViewById(R.id.iv);
            viewHolder.tv= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        }else {
           viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.iv.setImageResource(arrayImage[position]);
        viewHolder.tv.setText(arrayString[position]);
        return convertView;
    }

    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
