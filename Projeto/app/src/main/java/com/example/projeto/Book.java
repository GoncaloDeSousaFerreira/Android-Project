package com.example.projeto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

public class Book implements Parcelable {

    int id;
    String title;
    String genre;
    byte[] photo;

    public Bitmap getBitmap(){
        return BitmapFactory.decodeByteArray(this.photo,0,photo.length);
    }

    public byte[] BitmapToArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    public Book(int id, String title_, String genre_, byte[] photo_) {
        this.id = id;
        this.title = title_;
        this.genre = genre_;
        this.photo = photo_;
    }

    public Book(int id, String title_, String genre_, Bitmap bmp_) {
        this.id = id;
        this.title = title_;
        this.genre = genre_;
        this.photo = BitmapToArray(bmp_);
    }


    protected Book(Parcel in) {
        id = in.readInt();
        title = in.readString();
        genre = in.readString();
        photo = in.createByteArray();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //region getter/setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    //endregion

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(genre);
        dest.writeByteArray(photo);
    }
}
