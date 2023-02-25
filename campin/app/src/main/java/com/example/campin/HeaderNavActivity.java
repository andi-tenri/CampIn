package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class HeaderNavActivity extends AppCompatActivity {

    TextView TVNamaLengkap,TVNIM;
    String namalengkap,nim;

    Session session;
    SharedPreferences sharedPreferences;

    public static final String SHARED_PREF_NAME   = "camppref1";
    public static final String KEY_NAMA_LENGKAP   = "nama_lengkap";
    public static final String KEY_NIM            = "nim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_nav);

        TVNamaLengkap       = findViewById(R.id.nama_navmenu);
        TVNIM               = findViewById(R.id.nim_navmenu);

        sharedPreferences   = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        namalengkap         = sharedPreferences.getString(KEY_NAMA_LENGKAP, null);
        nim                 = sharedPreferences.getString(KEY_NIM, null);

        TVNamaLengkap.setText(namalengkap);
        TVNIM.setText(nim);
    }
}