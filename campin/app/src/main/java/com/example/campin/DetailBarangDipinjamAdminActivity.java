package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailBarangDipinjamAdminActivity extends AppCompatActivity {

    TextView TVPNama,TVPNIM,TVPNamaBarang,TVPRuangan,TVPLokasi,TVPJumlah,TVPTglPeminjaman;
    String id,nama,nim,nama_barang,ruangan,lokasi,jumlah,tglpeminjaman,foto;
    CircleImageView image;
    ProgressDialog progressDialog;
    ImageButton BTNImage;
    Button BTNKembalikanBarang;
    ImageView ic_kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang_dipinjam_admin);

        //Intent, get data from main
        id                  = getIntent().getStringExtra("id");
        nama                = getIntent().getStringExtra("nama");
        nim                 = getIntent().getStringExtra("nim");
        nama_barang         = getIntent().getStringExtra("nama_barang");
        ruangan             = getIntent().getStringExtra("ruangan");
        lokasi              = getIntent().getStringExtra("lokasi");
        jumlah              = getIntent().getStringExtra("jumlah");
        tglpeminjaman       = getIntent().getStringExtra("tgl_peminjaman");
        foto                = getIntent().getStringExtra("foto");


        TVPNama             = findViewById(R.id.TVP_nama);
        TVPNIM              = findViewById(R.id.TVP_nim);
        TVPNamaBarang       = findViewById(R.id.TVP_nama_barang_balik);
        TVPRuangan          = findViewById(R.id.TVP_ruangan_balik);
        TVPLokasi           = findViewById(R.id.TVP_lokasi_balik);
        TVPJumlah           = findViewById(R.id.TVP_jumlahbarangpinjam_balik);
        TVPTglPeminjaman    = findViewById(R.id.TVP_tglpeminjaman);
        ic_kembali          = findViewById(R.id.ic_kembali_detailpinjambarang);

        image               = findViewById(R.id.pic_barang_balik);
        BTNImage            = findViewById(R.id.IM_foto_barang_balik);
        BTNKembalikanBarang = findViewById(R.id.BTNKembalikanBarang);

        TVPNama.setText(nama);
        TVPNIM.setText(nim);
        TVPNamaBarang.setText(nama_barang);
        TVPRuangan.setText(ruangan);
        TVPLokasi.setText(lokasi);
        TVPJumlah.setText(jumlah);
        TVPTglPeminjaman.setText(tglpeminjaman);

        progressDialog = new ProgressDialog(this);

        if (foto.equals("")) {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/noimages.png").into(image);
        } else {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/" + foto).into(image);
        }

        ic_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailBarangDipinjamAdminActivity.this,List_Peminjaman_Admin_Activity.class);
                startActivity(intent);
            }
        });

    }
}