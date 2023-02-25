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

public class KontakAdminActivity extends AppCompatActivity {

    ImageView ic_kembali, ic_tambah;
    SwipeRefreshLayout srl_main;
    ArrayList<String> array_nama_staff,array_id_staff,array_nohp,array_foto,array_id;
    ProgressDialog progressDialog;
    ListView listProses;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak_admin);

        //set variable sesuai dengan widget yang digunakan
        listProses = findViewById(R.id.LV);
        srl_main    = findViewById(R.id.swipe_container);
        progressDialog = new ProgressDialog(this);

        ic_kembali = findViewById(R.id.ic_kembali_listkontak);
        ic_tambah  = findViewById(R.id.ic_tambah_kontak);

        ic_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KontakAdminActivity.this,BerandaAdminActivity.class);
                startActivity(intent);
            }
        });

        ic_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KontakAdminActivity.this,TambahKontakAdminActivity.class);
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
        progressDialog.setMessage("Mengambil Data Staf....");
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
        array_nama_staff    = new ArrayList<String>();
        array_id_staff      = new ArrayList<String>();
        array_nohp          = new ArrayList<String>();
        array_foto          = new ArrayList<String>();
        array_id            = new ArrayList<String>();

        array_nama_staff.clear();
        array_id_staff.clear();
        array_nohp.clear();
        array_foto.clear();
        array_id.clear();

    }

    public void getData(){
        initializeArray();
        AndroidNetworking.get("https://tkjalpha19.com/mobile/api_kelompok_1/getkontak.php")
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
                                    array_nama_staff.add(jo.getString("nama"));
                                    array_id_staff.add(jo.getString("id_staff"));
                                    array_nohp.add(jo.getString("no_hp"));
                                    array_foto.add(jo.getString("foto"));

                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_Kontak adapter = new CLV_Kontak(KontakAdminActivity.this,array_nama_staff,array_id_staff,
                                        array_nohp,array_foto);
                                //Set adapter to list
                                listProses.setAdapter(adapter);

                                //edit and delete
                                listProses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("TestKlik",""+array_nama_staff.get(position));
                                        Toast.makeText(KontakAdminActivity.this, array_nama_staff.get(position), Toast.LENGTH_SHORT).show();

                                        //Setelah proses koneksi keserver selesai, maka aplikasi akan berpindah class
                                        //DataActivity.class dan membawa/mengirim data-data hasil query dari server.
                                        Intent i = new Intent(KontakAdminActivity.this, DetailKontakStaffActivity.class);

                                        i.putExtra("id",array_id.get(position));
                                        i.putExtra("nama",array_nama_staff.get(position));
                                        i.putExtra("id_staff",array_id_staff.get(position));
                                        i.putExtra("no_hp",array_nohp.get(position));
                                        i.putExtra("foto",array_foto.get(position));

                                        startActivity(i);
                                    }
                                });

                            }else{
                                Toast.makeText(KontakAdminActivity.this, "Data Kontak Staff Kosong", Toast.LENGTH_SHORT).show();

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