package com.example.campin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CLV_Peminjaman extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> vNamaBarang;
    private ArrayList<String> vJumlah;
    private ArrayList<String> vFoto;


    public CLV_Peminjaman(Activity context, ArrayList<String> NamaBarang, ArrayList<String> Jumlah, ArrayList<String> Foto) {
        super(context, R.layout.list_barang_dipinjam,NamaBarang);
        this.context     = context;
        this.vNamaBarang = NamaBarang;
        this.vJumlah     = Jumlah;
        this.vFoto       = Foto;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_barang_dipinjam, null, true);

        //Declarasi komponen
        TextView namabarang             = rowView.findViewById(R.id.nama_barang_pinjam);
        TextView jumlah          = rowView.findViewById(R.id.jumlahpinjam);
        CircleImageView foto      = rowView.findViewById(R.id.pic_pinjam);

        //Set Parameter Value sesuai widget textview
        namabarang.setText(vNamaBarang.get(position));
        jumlah.setText(vJumlah.get(position));

        if (vFoto.get(position).equals(""))
        {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/noimages.png").into(foto);
        }
        else
        {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/"+vFoto.get(position)).into(foto);
        }


        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }

}