package com.example.projeto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

public class Fav implements Parcelable {
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

    public Fav(int id, String title_, String genre_, byte[] photo_) {
        this.id = id;
        this.title = title_;
        this.genre = genre_;
        this.photo = photo_;
    }

    public Fav(int id, String title_, String genre_, Bitmap bmp_) {
        this.id = id;
        this.title = title_;
        this.genre = genre_;
        this.photo = BitmapToArray(bmp_);
    }

    protected Fav(Parcel in) {
        id = in.readInt();
        title = in.readString();
        genre = in.readString();
        photo = in.createByteArray();
    }

    public static final Creator<Fav> CREATOR = new Creator<Fav>() {
        @Override
        public Fav createFromParcel(Parcel in) {
            return new Fav(in);
        }

        @Override
        public Fav[] newArray(int size) {
            return new Fav[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(genre);
        dest.writeByteArray(photo);
    }
}
