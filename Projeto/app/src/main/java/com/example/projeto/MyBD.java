package com.example.projeto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static android.widget.Toast.LENGTH_SHORT;

public class MyBD extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "library.db";
    public static final String TABLE_NAME = "books";
    public static final String COLUMN1_NAME = "id";
    public static final String COLUMN2_NAME = "title";
    public static final String COLUMN3_NAME = "genre";
    public static final String COLUMN4_NAME = "photo";

    public static final String COLUMN0_Person = "Id";
    public static final String COLUMN1_Person = "Name";
    public static final String COLUMN2_Person = "Genre";
    public static final String COLUMN5_Person = "FavBook";
    public static final String COLUMN3_Person = "Verified";
    public static final String COLUMN4_Person = "Photo";

    public Context ctx;

    public MyBD(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String sql = "CREATE TABLE " + TABLE_NAME + " ( ";
            sql += COLUMN1_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
            sql += COLUMN2_NAME + " VARCHAR (120) NOT NULL, ";
            sql += COLUMN3_NAME + " VARCHAR (120) NOT NULL, " + COLUMN4_NAME + " BLOB);";

            String sql_person = "CREATE TABLE Person (Id INTEGER AUTO INCREMENT PRIMARY KEY, " +
                    "Name VARCHAR (120) NOT NULL, Genre Varchar (120) NOT NULL, FavBook Varchar(120) NOT NULL, Verified Boolean NOT NULL, Photo BLOB);";

            String sql_fav = "CREATE TABLE Favs ( ";
            sql_fav += COLUMN1_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
            sql_fav += COLUMN2_NAME + " VARCHAR (120) NOT NULL, ";
            sql_fav += COLUMN3_NAME + " VARCHAR (120) NOT NULL, " + COLUMN4_NAME + " BLOB);";

            db.execSQL(sql);
            db.execSQL(sql_person);
            db.execSQL(sql_fav);

        }catch (SQLException error){
            Toast.makeText(ctx, error.getMessage(), LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            onCreate(db);
        }catch (SQLException error){
            Toast.makeText(ctx, error.getMessage(), LENGTH_SHORT).show();
        }
    }

    public boolean insertBook(String title , String genre , byte[] photo){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN2_NAME,title);
            cv.put(COLUMN3_NAME,genre);
            cv.put(COLUMN4_NAME,photo);

            long i = db.insert(TABLE_NAME,null,cv);
            return (i>0)?true:false;
        }catch (SQLException error){
            Toast.makeText(ctx, error.getMessage(), LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean insertBookFav(String title , String genre , byte[] photo){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("title",title);
            cv.put("genre",genre);
            cv.put("photo",photo);

            long i = db.insert("Favs",null,cv);
            return (i>0)?true:false;

        }catch (Exception e){
            Toast.makeText(ctx, e.getMessage(), LENGTH_SHORT).show();
        }
        return false;
    }

    public Cursor getBooks(){
        Cursor cur = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            cur = db.rawQuery("select  *  from " +  TABLE_NAME + ";",null);
            return cur;
        }catch (SQLException error){
            Toast.makeText(ctx,  error.getMessage(), LENGTH_SHORT).show();
        }
        return cur;
    }

    public Cursor getFavBookFavs() {
        Cursor cur = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            cur = db.rawQuery("select  *  from " +  "Favs" + ";",null);
            return cur;
        }catch (SQLException error){
            Toast.makeText(ctx,  error.getMessage(), LENGTH_SHORT).show();
        }
        return cur;
    }

    public boolean updateBook(String id ,String title, String genre, byte[] photo){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN2_NAME,title);
        cv.put(COLUMN3_NAME,genre);
        cv.put(COLUMN4_NAME,photo);
        SQLiteDatabase db=this.getWritableDatabase();
        long i=db.update(TABLE_NAME,cv,COLUMN1_NAME + "=?", new String[]{id});
        return (i<=0)?false:true;
    }

    public boolean deleteBook(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        long i = db.delete(TABLE_NAME,COLUMN1_NAME + "=?", new String[]{id} );
        return (i<=0)?false:true;
    }

    public boolean deleteFavBook(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        long i = db.delete("Favs",COLUMN1_NAME + "=?", new String[]{id} );
        return (i<=0)?false:true;
    }

    public boolean createUser(String Name_, String Genre, String FavBook, boolean Auth_, byte[] Photo){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            int id = 1;
            cv.put(COLUMN0_Person, id);
            cv.put(COLUMN1_Person, Name_);
            cv.put(COLUMN2_Person, Genre);
            cv.put(COLUMN5_Person, FavBook);
            cv.put(COLUMN3_Person, Auth_);
            cv.put(COLUMN4_Person, Photo);
            long a = db.insert("Person",null, cv);
            return (a>0)?true:false;
        }catch (Exception e){
            Toast.makeText(ctx, e.getMessage(), LENGTH_SHORT).show();
        }
        return false;
    }

    public void getUser(TextView txt) {
        String result = null;
        SQLiteDatabase db = this.getWritableDatabase();

//        Cursor cursor = db.rawQuery("SELECT Name FROM Person", null);
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            do {
//                result = cursor.getString(0);
//            } while (cursor.moveToNext());
//            txt.setText(result);
//        }

        Cursor c = db.rawQuery("SELECT Name FROM Person", null);
        if(c.moveToNext())
        {
            result = c.getString(0);
            txt.setText(result);
        }
    }

    public int getUserId(int value_){
        String result = null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT Id FROM Person", null);
        if(c.moveToNext())
        {
            result = c.getString(0);
            value_ = Integer.parseInt(result);
            return  value_;
        }
        return value_;
    }

    public void getUserPhoto(ImageView view){
//            cursor = db.rawQuery("SELECT Photo FROM Person", null);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT Photo FROM Person", null);
        if(c.moveToNext())
        {
            byte[] image = c.getBlob(0);
            Bitmap bmp= BitmapFactory.decodeByteArray(image, 0 , image.length);
            view.setImageBitmap(bmp);
        }


    }

    public void getFavGenre(TextView txt) {
        String result = null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT Genre FROM Person", null);
        if(c.moveToNext())
        {
            result = c.getString(0);
            txt.setText(result);
        }
    }

    public void getFavBook(TextView txt) {
        String result = null;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT FavBook FROM Person", null);
        if(c.moveToNext())
        {
            result = c.getString(0);
            txt.setText(result);
        }
    }

    public boolean deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.delete("Person", "", null);
        return (i<=0)?false:true;
    }

    public boolean deleteBooks(){
        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.delete("books", "", null);
        return (i<=0)?false:true;
    }



}