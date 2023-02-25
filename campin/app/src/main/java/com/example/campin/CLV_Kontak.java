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

public class CLV_Kontak extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> vNamaStaff;
    private ArrayList<String> vIdStaff;
    private ArrayList<String> vNoHP;
    private ArrayList<String> vFoto;


    public CLV_Kontak(Activity context, ArrayList<String> NamaStaff, ArrayList<String> IdStaff, ArrayList<String> NoHP, ArrayList<String> Foto) {
        super(context, R.layout.list_kontak_staff,NamaStaff);
        this.context    = context;
        this.vNamaStaff = NamaStaff;
        this.vIdStaff   = IdStaff;
        this.vNoHP      = NoHP;
        this.vFoto      = Foto;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_kontak_staff, null, true);

        //Declarasi komponen
        TextView name             = rowView.findViewById(R.id.nama_staff);
        TextView idstaff          = rowView.findViewById(R.id.id_staff);
        TextView nohp             = rowView.findViewById(R.id.nohp_staff);
        CircleImageView foto      = rowView.findViewById(R.id.pic_profil_staff);

        //Set Parameter Value sesuai widget textview
        name.setText(vNamaStaff.get(position));
        idstaff.setText(vIdStaff.get(position));
        nohp.setText(vNoHP.get(position));

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