package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {

    TextView TVNamaPengguna,TVNamaLengkap,TVNIM,TVNohp,TVEmail,navnamalengkap,navnim;
    String namapengguna,namalengkap,nim,nohp,email;
    SwipeRefreshLayout srl_main;
    ArrayList<String> array_nama_lengkap,array_nama_pengguna,array_nim,array_email,array_foto,array_id;
    ProgressDialog progressDialog;
    ListView listProses;
    private Context mContext;

    DrawerLayout drawerLayout;
    LinearLayout keluar;
    Button BTNupdate;

    Session session;
    SharedPreferences sharedPreferences;

    public static final String SHARED_PREF_NAME     = "camppref1";
    public static final String KEY_NAMA_LENGKAP     = "nama_lengkap";
    public static final String KEY_NAMA_PENGGUNA    = "nama_pengguna";
    public static final String KEY_NIM              = "nim";
    public static final String KEY_NOHP             = "nohp";
    public static final String KEY_EMAIL            = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        TVNamaPengguna      = findViewById(R.id.TVPnama_pengguna);
        TVNamaLengkap       = findViewById(R.id.TVPnama_lengkap);
        TVNIM               = findViewById(R.id.TVPnim);
        TVNohp              = findViewById(R.id.TVPnohp);
        TVEmail             = findViewById(R.id.TVPemail);
        navnamalengkap      = findViewById(R.id.nama_navmenu);
        navnim              = findViewById(R.id.nim_navmenu);

        sharedPreferences   = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        namapengguna        = sharedPreferences.getString(KEY_NAMA_PENGGUNA, null);
        namalengkap         = sharedPreferences.getString(KEY_NAMA_LENGKAP, null);
        nim                 = sharedPreferences.getString(KEY_NIM, null);
        nohp                = sharedPreferences.getString(KEY_NOHP, null);
        email               = sharedPreferences.getString(KEY_EMAIL, null);
        String namelengkap  = sharedPreferences.getString(KEY_NAMA_LENGKAP, null);
        String nim          = sharedPreferences.getString(KEY_NIM, null);

        TVNamaPengguna.setText(namapengguna);
        TVNamaLengkap.setText(namalengkap);
        TVNIM.setText(nim);
        TVNohp.setText(nohp);
        TVEmail.setText(email);
        navnamalengkap.setText(namelengkap);
        navnim.setText(nim);

        session             = new Session(this);
        if(!session.loggedin()) {
            logout();
        }

        keluar              = findViewById(R.id.linearLayout_keluar);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
//        BTNupdate    = findViewById(R.id.BTNUp_profil);

//        BTNupdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ProfilActivity.this, UpdateProfilActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    public void ClickNavMenu (View view) {

        BerandaActivity.openDrawer(drawerLayout);

    }
    public void Clickberanda (View view) {

        BerandaActivity.redirectActivity(this, BerandaActivity.class);

    }

    public void Clickprofil (View view) {

        recreate();
    }

    public void Clickriwayat (View view) {

        BerandaActivity.redirectActivity(this,RiwayatActivity.class);

    }

//    public void Clickkontak (View view) {
//
//        BerandaActivity.redirectActivity(this, KontakActivity.class);
//
//    }

    public void Clickinfo (View view) {

        BerandaActivity.redirectActivity(this, InfoActivity.class);

    }

    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(ProfilActivity.this, LoginActivity.class));
    }

}