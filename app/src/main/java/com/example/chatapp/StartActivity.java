package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button lb,rb;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rb=findViewById(R.id.reg_button);
        lb=findViewById(R.id.login_button);
        user= FirebaseAuth.getInstance().getCurrentUser();

        //checking if the user has already logged in (auto login)
       if(user!=null)
        {
            Intent i =new Intent(StartActivity.this,MainPage.class);
            startActivity(i);
            finish();
        }

       //setting onclick listener for register button
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(StartActivity.this,UserRegister.class);
                startActivity(intent);
            }
        });


       //setting onclick listener for login button
        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(StartActivity.this,UserLogin.class);
                startActivity(intent1);
            }
        });
    }
}