package com.aboluo.XUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aboluo.model.MessageBean;
import com.aboluo.model.UserSqlInfo;

/**
 * Created by CJ on 2016/12/26.
 */

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String TB_NAME = "MESSAGE";
    public static final String TB_NAME2 = "UserInfo";

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql  = "CREATE TABLE IF NOT EXISTS " +
                TB_NAME + "(" +
                MessageBean.ID + " integer primary key autoincrement," +
                MessageBean.MESSAGEINFO + " varchar," +
                MessageBean.CREATIME + " varchar" +
                ")";
        String sql2  = "CREATE TABLE IF NOT EXISTS " +
                TB_NAME2 + "(" +
                UserSqlInfo.ID + " integer primary key autoincrement," +
                UserSqlInfo.MemberId + " varchar," +
                UserSqlInfo.GESTURE + " varchar" +
                UserSqlInfo.ISUSE + " varchar" +
                ")";
        db.execSQL(sql);
        db.execSQL(sql2);
        Log.e("Database", "onCreate"+sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TB_NAME );
        onCreate(db);
        Log. e("Database" ,"onUpgrade" );
    }
    //更新列
    public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn){
        try{
            db.execSQL( "ALTER TABLE " +
                    TB_NAME + " CHANGE " +
                    oldColumn + " "+ newColumn +
                    " " + typeColumn
            );
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
