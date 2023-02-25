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
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RiwayatActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Session session;
    LinearLayout keluar;

    SwipeRefreshLayout srl_main;
    ArrayList<String> array_id,array_nama,array_nim,array_nama_barang,array_ruangan,array_lokasi,array_jumlah,array_pengembalian,array_peminjaman,array_foto;
    ProgressDialog progressDialog;
    ListView listProses;
    TextView navnamalengkap,navnim;
    private Context mContext;

    SharedPreferences sharedPreferences;

    public static final String SHARED_PREF_NAME     = "camppref1";
    public static final String KEY_NAMA_LENGKAP     = "nama_lengkap";
    public static final String KEY_NIM              = "nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

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

        //set variable sesuai dengan widget yang digunakan
        listProses = findViewById(R.id.LV);
        srl_main    = findViewById(R.id.swipe_container);
        progressDialog = new ProgressDialog(this);

        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh();
                srl_main.setRefreshing(false);
            }
        });

        // Scheme colors for animation
        srl_main.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

        );

        scrollRefresh();
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

        recreate();

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
        startActivity(new Intent(RiwayatActivity.this, LoginActivity.class));
    }

    public void scrollRefresh(){
        progressDialog.setMessage("Mengambil Riwayat Peminjaman....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        },300);
    }

    void initializeArray(){
        array_nama           = new ArrayList<String>();
        array_nim            = new ArrayList<String>();
        array_nama_barang    = new ArrayList<String>();
        array_ruangan        = new ArrayList<String>();
        array_lokasi         = new ArrayList<String>();
        array_jumlah         = new ArrayList<String>();
        array_pengembalian   = new ArrayList<String>();
        array_peminjaman     = new ArrayList<String>();
        array_foto           = new ArrayList<String>();
        array_id             = new ArrayList<String>();

        array_id.clear();
        array_nama.clear();
        array_nim.clear();
        array_nama_barang.clear();
        array_ruangan.clear();
        array_lokasi.clear();
        array_jumlah.clear();
        array_pengembalian.clear();
        array_peminjaman.clear();
        array_foto.clear();

    }

    public void getData(){
        initializeArray();
        AndroidNetworking.get("https://tkjalpha19.com/mobile/api_kelompok_1/getpengembalian.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        try{
                            Boolean status = response.getBoolean("status");
                            if(status){
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);

                                    array_id.add(jo.getString("id"));
                                    array_nama.add(jo.getString("nama"));
                                    array_nim.add(jo.getString("nim"));
                                    array_nama_barang.add(jo.getString("nama_barang"));
                                    array_lokasi.add(jo.getString("lokasi"));
                                    array_ruangan.add(jo.getString("ruangan"));
                                    array_jumlah.add(jo.getString("jumlah"));
                                    array_peminjaman.add(jo.getString("tgl_peminjaman"));
                                    array_pengembalian.add(jo.getString("tgl_pengembalian"));
                                    array_foto.add(jo.getString("foto"));

                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_Riwayat adapter = new CLV_Riwayat(RiwayatActivity.this,array_nama,array_nim,array_nama_barang,
                                        array_lokasi,array_ruangan,array_jumlah,array_peminjaman,array_pengembalian,array_foto);
                                //Set adapter to list
                                listProses.setAdapter(adapter);

                                //edit and delete
                                listProses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("TestKlik",""+array_nama.get(position));
                                        Toast.makeText(RiwayatActivity.this, array_nama.get(position), Toast.LENGTH_SHORT).show();

                                        //Setelah proses koneksi keserver selesai, maka aplikasi akan berpindah class
                                        //DataActivity.class dan membawa/mengirim data-data hasil query dari server.
                                        Intent i = new Intent(RiwayatActivity.this, DetailRiwayatActivity.class);

                                        i.putExtra("id",array_id.get(position));
                                        i.putExtra("nama",array_nama.get(position));
                                        i.putExtra("nim",array_nim.get(position));
                                        i.putExtra("nama_barang",array_nama_barang.get(position));
                                        i.putExtra("ruangan",array_ruangan.get(position));
                                        i.putExtra("lokasi",array_lokasi.get(position));
                                        i.putExtra("jumlah",array_jumlah.get(position));
                                        i.putExtra("tgl_peminjaman",array_peminjaman.get(position));
                                        i.putExtra("tgl_pengembalian", array_pengembalian.get(position));
                                        i.putExtra("foto", array_foto.get(position));

                                        startActivity(i);
                                    }
                                });


                            }else{
                                Toast.makeText(RiwayatActivity.this, "Riwayat peminjaman barang kosong!", Toast.LENGTH_SHORT).show();

                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}