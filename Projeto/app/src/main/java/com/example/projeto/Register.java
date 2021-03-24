package com.example.projeto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends AppCompatActivity {


    public  static  final int GETFOTO=1;
    private EditText eName, eFavBook;
    private Spinner spin;
    private Button bSave;
    private ImageView img;
    int result, finalresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eName = findViewById(R.id.edit_Name_Register);
        eFavBook = findViewById(R.id.edit_FavBook);
        spin = findViewById(R.id.spin_genre_register);
        bSave = findViewById(R.id.btn_Register);
        img = findViewById(R.id.image_Register);
        MyBD db = new MyBD(getApplicationContext());

        finalresult =  db.getUserId(result);
        if (finalresult == 1){
            Intent it = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(it);
            finish();
        }


        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this,
                R.array.genres,R.layout.itemspinner);
        adapt.setDropDownViewResource(R.layout.itemspinner);

        spin.setAdapter(adapt);
    }

    @Override
    protected void onStart() {
        super.onStart();

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    BitmapDrawable db_bitmap = (BitmapDrawable) img.getDrawable();
                    Bitmap bmp = db_bitmap.getBitmap();

                    MyBD db = new MyBD(Register.this);
                    User uInfo = new User(eName.getText().toString(), spin.getSelectedItem().toString(), eFavBook.getText().toString(), bmp);
                    db.createUser(uInfo.Name, uInfo.Genre, uInfo.FavBook, true, uInfo.Photo);

                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(it);
                    finish();
                    Toast.makeText(Register.this, "Bem-Vindo, " + eName.getText().toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it,GETFOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GETFOTO && resultCode==RESULT_OK){
            Uri uri =  Uri.parse(data.getData().toString());
            img.setImageURI(uri);
        }
    }


}