package edu.northeastern.a7team45;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

public class AppUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_users);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stickchatmenu, menu);
        return true;
    }
}