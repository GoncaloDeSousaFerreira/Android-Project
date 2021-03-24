package com.example.projeto;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class App extends android.app.Application {
    static MyBD bd;
    public static Context ctx;
    public static List<Book> books = new ArrayList<Book>();
    public static List<Fav> booksfavs = new ArrayList<Fav>();

    public static List<Book> getBooksApp() {
        books.clear();
        createList();
        return books;
    }

    public static List<Fav> getBooksAppFavs() {
        booksfavs.clear();
        createList2();
        return booksfavs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();


        bd= new MyBD(ctx);
        createList();
    }

    public static int GetPositionCat(String cat) {
        int pos = 0;
        String[] cats = ctx.getResources().getStringArray(R.array.genres);
        for (pos = 0; pos < cats.length; pos++) {
            if (cat.equals(cats[pos])) {
                return pos;
            }
        }
        return pos;
    }

    public static void createList() {
        try{
            books.clear();
            books = new ArrayList<Book>();
            Cursor cur = bd.getBooks();
            if (cur == null || cur.getCount() <= 0) return;
            Book bk;
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                do{
                    bk = new Book(cur.getInt(0), cur.getString(1), cur.getString(2), cur.getBlob(3));
                    books.add(bk);
                }while(cur.moveToNext());
            }
        }catch (Exception error){
            Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public static void createList2() {
        try{
            booksfavs.clear();
            booksfavs = new ArrayList<Fav>();
            Cursor cur = bd.getFavBookFavs();
            if (cur == null || cur.getCount() <= 0) return;
            Fav bk;
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                do{
                    bk = new Fav(cur.getInt(0), cur.getString(1), cur.getString(2), cur.getBlob(3));
                    booksfavs.add(bk);
                }while(cur.moveToNext());
            }
        }catch (Exception error){
            Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
