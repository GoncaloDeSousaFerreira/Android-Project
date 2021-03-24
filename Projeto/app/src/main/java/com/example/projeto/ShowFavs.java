package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class ShowFavs extends AppCompatActivity {
        ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favs);

        lst = findViewById(R.id.lst_show);

        try {
            MyBD db = new MyBD(ShowFavs.this);
            Cursor cur = db.getFavBookFavs();

            if (cur != null && cur.getCount() > 0) {
                FavAdapter adapt = new FavAdapter(ShowFavs.this, R.layout.itemfav, App.getBooksAppFavs());
                lst.setAdapter(adapt);
            } else {
                Toast.makeText(this, "Sem Favoritos", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}