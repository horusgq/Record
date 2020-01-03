package com.xenome.Students_Record;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME= "MYMates.db";
    public final static String TABLE_NAME= "mytable_table";
    public final static String COL_1= "ID";
    public final static String COL_2= "NAME";
    public final static String COL_3= "EMAIL";
    public final static String COL_4= "COURSE_COUNT";
    public final static String COL_5= "Enrollment";
    public final static String COL_6="Course_Name";



    public Databasehelper(@Nullable Context context) { super(context, DATABASE_NAME, null, 14);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY , "+
                "NAME TEXT," +
                " EMAIL TEXT," +
                " COURSE_COUNT INTEGER," +
                " Enrollment TEXT," +
                "Course_Name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i14) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, String email, String courseCount, String Enrollment, String Coursename){
        SQLiteDatabase db= this.getWritableDatabase() ;
    ContentValues contentValues= new ContentValues();

    contentValues.put(COL_2,name);
    contentValues.put(COL_3,email);
    contentValues.put(COL_4,courseCount);
    contentValues.put(COL_5,Enrollment);
    contentValues.put(COL_6,Coursename);
    long results = db.insert(TABLE_NAME,null,contentValues);
    if(results==-1){
        return false;
    }
    else {
        return true;
    }
    }

    public boolean updateData(String id,String name, String email, String courseCount,String Enrollment, String Coursename){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,courseCount);
        contentValues.put(COL_5, Enrollment);
        contentValues.put(COL_6, Coursename);

        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;
    }

    public Cursor getData(String id){
        SQLiteDatabase db= this.getWritableDatabase();
        String query= "SELECT * FROM " +TABLE_NAME+" WHERE ID=' "+id+" ' ";
        Cursor  cursor= db.rawQuery(query, null);
        return cursor;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db= this.getWritableDatabase();
        return  db.delete(TABLE_NAME,"ID=?", new String[]{id});
    }

    public Integer deleteAll(){
        SQLiteDatabase db= this.getWritableDatabase();
        return  db.delete(TABLE_NAME,null,null);

    }

    public Cursor getAllData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+TABLE_NAME,null );
        return cursor;
    }
}
