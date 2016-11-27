package com.ycc.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 16-11-23.
 */
public class AddressDao {
    //访问数据库的路径
    public static String path = "data/data/com.ycc.bodyguard/files/address.db";
    private static SQLiteDatabase db;
    private static String QCellCore = "奇怪,查不到";


    //号码查询数据库
    public static String queryPhone(String phone) {
//        boolean isMatches=QCellCore.matches("\\^1[3-8]\\d{9}");
//        if(isMatches) {
        if (phone.length() >= 7) {
            phone = phone.substring(0, 7);
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            //先用号码查对应的关联表数据
            Cursor cursor = db.query("data1", new String[]{"outkey"}, "id=?", new String[]{phone}, null, null, null);
            if (cursor.moveToNext()) {
                QCellCore = cursor.getString(0);
                //再关联查询表2
                Cursor cursorSite = db.query("data2", new String[]{"location"}, "id=?", new String[]{QCellCore}, null, null, null);
                if (cursorSite.moveToNext()) {
                    QCellCore = cursorSite.getString(0);
                }
            } else {
                QCellCore = "很独特..查不到";
            }

        } else {
            //奇怪号码
            QCellCore = "很独特..查不到";
        }
        return QCellCore;
    }
}
