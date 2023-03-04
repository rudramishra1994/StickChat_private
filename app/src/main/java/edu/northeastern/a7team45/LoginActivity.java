package edu.northeastern.a7team45;

import static edu.northeastern.a7team45.R.id.username;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickershare);

        Button login = findViewById(R.id.loginbutton);
        TextInputLayout usernameLayout = findViewById(R.id.usernamelayout);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameLayout.getEditText().getText().toString();
                if(username.isBlank()){
                    Toast toast = Toast.makeText(LoginActivity.this, "Username cannot be empty", Toast.LENGTH_LONG);
                }else {
                    Intent serviceIntent = new Intent(LoginActivity.this, StickChatHome.class);
                    serviceIntent.putExtra("username",username);
                    startActivity(serviceIntent);
                }
            }
        });

    }
}