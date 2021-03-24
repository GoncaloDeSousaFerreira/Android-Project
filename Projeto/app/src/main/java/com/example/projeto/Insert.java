package com.example.projeto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class Insert extends AppCompatActivity {

    public  static  final int GETFOTO=1;
    Button btnsave;
    Spinner spin;
    ImageView photo;
    EditText title , id_;
    MyBD bd = new MyBD(Insert.this);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GETFOTO && resultCode==RESULT_OK){
            Uri uri =  Uri.parse(data.getData().toString());
            photo.setImageURI(uri);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        title = findViewById(R.id.edit_title_insert);
        id_ = findViewById(R.id.edit_id_insert);
        btnsave = findViewById(R.id.btn_save_insert);
        spin = findViewById(R.id.spin_genre_insert);
        photo = findViewById(R.id.img_photo_insert);

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BitmapDrawable db = (BitmapDrawable) photo.getDrawable();
                    int id = Integer.parseInt(id_.getText().toString());
                    String title_ = title.getText().toString();
                    String genre_ = spin.getSelectedItem().toString();
                    Bitmap bmp = db.getBitmap();

                    Book nb = new Book(id,title_,genre_,bmp);
                    bd.insertBook(nb.title, nb.genre, nb.photo);
                    Intent it = new Intent(Insert.this,MainActivity.class);
                    startActivity(it);
                }catch (Exception error){
                    System.out.println(error);
                }
                finish();
            }
        });

        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this,
                R.array.genres,R.layout.itemspinner);
        adapt.setDropDownViewResource(R.layout.itemspinner);

        spin.setAdapter(adapt);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it,GETFOTO);
            }
        });

    }
}