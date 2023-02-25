package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DaftarAdminActivity extends AppCompatActivity {

    EditText ETDnama_admin, ETDid_admin, ETDno_handphone, ETDemail, ETDkata_sandi;
    TextView TVDhello, TVDsudah_punyaakun, TVDmasuk;
    Button BTNDaftar;
    ProgressDialog progressDialog;
    float v=0;
    Bitmap bitMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_admin);

        // Text View
        TVDhello = findViewById(R.id.TVDhello);
        TVDsudah_punyaakun = findViewById(R.id.TVDsudah_punyaakun);
        TVDmasuk = findViewById(R.id.TVDmasuk);
        // Edit Text
        ETDnama_admin = findViewById(R.id.ETDnama_admin);
        ETDid_admin = findViewById(R.id.ETDid_admin);
        ETDno_handphone = findViewById(R.id.ETDNo_Telepon_Admin);
        ETDemail = findViewById(R.id.ETDemail_admin);
        ETDkata_sandi = findViewById(R.id.ETDkata_sandi_admin);
        // Button
        BTNDaftar = findViewById(R.id.BTNDaftar_Admin);
        // Progres Dialog
        progressDialog = new ProgressDialog(this);


        // Text View
        TVDhello.setTranslationX(400);
        TVDsudah_punyaakun.setTranslationX(400);
        TVDmasuk.setTranslationX(400);
        // Edit Text
        ETDnama_admin.setTranslationX(400);
        ETDid_admin.setTranslationX(400);
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
        ETDnama_admin.setAlpha(v);
        ETDid_admin.setAlpha(v);
        ETDno_handphone.setAlpha(v);
        ETDemail.setAlpha(v);
        ETDkata_sandi.setAlpha(v);
        // Button
        BTNDaftar.setAlpha(v);


        // Delay
        TVDhello.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(75).start();

        ETDnama_admin.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        ETDid_admin.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(125).start();
        ETDno_handphone.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(150).start();
        ETDemail.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(175).start();
        ETDkata_sandi.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();

        BTNDaftar.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(225).start();

        TVDsudah_punyaakun.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(250).start();
        TVDmasuk.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(275).start();
    }
}