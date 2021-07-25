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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class UserRegister extends AppCompatActivity {

    EditText username,email,password;
    Button regbtn;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        regbtn=findViewById(R.id.reg_btn);
        auth= FirebaseAuth.getInstance();

        //setting onclick listener for register button
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_name=username.getText().toString();
                String e_mail=email.getText().toString();
                String pass=password.getText().toString();
                if(TextUtils.isEmpty(u_name)||TextUtils.isEmpty(e_mail)||TextUtils.isEmpty(pass))//checking whether the fields are empty or not
                    Toast.makeText(UserRegister.this,"BLANK FIELDS NOT ALLOWED",Toast.LENGTH_SHORT).show();
                else if(pass.length()<6)
                    Toast.makeText(UserRegister.this,"MINIMUM 6 LETTERS REQUIRED FOR PASSWORD",Toast.LENGTH_SHORT).show();
                else
                    register(u_name,e_mail,pass);//passing the values into register function
            }
        });

    }

    private void register(String username,String email, String password)
    {
        //creating new user using firebase authentication SDK
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser= auth.getCurrentUser();//getting currentUser
                            assert firebaseUser != null;
                            String user_id= firebaseUser.getUid();//getting user id
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(user_id);//getting the reference of the database of the respective user id
                            HashMap<String,String>hashMap=new HashMap<>();//using hashmap to assign id,name and profilepic values
                            hashMap.put("id",user_id);
                            hashMap.put("name",username);
                            hashMap.put("imageURL","default");

                            //setting the values from user into reference database of the user
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Intent intent = new Intent(UserRegister.this,StartActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(UserRegister.this,"Account Already Exists!",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(UserRegister.this,"Registration Unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}