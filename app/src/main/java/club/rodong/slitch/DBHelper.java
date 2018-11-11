/*
package club.rodong.playerforstream;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import club.rodong.playerforstream.POJO.QueryBox;

*/
/**
 * Created by Bluewarn on 2018-03-13.
 *//*


public class DBHelper extends SQLiteOpenHelper {
    Context context;
    public DBHelper(Context c){
        super(c, "pdid.db", null, 1);
        context = c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QueryBox.Twitch_sqlCreateTbl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void CREATE_DB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(QueryBox.Twitch_sqlCreateTbl);
    }
    public String SELECT_T_NAME(int stid){
        String Name = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try{
            cursor = db.rawQuery(QueryBox.SelectWhere_STID + stid,null);
            try{
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    Name = cursor.getString(1);
                    cursor.moveToNext();
                }
            }finally {
                cursor.close();
                db.close();
            }
        }catch (SQLiteException e){
            Toast.makeText(context, "Database 실패", Toast.LENGTH_SHORT).show();
        }
        return Name;
    }
    public String SELECT_T_LOGIN(int stid){
        String LOGIN = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try{
            cursor = db.rawQuery(QueryBox.SelectWhere_STID + stid,null);
            try{
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    LOGIN = cursor.getString(2);
                    cursor.moveToNext();
                }
            }finally {
                cursor.close();
                db.close();
            }
        }catch (SQLiteException e){
            Toast.makeText(context, "Database 실패", Toast.LENGTH_SHORT).show();
        }
        return LOGIN;
    }
    public String SELECT_PROFILEURL_Twitch(int stid){
        String P_URL = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QueryBox.SelectWhere_STID + stid,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            P_URL = cursor.getString(3);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return P_URL;
    }
    public ArrayList<Integer> SELECT_STID(){
        ArrayList<Integer> ID = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(QueryBox.Select_STID,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int D_STID = cursor.getInt(0);
            ID.add(D_STID);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return ID;
    }
    public void INSERT_Twitch(long stid, String username, String login, String profileimgurl, boolean msg){
        Log.i("INSERT_Twitch()","실행됨");
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.execSQL(QueryBox.Insert_STID + "(" + stid + ",\"" + username + "\",\"" + login + "\",\"" + profileimgurl + "\")");
        }catch (SQLiteException e){
            if(msg){
                //Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, context.getString(R.string.AlreadySaved_Twitch), Toast.LENGTH_SHORT).show();
            }
        }
        db.close();
    }
    public void DELETE_STID(){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            //db.rawQuery(QueryBox.Delete_STID,null);
            //db.execSQL(QueryBox.Delete_STID);
            db.delete(QueryBox.TBL_STID,null,null);
        }catch (SQLiteException e){
            Log.i("DELETE_STID",e.getLocalizedMessage());
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public int getTwitchCount(){
        int count;
        SQLiteDatabase db = this.getReadableDatabase();
        count = (int)(DatabaseUtils.queryNumEntries(db, QueryBox.TBL_STID));
        db.close();
        return count;
    }
}

*/
