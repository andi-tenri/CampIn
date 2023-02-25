package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginAdminActivity extends AppCompatActivity {

    EditText ETLnama_admin, ETLkata_sandi_admin;
    ImageView ic_kembali_admin;
    TextView TVLhello, TVLsilahkan,TVLUser, TVLmasuk_sebagaiuser;
    ProgressDialog progressDialog;
    Button BTNLmasuk;
    float v=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        ic_kembali_admin = findViewById(R.id.ic_kembali_admin);
        // text view
        TVLhello                = findViewById(R.id.TVLhello);
        TVLsilahkan             = findViewById(R.id.TVLsilahkan);
        TVLmasuk_sebagaiuser    = findViewById(R.id.TVLmasuk_sebagaiuser);
        TVLUser                 = findViewById(R.id.TVLUser);
        // edit text
        ETLnama_admin           = findViewById(R.id.ETLnama_admin);
        ETLkata_sandi_admin = findViewById(R.id.ETLkata_sandi_admin);
        // button
        BTNLmasuk               = findViewById(R.id.BTNLmasuk_admin);
        // Progres Dialog
        progressDialog          = new ProgressDialog(this);


        // Text View
        TVLhello.setTranslationX(400);
        TVLsilahkan.setTranslationX(400);
        TVLmasuk_sebagaiuser.setTranslationX(400);
        TVLUser.setTranslationX(400);
        // Edit Text
        ETLnama_admin.setTranslationX(400);
        ETLkata_sandi_admin.setTranslationX(400);
        // Button
        BTNLmasuk.setTranslationX(400);


        // Text View
        TVLhello.setAlpha(v);
        TVLsilahkan.setAlpha(v);
        TVLmasuk_sebagaiuser.setAlpha(v);
        TVLUser.setAlpha(v);
        // Edit Text
        ETLnama_admin.setAlpha(v);
        ETLkata_sandi_admin.setAlpha(v);
        // Button
        BTNLmasuk.setAlpha(v);


        // Delay
        TVLhello.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        TVLsilahkan.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        ETLnama_admin.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(150).start();

        ETLkata_sandi_admin.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(175).start();

        BTNLmasuk.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();

        TVLmasuk_sebagaiuser.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(225).start();
        TVLUser.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(250).start();

        ic_kembali_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        TVLUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        BTNLmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama         = ETLnama_admin.getText().toString();
                String kata_sandi   = ETLkata_sandi_admin.getText().toString();

                CheckLogin(nama, kata_sandi);

            }
        });

    }

    public void CheckLogin(final String nama, final String kata_sandi) {
        if(checkNetworkConnection()) {
            progressDialog.setMessage("Mencoba masuk...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_LOGIN_ADMIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"Berhasil masuk!\"}]")) {
                                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginAdminActivity.this,BerandaAdminActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Periksa kembali akun anda!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nama", nama);
                    params.put("kata_sandi", kata_sandi);
                    return params;
                }
            };

            VolleyConnection.getInstance(LoginAdminActivity.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 1000);
        } else {
            Toast.makeText(getApplicationContext(), "Tidak Ada koneksi Internet", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}