package com.example.projeto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;

import java.io.ByteArrayOutputStream;

public class User {

    String Name;
    String Genre;
    String FavBook;
    byte[] Photo;

    public Bitmap getBitmap(){
        return BitmapFactory.decodeByteArray(this.Photo,0,Photo.length);
    }

    public byte[] BitmapToArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    public User(String name, String genre, String favbook,  Bitmap photo) {
        Name = name;
        Genre = genre;
        FavBook = favbook;
        Photo = BitmapToArray(photo);
    }


}
