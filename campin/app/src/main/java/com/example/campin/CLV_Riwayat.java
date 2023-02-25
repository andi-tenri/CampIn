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

public class CLV_Riwayat extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> vNama;
    private ArrayList<String> vNim;
    private ArrayList<String> vNama_Barang;
    private ArrayList<String> vLokasi;
    private ArrayList<String> vRuangan;
    private ArrayList<String> vJumlah;
    private ArrayList<String> vTgl_Pengembalian;
    private ArrayList<String> vTgl_Peminjaman;
    private ArrayList<String> vFoto;

        public CLV_Riwayat(Activity context, ArrayList<String> Nama, ArrayList<String> NIM,ArrayList<String> NamaBarang,
                           ArrayList<String> Lokasi, ArrayList<String> Ruangan, ArrayList<String> Jumlah, ArrayList<String> TglPengembalian,
                           ArrayList<String> TglPeminjaman,ArrayList<String> Foto) {
        super(context, R.layout.list_riwayat,Nama);
        this.context = context;
        this.vNama              = Nama;
        this.vNim               = NIM;
        this.vNama_Barang       = NamaBarang;
        this.vLokasi            = Lokasi;
        this.vRuangan           = Ruangan;
        this.vJumlah            = Jumlah;
        this.vTgl_Pengembalian  = TglPengembalian;
        this.vTgl_Peminjaman    = TglPeminjaman;
        this.vFoto              = Foto;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_riwayat, null, true);

        //Declarasi komponen
        TextView namabarang       = rowView.findViewById(R.id.namabarang);
        TextView nama             = rowView.findViewById(R.id.nama);
        TextView tglpengembalian  = rowView.findViewById(R.id.tglpengembalian);
        CircleImageView foto      = rowView.findViewById(R.id.pic_barang);

        //Set Parameter Value sesuai widget textview
        namabarang.setText(vNama_Barang.get(position));
        nama.setText(vNama.get(position));
        tglpengembalian.setText(vTgl_Pengembalian.get(position));

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
