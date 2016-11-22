package com.ycc.bodyguard.MobileVTD;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ycc.adapter.LinkmanArrayAdapter;
import com.ycc.bodyguard.R;
import com.ycc.util.ActivityManage;
import com.ycc.util.ReleaseCorrelation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 16-11-21.
 */
public class LinkmanActivity extends ActivityManage {
    //装Map的集合
    List<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();;

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linkman_activity);

        listView = (ListView) findViewById(R.id.listView);
        iniData(this);
        if(list.size()==0){
            ReleaseCorrelation.showT("没有联系人信息！！");
            return;
        }
        LinkmanArrayAdapter adapter=new LinkmanArrayAdapter(this,R.layout.linkman_item,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //返回数据给上一个界面
                HashMap<String, String> map = list.get(position);
                setResult(0,new Intent().putExtra("phone",map.get("number")));
                LinkmanActivity.this.finish();
            }
        });

    }

    /**
     * 获得联系人电话和名字，封装到Map放进集合
     * @return 返回信息集合
     */
    private void iniData(Context context) {
        list.clear();
        //获得系统服务
        ContentResolver content =context.getContentResolver();
        //查询字段
        final String[] CURSOR_CONDITION = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,//号码
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,//名字
        };
        // 获得游标
        Cursor phoneCursor = content.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,CURSOR_CONDITION, null, null, null);
        //遍历游标，封装数据
        if(phoneCursor.moveToFirst()){
            for (;!phoneCursor.isAfterLast();phoneCursor.moveToNext()){
                //联系人电话
                String number = phoneCursor.getString(0).trim();
                //联系人名称
                String name =phoneCursor.getString(1).trim();
                if(number.startsWith("+86")) {
                    number=number.substring(3);
                }
                if(number.contains("-")){
                   number= number.replace("-","");
                };
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put( "name",name);
                hashMap.put( "number",number);
                list.add(hashMap);
            }
        }
        phoneCursor.close();
    }
}
