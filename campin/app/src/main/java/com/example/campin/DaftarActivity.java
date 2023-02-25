package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class DaftarActivity extends AppCompatActivity implements View.OnClickListener{

    EditText ETDnama_lengkap, ETDnama_pengguna, ETDnim, ETDno_handphone, ETDemail, ETDkata_sandi;
    TextView TVDhello, TVDsudah_punyaakun, TVDmasuk;
    Button BTNDaftar;
    ProgressDialog progressDialog;
    float v=0;
    SharedPreferences sharedPreferences;

    DB_Helper db;

    public static final String SHARED_PREF_NAME     = "camppref1";
    public static final String KEY_NAMA_LENGKAP     = "nama_lengkap";
    public static final String KEY_NAMA_PENGGUNA    = "nama_pengguna";
    public static final String KEY_NIM              = "nim";
    public static final String KEY_NOHP             = "nohp";
    public static final String KEY_EMAIL            = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        db = new DB_Helper(this);

        // Text View
        TVDhello            = findViewById(R.id.TVDhello);
        TVDsudah_punyaakun  = findViewById(R.id.TVDsudah_punyaakun);
        TVDmasuk            = findViewById(R.id.TVDmasuk);
        // Edit Text
        ETDnama_lengkap     = findViewById(R.id.ETDnama_lengkap);
        ETDnama_pengguna    = findViewById(R.id.ETDnama_pengguna);
        ETDnim              = findViewById(R.id.ETDnim);
        ETDno_handphone     = findViewById(R.id.ETDNo_Telepon);
        ETDemail            = findViewById(R.id.ETDemail);
        ETDkata_sandi       = findViewById(R.id.ETDkata_sandi);
        // Button
        BTNDaftar           = findViewById(R.id.BTNDaftar);
        // Progres Dialog
        progressDialog      = new ProgressDialog(this);

        sharedPreferences       = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String nama_lengkap   = sharedPreferences.getString(KEY_NAMA_LENGKAP,null);
        String nama_pengguna  = sharedPreferences.getString(KEY_NAMA_PENGGUNA,null);
        String nim            = sharedPreferences.getString(KEY_NIM,null);
        String nohp           = sharedPreferences.getString(KEY_NOHP,null);
        String em             = sharedPreferences.getString(KEY_EMAIL,null);


        // Text View
        TVDhello.setTranslationX(400);
        TVDsudah_punyaakun.setTranslationX(400);
        TVDmasuk.setTranslationX(400);
        // Edit Text
        ETDnama_lengkap.setTranslationX(400);
        ETDnama_pengguna.setTranslationX(400);
        ETDnim.setTranslationX(400);
        ETDno_handphone.setTranslationX(400);
        ETDemail.setTranslationX(400);
        ETDkata_sandi.setTranslationX(400);
        // Button
        BTNDaftar.setTranslationX(400);


        // Text View
        TVDhello.setAlpha(v);
        TVDsudah_punyaakun.setAlpha(v);
        TVDmasuk.setAlpha(v);
        // Edit Text
        ETDnama_lengkap.setAlpha(v);
        ETDnama_pengguna.setAlpha(v);
        ETDnim.setAlpha(v);
        ETDno_handphone.setAlpha(v);
        ETDemail.setAlpha(v);
        ETDkata_sandi.setAlpha(v);
        // Button
        BTNDaftar.setAlpha(v);


        // Delay
        TVDhello.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(75).start();

        ETDnama_lengkap.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        ETDnama_pengguna.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(125).start();
        ETDnim.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(150).start();
        ETDno_handphone.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(175).start();
        ETDemail.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        ETDkata_sandi.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(225).start();

        BTNDaftar.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(250).start();

        TVDsudah_punyaakun.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(275).start();
        TVDmasuk.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();

        BTNDaftar.setOnClickListener(this);
        TVDmasuk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BTNDaftar:
                register();
                break;
            case R.id.TVDmasuk:
                startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
                break;
            default:

        }
    }

    private void register() {
        String nama_pengguna = ETDnama_pengguna.getText().toString();
        String nama_lengkap  = ETDnama_lengkap.getText().toString();
        String nim           = ETDnim.getText().toString();
        String no_hp         = ETDno_handphone.getText().toString();
        String email         = ETDemail.getText().toString();
        String kata_sandi    = ETDkata_sandi.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAMA_LENGKAP,ETDnama_lengkap.getText().toString());
        editor.putString(KEY_NAMA_PENGGUNA,ETDnama_pengguna.getText().toString());
        editor.putString(KEY_NIM,ETDnim.getText().toString());
        editor.putString(KEY_NOHP,ETDno_handphone.getText().toString());
        editor.putString(KEY_EMAIL,ETDemail.getText().toString());
        editor.apply();

        if(nama_pengguna.isEmpty() && nama_lengkap.isEmpty() && nim.isEmpty() && no_hp.isEmpty() && email.isEmpty() && kata_sandi.isEmpty()) {
            displayToast("Kolom tidak boleh ada yang kosong!");
        }else {
            db.addUser(nama_lengkap,nama_pengguna,nim,no_hp,email,kata_sandi);
            displayToast("Berhasi membuat akun baru");
            finish();
        }

    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}