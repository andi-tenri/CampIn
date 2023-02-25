package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailDataUserAdminActivity extends AppCompatActivity {

    EditText ETNamaPengguna,ETNamaLengkap,ETNim,ETNohp,ETRole,ETEmail,ETKataSandi;
    String id,nama_lengkap,nama_pengguna,nim,nohp,role,email,kata_sandi,foto;
    CircleImageView image;
    ProgressDialog progressDialog;
    ImageButton BTNImage;
    ImageView ic_kembali;

    String pilihan;
    private static final int PHOTO_REQUEST_GALLERY = 1;//gallery
    static final int REQUEST_TAKE_PHOTO = 1;
    final int CODE_GALLERY_REQUEST = 999;
    String rPilihan[]= {"Take a photo", "From Album"};
    public final String APP_TAG = "MyApp";
    public String photoFileName = "photo.jpg";
    File photoFile;

    Bitmap bitMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_user_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_kembali);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Intent, get data from main
        id                   = getIntent().getStringExtra("id");
        nama_lengkap         = getIntent().getStringExtra("nama_lengkap");
        nama_pengguna        = getIntent().getStringExtra("nama_pengguna");
        nim                  = getIntent().getStringExtra("nim");
        nohp                 = getIntent().getStringExtra("nohp");
        email                = getIntent().getStringExtra("email");
        kata_sandi           = getIntent().getStringExtra("kata_sandi");
        foto                 = getIntent().getStringExtra("foto");


        ETNamaPengguna       = findViewById(R.id.ETUp_namapengguna_profil);
        ETNamaLengkap        = findViewById(R.id.ETUp_namalengkap_profil);
        ETNim                = findViewById(R.id.ETUp_nim_profil);
        ETNohp               = findViewById(R.id.ETUp_nohp_profil);
        ETEmail              = findViewById(R.id.ETUp_email);
        ETKataSandi          = findViewById(R.id.ETUp_kata_sandi);

        BTNImage             = findViewById(R.id.IM_ubahfoto_profil);
        image                = findViewById(R.id.pic_profil);

        ETNamaPengguna.setText(nama_pengguna);
        ETNamaLengkap.setText(nama_lengkap);
        ETNim.setText(nim);
        ETNohp.setText(nohp);
        ETEmail.setText(email);
        ETKataSandi.setText(kata_sandi);


        if (foto.equals("")) {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/noimages.png").into(image);
        } else {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/" + foto).into(image);
        }

        progressDialog = new ProgressDialog(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_delete){
            new AlertDialog.Builder(DetailDataUserAdminActivity.this)
                    .setMessage("Apakah kamu ingin menghapus data user "+nama_lengkap+"?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.setMessage("Menghapus...");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            AndroidNetworking.post("https://tkjalpha19.com/mobile/api_kelompok_1/hapususer.php")
                                    .addBodyParameter("nama_lengkap",""+nama_lengkap)
                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            progressDialog.dismiss();
                                            try {
                                                Boolean status = response.getBoolean("status");
                                                Log.d("status",""+status);
                                                String result = response.getString("result");
                                                if(status){
                                                    new AlertDialog.Builder(DetailDataUserAdminActivity.this)
                                                            .setMessage("Data user telah dihapus!")
                                                            .setCancelable(false)
                                                            .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent i = new Intent(DetailDataUserAdminActivity.this, List_Peminjaman_Admin_Activity.class);
                                                                    startActivity(i);
                                                                    DetailDataUserAdminActivity.this.finish();
                                                                }
                                                            })
                                                            .show();

                                                }else{
                                                    Toast.makeText(DetailDataUserAdminActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                                                }
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            anError.printStackTrace();
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();


        }
        return super.onOptionsItemSelected(item);
    }

}