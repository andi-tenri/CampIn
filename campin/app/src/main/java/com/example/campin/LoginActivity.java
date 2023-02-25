package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText ETLnama_pengguna, ETLkataSandi;
    TextView TVLhello, TVLsilahkan, TVLdaftar, TVLbelum_punyaakun, TVLadmin, TVLmasuk_sebagaiadmin;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    Button BTNLmasuk;
    float v=0;

    DB_Helper db;
    Session session;
    SharedPreferences sharedPreferences;

    public static final String SHARED_PREF_NAME = "camppref";
    public static final String KEY_NAME         = "nama_pengguna";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db                      = new DB_Helper(this);
        session                 = new Session(this);

        // text view
        TVLhello                = findViewById(R.id.TVLhello);
        TVLsilahkan             = findViewById(R.id.TVLsilahkan);
        TVLdaftar               = findViewById(R.id.TVLdaftar);
        TVLbelum_punyaakun      = findViewById(R.id.TVLbelum_punyaakun);
        TVLmasuk_sebagaiadmin   = findViewById(R.id.TVLmasuk_sebagaiadmin);
        TVLadmin                = findViewById(R.id.TVLadmin);

        sharedPreferences       = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name             = sharedPreferences.getString(KEY_NAME,null);



        progressBar             = findViewById(R.id.progress);
        // edit text
        ETLnama_pengguna        = findViewById(R.id.ETLnama_pengguna);
        ETLkataSandi            = findViewById(R.id.ETLkata_sandi);
        // button
        BTNLmasuk               = findViewById(R.id.BTNLmasuk);
        // Progres
        progressDialog          = new ProgressDialog(this);


        // Text View
        TVLhello.setTranslationX(400);
        TVLsilahkan.setTranslationX(400);
        TVLdaftar.setTranslationX(400);
        TVLbelum_punyaakun.setTranslationX(400);
        TVLmasuk_sebagaiadmin.setTranslationX(400);
        TVLadmin.setTranslationX(400);
        // Edit Text
        ETLnama_pengguna.setTranslationX(400);
        ETLkataSandi.setTranslationX(400);
        // Button
        BTNLmasuk.setTranslationX(400);


        // Text View
        TVLhello.setAlpha(v);
        TVLsilahkan.setAlpha(v);
        TVLdaftar.setAlpha(v);
        TVLbelum_punyaakun.setAlpha(v);
        TVLmasuk_sebagaiadmin.setAlpha(v);
        TVLadmin.setAlpha(v);
        // Edit Text
        ETLnama_pengguna.setAlpha(v);
        ETLkataSandi.setAlpha(v);
        // Button
        BTNLmasuk.setAlpha(v);


        // Delay
        TVLhello.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        TVLsilahkan.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        ETLnama_pengguna.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(150).start();

        ETLkataSandi.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(175).start();

        BTNLmasuk.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();

        TVLbelum_punyaakun.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(225).start();
        TVLdaftar.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(250).start();

        TVLmasuk_sebagaiadmin.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(275).start();
        TVLadmin.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();

        TVLadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LoginAdminActivity.class);
                startActivity(intent);
            }
        });

        BTNLmasuk.setOnClickListener(this);
        TVLdaftar.setOnClickListener(this);

        if (session.loggedin()) {
            startActivity(new Intent(LoginActivity.this, BerandaActivity.class));
            finish();
        }

        // Kode Login1
//        BTNLmasuk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String nama_pengguna = ETLnama_pengguna.getText().toString();
//                String kata_sandi    = ETLkataSandi.getText().toString();
////                Intent intent = new Intent(LoginActivity.this, BerandaActivity.class);
////                startActivity(intent);
//                CheckLogin(nama_pengguna, kata_sandi);
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTNLmasuk:
                login();
                break;
            case  R.id.TVLdaftar:
                startActivity(new Intent(LoginActivity.this, DaftarActivity.class));
                break;
            default:
        }
    }

    private void login() {
        String nama_pengguna = ETLnama_pengguna.getText().toString();
        String kata_sandi    = ETLkataSandi.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME,ETLnama_pengguna.getText().toString());
        editor.apply();

        if(db.getUser(nama_pengguna,kata_sandi)) {
            session.setLoggedin(true);
            startActivity(new Intent(LoginActivity.this, BerandaActivity.class));
            finish();
        }else {
            Toast.makeText(getApplicationContext(), "Nama Pengguna dan Kata Sandi salah!", Toast.LENGTH_SHORT).show();
        }

    }

    //Kode Login1
//    public void CheckLogin(final String nama_pengguna, final String kata_sandi) {
//        if(checkNetworkConnection()) {
//            progressDialog.setMessage("Mencoba masuk...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_LOGIN_URL,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                String resp = jsonObject.getString("server_response");
//                                if (resp.equals("[{\"status\":\"Berhasil masuk!\"}]")) {
//                                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(LoginActivity.this,BerandaActivity.class);
//                                    startActivity(intent);
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Periksa kembali akun anda!", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("nama_pengguna", nama_pengguna);
//                    params.put("kata_sandi", kata_sandi);
//                    return params;
//                }
//            };
//
//            VolleyConnection.getInstance(LoginActivity.this).addToRequestQue(stringRequest);
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    progressDialog.cancel();
//                }
//            }, 1000);
//        } else {
//            Toast.makeText(getApplicationContext(), "Tidak Ada koneksi Internet", Toast.LENGTH_SHORT).show();
//        }
//    }

    //Kode Login1
//    public boolean checkNetworkConnection() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }

}