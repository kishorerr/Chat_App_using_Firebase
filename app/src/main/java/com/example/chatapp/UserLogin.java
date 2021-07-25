package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class UserLogin extends AppCompatActivity {
    EditText username,password;
    Button loginbtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        username=findViewById(R.id.l_username);
        password=findViewById(R.id.l_password);
        loginbtn=findViewById(R.id.login_btn);

        auth=FirebaseAuth.getInstance();

        //setting onclick listener for login button
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_name=username.getText().toString();
                String pass=password.getText().toString();
                if(TextUtils.isEmpty(u_name)||TextUtils.isEmpty(pass)) //checking whether the fields are empty or not
                    Toast.makeText(UserLogin.this,"BLANK FIELDS NOT ALLOWED",Toast.LENGTH_SHORT).show();
                else
                    login(u_name,pass); //passing the values to login function
            }
        });

    }
    private void login(String l_name,String l_pass)
    {
        //setting up sign in method using firebase authentication SDK
        auth.signInWithEmailAndPassword(l_name,l_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(UserLogin.this,MainPage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(UserLogin.this,"Invalid Credentials...LOGIN FAILED",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}