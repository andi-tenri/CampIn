package com.example.campin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class InfoAdminActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    LinearLayout keluar;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_admin);

        session             = new Session(this);

        keluar       = findViewById(R.id.linearLayoutadmin);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout1);

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

        redirectActivity(this,BerandaAdminActivity.class);

    }

    public void Clickinfo(View view) {

        recreate();

    }

    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(InfoAdminActivity.this, LoginActivity.class));
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