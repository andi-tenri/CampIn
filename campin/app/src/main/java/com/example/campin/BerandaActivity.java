package com.example.campin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class BerandaActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    LinearLayout keluar;
    TextView TVNamaPengguna,navnamalengkap,navnim;
    RelativeLayout fitur_kirimSaran, fitur_kontakstaff, fitur_barang, fitur_peminjaman;
    private RecyclerView recyclerView;
    private StaticInfoAdapter staticInfoAdapter;

    Session session;
    SharedPreferences sharedPreferences;

    public static final String SHARED_PREF_NAME  = "camppref";
    public static final String SHARED_PREF_NAME1 = "camppref1";
    public static final String KEY_NAME          = "nama_pengguna";
    public static final String KEY_NAMA_LENGKAP  = "nama_lengkap";
    public static final String KEY_NIM           = "nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

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

        drawerLayout        = findViewById(R.id.drawer_layout);
        fitur_barang        = findViewById(R.id.fitur1_listbarang);
        fitur_peminjaman    = findViewById(R.id.fitur2_peminjaman);
        fitur_kontakstaff   = findViewById(R.id.fitur3_kontakstaff);
        fitur_kirimSaran    = findViewById(R.id.fitur4_kirimsaran);

        TVNamaPengguna      = findViewById(R.id.TVBNamaPengguna);
        navnamalengkap      = findViewById(R.id.nama_navmenu);
        navnim              = findViewById(R.id.nim_navmenu);

        sharedPreferences   = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        sharedPreferences   = getSharedPreferences(SHARED_PREF_NAME1,MODE_PRIVATE);
        String name         = sharedPreferences.getString(KEY_NAME, null);
        String namelengkap  = sharedPreferences.getString(KEY_NAMA_LENGKAP, null);
        String nim         = sharedPreferences.getString(KEY_NIM, null);

        TVNamaPengguna.setText(name);
        navnamalengkap.setText(namelengkap);
        navnim.setText(nim);

        ArrayList<StaticInfoModel> item = new ArrayList<>();
        item.add(new StaticInfoModel(R.drawable.fitur2, "Selamat Datang di CampIn",
                "Sebuah aplikasi Inventaris Kampus yang bertujuan menangani peminjaman dan pengembalian inventaris kampus"));
        item.add(new StaticInfoModel(R.drawable.fitur1, "Bingung mencari lokasi barang?",
                "Dengan aplikasi Inventaris Kampus lokasi barang akan mudah ditemukan"));
        item.add(new StaticInfoModel(R.drawable.fitur3, "Proses pengembalian barang yang ribet?",
                "Proses pengembalian barang yang mudah dengan hanya memotret kondisi barang"));
        item.add(new StaticInfoModel(R.drawable.fitur4, "Susah mencari para staff ruangan?",
                "Di dalam aplikasi ini tersedian fitur kontak yang didalamnya terdapat kumpulan kontak para staff ruangan"));

        recyclerView = findViewById(R.id.rv_info);
        staticInfoAdapter = new StaticInfoAdapter(item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(staticInfoAdapter);

        fitur_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this,List_Barang_Activity.class);
                startActivity(intent);
            }
        });

        fitur_peminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this,PeminjamanActivity.class);
                startActivity(intent);
            }
        });

        fitur_kirimSaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this,KontakActivity.class);
                startActivity(intent);
            }
        });

        fitur_kontakstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BerandaActivity.this,List_Kontak_Activity.class);
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

    public void Clickprofil(View view) {

        redirectActivity(this,ProfilActivity.class);

    }

    public void Clickriwayat(View view) {

        redirectActivity(this,RiwayatActivity.class);

    }

    public void Clickinfo(View view) {

        redirectActivity(this,InfoActivity.class);

    }

//    public void Clickkeluar(View view) {
//
//        keluar(this);
//
//    }
//
//    public static void keluar(Activity activity) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//
//        builder.setTitle("Keluar");
//        builder.setMessage("Apakah anda mau keluar dari aplikasi?");
//
//        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                activity.finishAffinity();
//
//                System.exit(0);
//
//            }
//        });
//
//        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                dialog.dismiss();
//            }
//        });
//
//        builder.show();
//
//    }

    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(BerandaActivity.this, LoginActivity.class));
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