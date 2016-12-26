package com.aboluo.XUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aboluo.model.MessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2016/12/26.
 */

public class DataHelper {
    // 数据库名称
    private static String DB_NAME = "weibo.db";
    // 数据库版本
    private static int DB_VERSION = 2;
    private SQLiteDatabase db;
    private SqliteHelper dbHelper;

    public DataHelper(Context context) {
        if(dbHelper ==null) {
            dbHelper = new SqliteHelper(context, DB_NAME, null, DB_VERSION);
        }else {}
        db = dbHelper.getWritableDatabase();
    }

    public void Close() {
        db.close();
        dbHelper.close();
    }

    public List<MessageBean> GetUserList() {
        List<MessageBean> messageBeanList = new ArrayList<MessageBean>();
        Cursor cursor = db.query(SqliteHelper.TB_NAME, null, null, null, null,
                null, MessageBean.ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
            MessageBean message = new MessageBean();
            message.set_id(cursor.getString(0));
            message.setMessageinfo(cursor.getString(1));
            message.setCreatetime(cursor.getString(2));
            messageBeanList.add(message);
            cursor.moveToNext();
        }
        cursor.close();
        return messageBeanList;
    }

    // 添加users表的记录
    public Long SaveUserInfo(MessageBean message) {
        ContentValues values = new ContentValues();
        values.put(MessageBean.MESSAGEINFO, message.getMessageinfo());
        values.put(MessageBean.CREATIME, message.getCreatetime());
        Long uid = db.insert(SqliteHelper.TB_NAME, null, values);
        Log.e("SaveUserInfo", uid + "");
        return uid;
    }

}
