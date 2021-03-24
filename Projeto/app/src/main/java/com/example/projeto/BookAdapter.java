package com.example.projeto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public static final int GETEDIT=100;
    public BookAdapter bookAdapter;
    public Context ctx;
    public int mylayout;
    public List<Book>books;
    MyBD bd;

    class Handler{
        ImageView photo;
        TextView id;
        TextView title;
        TextView genre;
        Button btndelete;
        Button btnupdate;
        Button btnshare;
        Button btnFav;
    }


    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        this.ctx=context;
        this.mylayout=resource;
        this.books=objects;
        bd= new MyBD(ctx);
        bookAdapter=this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Book book = books.get(position);
        Handler handler = new Handler();

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(ctx);
            convertView = inflater.inflate(mylayout, parent,false);
            handler.photo = convertView.findViewById(R.id.photo_itemFav);
            handler.id = convertView.findViewById(R.id.id_itemFav);
            handler.title = convertView.findViewById(R.id.title_itemFav);
            handler.genre = convertView.findViewById(R.id.genre_itemFav);
            handler.btndelete = convertView.findViewById(R.id.btn_delete_itemBook);
            handler.btnupdate = convertView.findViewById(R.id.btn_update_itemBook);
            handler.btnshare = convertView.findViewById(R.id.btn_share_itemBook);
            handler.btnFav = convertView.findViewById(R.id.btn_fav);

            convertView.setTag(handler);

        }else{
            handler = (Handler)convertView.getTag();
        }

        handler.photo.setImageBitmap(book.getBitmap());
        handler.id.setText(String.valueOf(book.id));
        handler.title.setText(book.title);
        handler.genre.setText(book.genre);

        handler.btndelete.setTag(book);
        handler.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book bk = (Book) view.getTag();
                bd.deleteBook(String.valueOf(bk.id));
                App.createList();

                //
                bookAdapter.clear();
                bookAdapter.addAll(App.getBooksApp());
                bookAdapter.notifyDataSetChanged();
            }
        });

        handler.btnupdate.setTag(book);
        handler.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Book bk = (Book) view.getTag();
                    Intent it = new Intent(ctx, EditAct.class);
                    it.putExtra("book",bk);
                    ((MainActivity)ctx).startActivityForResult(it,GETEDIT);
                }
        });

        handler.btnshare.setTag(book);
        handler.btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book bk = (Book) v.getTag();
                Intent it = new Intent();
                it.setAction(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_TEXT, "Boas, venho partilhar este excelente livro contigo: " + bk.title + "\nDisponivel na MyLibrary!");
                it.setType("text/plain");
                ctx.startActivity(it);
            }
        });

        handler.btnFav.setTag(book);
        handler.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book bk = (Book) v.getTag();
                Fav fav = new Fav(bk.id, bk.title, bk.genre, bk.photo);
                bd.insertBookFav(fav.title, fav.genre, fav.photo);
                Toast.makeText(ctx, "Adicionado aos favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
