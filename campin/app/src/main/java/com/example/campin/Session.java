package com.example.campin;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {
        this.context = context;
        prefs        = context.getSharedPreferences("campin", Context.MODE_PRIVATE);
        editor       = prefs.edit();
    }

    public void setLoggedin(boolean logggedin) {
        editor.putBoolean("loggedInmode", logggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }

}
