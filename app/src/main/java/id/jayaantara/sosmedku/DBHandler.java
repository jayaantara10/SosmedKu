package id.jayaantara.sosmedku;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

public class DBHandler extends SQLiteOpenHelper {
    public static final String db_name="db_sosmedku";
    public static final String table_user="tb_user";
    public static final String table_sosmed="tb_sosmed";
    public static final int VER=2;

    public static final String row_id_user ="id_user";
    public static final String row_username ="username";
    public static final String row_email ="email";
    public static final String row_password="password";

    public static final String row_id_sosmed="id_sosmed";
    public static final String row_inisial="inisial";
    public static final String row_sosmed="sosmed";
    public static final String row_email_sosmed="email_sosmed";
    public static final String row_username_sosmed="username_sosmed";
    public static final String row_password_sosmed="password_sosmed";
    public static final String row_status="status";
    public static final String row_prioritas="prioritas";
    public static final String row_tanda_bisnis="tanda_bisnis";

    private SQLiteDatabase db;

    public DBHandler(Context context) {
        super(context, db_name, null, VER);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
        String query ="CREATE TABLE " + table_user + "("
                + row_id_user + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_username + " TEXT,"
                + row_email + " TEXT,"
                + row_password + " TEXT)";
        db.execSQL(query);

        String query2 ="CREATE TABLE " + table_sosmed + "("
                + row_id_sosmed + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_inisial + " TEXT,"
                + row_sosmed + " TEXT,"
                + row_email_sosmed + " TEXT,"
                + row_username_sosmed + " TEXT,"
                + row_password_sosmed + " TEXT,"
                + row_status + " TEXT,"
                + row_prioritas + " TEXT,"
                + row_tanda_bisnis + " TEXT,"
                + row_id_user + " INTEGER,"
                + " FOREIGN KEY ("+row_id_user+") REFERENCES "+table_user+"("+row_id_user+"));";
        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ table_user);
        db.execSQL("DROP TABLE IF EXISTS "+ table_sosmed);
        onCreate(db);
    }
    public void insertDataSosmed(ContentValues values){
        db.insert(table_sosmed,null,values);
    }

    public void updateDataSosmed(ContentValues values, long id){
        db.update(table_sosmed,values,row_id_sosmed + "=" + id, null);
    }

    public void deleteDataSosmed(long id){
        db.delete(table_sosmed, row_id_sosmed + "=" + id, null);
    }

    public Cursor getAllDataSosmed(){
        return db.query(table_sosmed,null,null,null,null, null,null);
    }

    public Cursor getDataSosmed(long id){
        return db.rawQuery("SELECT*FROM " + table_sosmed + " WHERE " + row_id_sosmed + "=" + id, null);
    }

    public Cursor getDataSosmedByUserId(long id){
        return db.rawQuery("SELECT*FROM " + table_sosmed + " WHERE " + row_id_user + "=" + id, null);
    }

    public Cursor getDataSosmedByUserIdSearchBar(long id, Editable s){
        return db.rawQuery("SELECT*FROM " + table_sosmed + " WHERE " + row_id_user + "=" + id + " AND " + row_inisial + " LIKE " +  " '%" + s + "%'", null);
    }

    public void insertDataUser(ContentValues values){
        db.insert(table_user,null,values);
    }

    public void updateDataUser(ContentValues values, long id){
        db.update(table_user,values,row_id_user + "=" + id, null);
    }

    public boolean checkUser(String username, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        String [] columns = {row_id_user};
        String selections = row_username + "=?" + " and " + row_password + "=?";
        String [] selectionArgs = {username, password};
        Cursor cursor = db.query(table_user, columns, selections, selectionArgs, null, null, null);
        int count = cursor.getCount();
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    public long getIdUser(String username, String password){
        long id_user = 0;
        SQLiteDatabase DB = this.getWritableDatabase();
        String[] columns = {row_id_user};
        String selection = row_username + "=?" + " and " + row_password + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(table_user, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.moveToFirst();
        id_user = cursor.getLong(0);
        cursor.close();
        if (cursorCount > 0){
            return id_user;
        }
        return id_user;
    }

    public Cursor getUser(long id){
        return db.rawQuery("SELECT*FROM " + table_user + " WHERE " + row_id_user + "=" + id, null);
    }

    public void deleteUser(long id){
        db.delete(table_user, row_id_user + "=" + id, null);
    }
}
