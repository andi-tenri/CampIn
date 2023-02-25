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

public class DetailRiwayatAdminActivity extends AppCompatActivity {

    TextView TVRNama,TVRNIM,TVRNamaBarang,TVRRuangan,TVRLokasi,TVRJumlah,TVRTglPeminjaman,TVRTglPengembalian,navnamalengkap,navnim;
    String id,nama,nim,nama_barang,ruangan,lokasi,jumlah,tglpeminjaman,tglpengembalian,foto;
    CircleImageView image;
    ProgressDialog progressDialog;
    ImageButton BTNImage;
    Button BTNKembalikanBarang;
    ImageView ic_kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_admin);

        //Intent, get data from main
        id                  = getIntent().getStringExtra("id");
        nama                = getIntent().getStringExtra("nama");
        nim                 = getIntent().getStringExtra("nim");
        nama_barang         = getIntent().getStringExtra("nama_barang");
        ruangan             = getIntent().getStringExtra("ruangan");
        lokasi              = getIntent().getStringExtra("lokasi");
        jumlah              = getIntent().getStringExtra("jumlah");
        tglpeminjaman       = getIntent().getStringExtra("tgl_peminjaman");
        tglpengembalian     = getIntent().getStringExtra("tgl_pengembalian");
        foto                = getIntent().getStringExtra("foto");


        TVRNama             = findViewById(R.id.TVR_nama);
        TVRNIM              = findViewById(R.id.TVR_nim);
        TVRNamaBarang       = findViewById(R.id.TVR_nama_barang);
        TVRRuangan          = findViewById(R.id.TVR_ruangan);
        TVRLokasi           = findViewById(R.id.TVR_lokasi);
        TVRJumlah           = findViewById(R.id.TVR_jumlahbarangpinjam);
        TVRTglPeminjaman    = findViewById(R.id.TVR_tglpeminjaman);
        TVRTglPengembalian  = findViewById(R.id.TVR_tglpengembalian);
        ic_kembali          = findViewById(R.id.ic_kembali_detailriwayatadminbarang);

        image               = findViewById(R.id.pic_barang_riwayat);

        TVRNama.setText(nama);
        TVRNIM.setText(nim);
        TVRNamaBarang.setText(nama_barang);
        TVRRuangan.setText(ruangan);
        TVRLokasi.setText(lokasi);
        TVRJumlah.setText(jumlah);
        TVRTglPeminjaman.setText(tglpeminjaman);
        TVRTglPengembalian.setText(tglpengembalian);


        if (foto.equals("")) {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/noimages.png").into(image);
        } else {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/" + foto).into(image);
        }

        ic_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailRiwayatAdminActivity.this,RiwayatAdminActivity.class);
                startActivity(intent);
            }
        });

    }
}