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

public class CLV_Saran extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> vNama;
    private ArrayList<String> vNim;
    private ArrayList<String> vSaran;
    private ArrayList<String> vFoto;

    public CLV_Saran(Activity context, ArrayList<String> Nama, ArrayList<String> Nim, ArrayList<String> Saran) {
        super(context, R.layout.list_item_user,Nama);
        this.context = context;
        this.vNama = Nama;
        this.vNim = Nim;
        this.vSaran = Saran;

    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_saran, null, true);

        //Declarasi komponen
        TextView nama             = rowView.findViewById(R.id.nama);
        TextView nim            =  rowView.findViewById(R.id.nim);
        TextView saran            =  rowView.findViewById(R.id.saran);

        //Set Parameter Value sesuai widget textview
        nama.setText(vNama.get(position));
        nim.setText(vNim.get(position));
        saran.setText(vSaran.get(position));

        //Load the animation from the xml file and set it to the row
        //load animasi untuk listview
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.down_from_top);
        animation.setDuration(500);
        rowView.startAnimation(animation);

        return rowView;
    }


}
