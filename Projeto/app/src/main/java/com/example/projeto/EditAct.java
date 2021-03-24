package com.example.projeto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class EditAct extends AppCompatActivity {

    public static final int GETPHOTO = 1;
    Spinner spin;
    ImageView photo;
    EditText editid, edittitle;
    Button btnsave;
    MyBD bd = new MyBD(EditAct.this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

            spin = findViewById(R.id.spin_genre_edit);
            ArrayAdapter<CharSequence> adpt = ArrayAdapter
                    .createFromResource(EditAct.this, R.array.genres,
                            R.layout.itemspinner);

            adpt.setDropDownViewResource(R.layout.itemspinner);
            spin.setAdapter(adpt);
            photo = findViewById(R.id.img_foto_edit);
            editid = findViewById(R.id.edit_id_edit);
            edittitle = findViewById(R.id.edit_title_edit);
            btnsave = findViewById(R.id.btn_save_edit);
            showBook();

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(editid.getText().toString());
                    String title_ = edittitle.getText().toString();
                    String genre_ = spin.getSelectedItem().toString();
                    BitmapDrawable drw = (BitmapDrawable) photo.getDrawable();
                    Bitmap bmp = drw.getBitmap();
                    Book bk = new Book(id, title_, genre_, bmp);
                    bd.updateBook(String.valueOf(id),bk.title,bk.genre,bk.photo);


                    finish();
                }catch (Exception e){
                    Toast.makeText(EditAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = Uri.parse(data.getData().toString());
        photo.setImageURI(uri);
    }

    void showBook(){
            Intent it = getIntent();
            Book bk = it.getParcelableExtra("book");
            editid.setText(String.valueOf(bk.id));
            edittitle.setText(bk.title);
            photo.setImageBitmap(bk.getBitmap());
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                    it.setType("image/*");
                    startActivityForResult(it,GETPHOTO);
                }
            });

            spin.setSelection(App.GetPositionCat(bk.genre));
        }
    }
