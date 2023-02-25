package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Session session;
    LinearLayout keluar;
    TextView navnamalengkap,navnim;

    SharedPreferences sharedPreferences;

    public static final String SHARED_PREF_NAME     = "camppref1";
    public static final String KEY_NAMA_LENGKAP     = "nama_lengkap";
    public static final String KEY_NIM              = "nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        navnamalengkap      = findViewById(R.id.nama_navmenu);
        navnim              = findViewById(R.id.nim_navmenu);

        sharedPreferences   = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String namelengkap  = sharedPreferences.getString(KEY_NAMA_LENGKAP, null);
        String nim          = sharedPreferences.getString(KEY_NIM, null);

        navnamalengkap.setText(namelengkap);
        navnim.setText(nim);

        drawerLayout = findViewById(R.id.drawer_layout);

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

    }

    public void ClickNavMenu (View view) {

        BerandaActivity.openDrawer(drawerLayout);

    }
    public void Clickberanda (View view) {

        BerandaActivity.redirectActivity(this, BerandaActivity.class);

    }

    public void Clickprofil (View view) {

        BerandaActivity.redirectActivity(this, ProfilActivity.class);

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

        recreate();

    }

    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(InfoActivity.this, LoginActivity.class));
    }

}