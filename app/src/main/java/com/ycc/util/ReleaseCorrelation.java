package com.ycc.util;

import android.util.Log;
import android.widget.Toast;

import com.ycc.MyApplication;

/**
 * Created by Administrator on 16-11-12.
 */
public class ReleaseCorrelation {


    public static void handlerExceptoinHandler(Throwable e){
        if(MyApplication.isRunning){
            //已经发布，出错了！暂时什么都不在
            return;
        }else{
            //没有发布，暂时先打印并蹦掉
            getLog("log",e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 发布后不Log
     * @param tag 标签
     * @param concent 内容
     */
    public static void getLog(String tag,String concent){
        if(!MyApplication.isRunning) {
            Log.v(tag,concent);
        }
    }

    /**
     *只存在一个吐司
     *@param content 内容
     */
    public  static  void showT(String content){
        if (toast==null) {
           toast = Toast.makeText(MyApplication.app, content, Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();

    }
    static Toast toast=null;
}
