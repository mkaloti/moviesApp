package com.example.jbt.filmlibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jbt on 1/10/2017.
 */

public class helperdb extends SQLiteOpenHelper {
//SET THE TABLE AND THE COLUMN NAMES
    private static final String note_TABLE_NAME = "film";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_ADDRESS = "details";
    private static final String COL_TYPE = "url";


    public helperdb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {//CONSTRUCTOR
        super(context, "filmdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //TO CREATE TABLE ON THE DATABASE
        db.execSQL("CREATE TABLE film ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, details TEXT,url TEXT);");


    }

    @Override   //IN CASE OF A NEW VERSION DEVELOPED
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void updatefilm(film n,int id){ //UPDATE THE ROW IN THE TABLE
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name" , n.getName());
        values.put("details" , n.getDetails());
        values.put("url" , n.getUrl());

        db.update("film", values, "id="+ id,null);  //UPDATE THE FILM TABLE
        db.close();

    }



    public void addName(film n){ //ADD A ROW TO THE TABLE
        ContentValues values = new ContentValues();
        values.put("name" , n.getName());           //ADD THE NAME TO THE VALUES
        values.put("details" , n.getDetails());
        values.put("url",n.getUrl());               //ADD THE URL TO THE VALUES

        SQLiteDatabase db = getWritableDatabase();
        db.insert("film" , null , values);   //INSERT THE VALUES TO THE FILM TABLE
        db.close();
    }


public boolean isExist( String name){   //A FUNCTION TO CHECK IF ROW EXIST
    SQLiteDatabase db=this.getReadableDatabase();

   Cursor c=db.rawQuery("SELECT * FROM film WHERE NAME= '" +name+ "'",null);  //GET THE NAME TITLE
    boolean exist=(c.getCount()>0);      //IF THE COUNT MORE THAN 0 IT MEANS THAT THE NAME EXIST
    c.close();;
    db.close();
    return  exist;                  //RETURN TRUE IF EXIST

}

    public ArrayList<film> getAllNames(){

        ArrayList<film> names = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query("film",null,null,null,null,null,null,null);

        //Cursor running until the last item
        while (c.moveToNext()){
            int id = c.getInt(c.getColumnIndex("id"));                  //get ID number
             String name = c.getString(c.getColumnIndex("name"));        //get String name
            String details = c.getString(c.getColumnIndex("details"));//get String lastName
            String url=c.getString(c.getColumnIndex("url"));
            names.add(new film(id,name,details,url));                     //Every loop, pull one

        }
        return names;//return all my list

    }





    public void removefilm(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("film", "id" + "=" + id, null);   //DELETE FILM BY USING THE ID

        db.close();

    }

    public void removeallfilm(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("film", null, null);              //DELETE THE FILM TABLE

        db.close();

    }









}
