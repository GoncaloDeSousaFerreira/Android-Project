package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private TextView tUser, tGenre, tBook;
    private Button btnDelete;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tUser = findViewById(R.id.txt_name);
        tGenre = findViewById(R.id.txt_FavGenre);
        tBook = findViewById(R.id.txt_FavBook);
        photo = findViewById(R.id.imagephoto_profile);
        btnDelete = findViewById(R.id.btn_delete);

    }

    @Override
    protected void onStart() {
        super.onStart();

        MyBD db = new MyBD(getApplicationContext());
        db.getUser(tUser);
        db.getFavBook(tBook);
        db.getFavGenre(tGenre);
        db.getUserPhoto(photo);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Profile.this);
                builder1.setMessage("Deseja apagar a conta?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MyBD db = new MyBD(getApplicationContext());
                                db.deleteUser();
                                db.deleteBooks();
                                finishAffinity();
                            }
                        });

                builder1.setNegativeButton(
                        "Não",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });



    }
}