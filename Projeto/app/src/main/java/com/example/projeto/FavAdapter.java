package com.example.projeto;

import android.content.Context;
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

public class FavAdapter extends ArrayAdapter<Fav> {
    public static final int GETEDIT=100;
    public FavAdapter favadapter;
    public Context ctx;
    public int mylayout;
    public List<Fav>favs;
    MyBD bd;

    class Handler{
        ImageView photo;
        TextView id;
        TextView title;
        TextView genre;
        Button btnUnfav;
    }

    public FavAdapter(@NonNull Context context, int resource, @NonNull List<Fav> objects) {
        super(context, resource, objects);
        this.ctx=context;
        this.mylayout=resource;
        this.favs=objects;
        bd= new MyBD(ctx);
        favadapter=this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Fav fav = favs.get(position);
        Handler handler = new Handler();

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(ctx);
            convertView = inflater.inflate(mylayout, parent,false);
            handler.photo = convertView.findViewById(R.id.photo_itemFav);
            handler.id = convertView.findViewById(R.id.id_itemFav);
            handler.title = convertView.findViewById(R.id.title_itemFav);
            handler.genre = convertView.findViewById(R.id.genre_itemFav);
            handler.btnUnfav = convertView.findViewById(R.id.btn_UnFav);

            convertView.setTag(handler);
        }else{
            handler = (FavAdapter.Handler)convertView.getTag();
        }

        handler.photo.setImageBitmap(fav.getBitmap());
        handler.id.setText(String.valueOf(fav.id));
        handler.title.setText(fav.title);
        handler.genre.setText(fav.genre);


        handler.btnUnfav.setTag(fav);
        handler.btnUnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fav fav = (Fav) v.getTag();
                bd.deleteFavBook(String.valueOf(fav.id));
                App.createList2();

                //
                favadapter.clear();
                favadapter.addAll(App.getBooksAppFavs());
                favadapter.notifyDataSetChanged();
                Toast.makeText(ctx, "Removido dos favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
