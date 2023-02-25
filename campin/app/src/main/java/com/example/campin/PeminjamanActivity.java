package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PeminjamanActivity extends AppCompatActivity {

    ImageView ic_kembali;
    SwipeRefreshLayout srl_main;
    ArrayList<String> array_nama_barang,array_ruangan,array_lokasi,array_tgl_peminjaman,array_jumlah,array_nama,array_nim,array_foto,array_id;
    ProgressDialog progressDialog;
    ListView listProses;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);

        //set variable sesuai dengan widget yang digunakan
        listProses = findViewById(R.id.LV);
        srl_main    = findViewById(R.id.swipe_container);
        progressDialog = new ProgressDialog(this);

        ic_kembali = findViewById(R.id.ic_kembali_peminjaman);

        ic_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PeminjamanActivity.this,BerandaActivity.class);
                startActivity(intent);
            }
        });

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

    public void scrollRefresh(){
        progressDialog.setMessage("Mengambil Data Barang yang dipinjam....");
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
        array_nama_barang       = new ArrayList<String>();
        array_ruangan           = new ArrayList<String>();
        array_lokasi            = new ArrayList<String>();
        array_jumlah            = new ArrayList<String>();
        array_nama              = new ArrayList<String>();
        array_nim               = new ArrayList<String>();
        array_foto              = new ArrayList<String>();
        array_tgl_peminjaman    = new ArrayList<String>();
        array_id                = new ArrayList<String>();

        array_nama_barang.clear();
        array_ruangan.clear();
        array_lokasi.clear();
        array_jumlah.clear();
        array_nama.clear();
        array_nim.clear();
        array_foto.clear();
        array_tgl_peminjaman.clear();
        array_id.clear();

    }

    public void getData(){
        initializeArray();
        AndroidNetworking.get("https://tkjalpha19.com/mobile/api_kelompok_1/getpeminjaman.php")
                .setTag("Get Data Barang")
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
                                    array_nama_barang.add(jo.getString("nama_barang"));
                                    array_ruangan.add(jo.getString("ruangan"));
                                    array_lokasi.add(jo.getString("lokasi"));
                                    array_jumlah.add(jo.getString("jumlah"));
                                    array_nama.add(jo.getString("nama"));
                                    array_nim.add(jo.getString("nim"));
                                    array_tgl_peminjaman.add(jo.getString("tgl_peminjaman"));
                                    array_foto.add(jo.getString("foto"));

                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_Peminjaman adapter = new CLV_Peminjaman(PeminjamanActivity.this,array_nama_barang,array_jumlah,array_foto);
                                //Set adapter to list
                                listProses.setAdapter(adapter);

                                //edit and delete
                                listProses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("TestKlik",""+array_nama_barang.get(position));
                                        Toast.makeText(PeminjamanActivity.this, array_nama_barang.get(position), Toast.LENGTH_SHORT).show();

                                        //Setelah proses koneksi keserver selesai, maka aplikasi akan berpindah class
                                        //DataActivity.class dan membawa/mengirim data-data hasil query dari server.
                                        Intent i = new Intent(PeminjamanActivity.this, DetailBarangPeminjamActivity.class);

                                        i.putExtra("id",array_id.get(position));
                                        i.putExtra("nama_barang",array_nama_barang.get(position));
                                        i.putExtra("ruangan",array_ruangan.get(position));
                                        i.putExtra("lokasi",array_lokasi.get(position));
                                        i.putExtra("jumlah",array_jumlah.get(position));
                                        i.putExtra("nama",array_nama.get(position));
                                        i.putExtra("nim",array_nim.get(position));
                                        i.putExtra("tgl_peminjaman",array_tgl_peminjaman.get(position));

                                        startActivity(i);
                                    }
                                });

                            }else{
                                Toast.makeText(PeminjamanActivity.this, "Tidak ada barang yang sedang dipinjam", Toast.LENGTH_SHORT).show();

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
