package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BerandaAdminActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    LinearLayout keluar;
    Session session;
    RelativeLayout fitur_barang,fitur_peminjaman, fitur_kontak, fitur_saran,fitur_riwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_admin);

        session             = new Session(this);

        keluar       = findViewById(R.id.linearLayoutadmin);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        drawerLayout       = findViewById(R.id.drawer_layout1);
        fitur_barang       = findViewById(R.id.fitur1_listbarang);
        fitur_kontak       = findViewById(R.id.fitur2_kontak);
        fitur_peminjaman   = findViewById(R.id.fitur3_user);
        fitur_saran        = findViewById(R.id.fitur4_saran);
        fitur_riwayat      = findViewById(R.id.fitur5_riwayat);

        fitur_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaAdminActivity.this,List_Barang_Admin_Activity.class);
                startActivity(intent);
            }
        });

        fitur_peminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaAdminActivity.this, List_Peminjaman_Admin_Activity.class);
                startActivity(intent);
            }
        });

        fitur_saran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaAdminActivity.this,List_Saran_Activity.class);
                startActivity(intent);
            }
        });

        fitur_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaAdminActivity.this,KontakAdminActivity.class);
                startActivity(intent);
            }
        });

        fitur_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaAdminActivity.this,RiwayatAdminActivity.class);
                startActivity(intent);
            }
        });

    }



    public void ClickNavMenu (View view) {

        openDrawer(drawerLayout);

    }

    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer((GravityCompat.START));

    }

    private static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);

        }

    }

    public void Clickberanda(View view) {

        recreate();

    }

    public void Clickinfo(View view) {

        redirectActivity(this,InfoAdminActivity.class);

    }

    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(BerandaAdminActivity.this, LoginActivity.class));
    }

    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);

    }

    public static void redirectActivity(Activity activity, Class aClass) {

        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);

    }

}