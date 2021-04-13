package com.example.projeto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView lst;
    private FloatingActionButton fab, fabProfile, fabfav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst = findViewById(R.id.list_main);
        fab = findViewById(R.id.floatingActionButton);
        fabProfile = findViewById(R.id.floatingActionButton3);
        fabfav = findViewById(R.id.floatingActionButton2);

        fabProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), Profile.class);
                startActivity(it);
            }
        });

        fabfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, ShowFavs.class);
                startActivity(it);
            }
        });

        MyBD db = new MyBD(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            MyBD db = new MyBD(MainActivity.this);
            Cursor cur = db.getBooks();

            if (cur != null && cur.getCount() > 0) {
                BookAdapter adapt = new BookAdapter(MainActivity.this, R.layout.itembook, App.getBooksApp());
                lst.setAdapter(adapt);
            } else {
                Toast.makeText(this, "Não há registos", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, Insert.class);
                startActivity(it);
            }
        });
    }


}