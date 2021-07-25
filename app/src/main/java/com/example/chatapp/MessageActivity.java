package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapter.MessageAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    TextView textView;
    CircleImageView circleImageView;
    Intent intent;
    Button send_button;
    EditText msg_box;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    List<MessageDetails> messageDetailsList;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        textView=findViewById(R.id.user_na);
        circleImageView=findViewById(R.id.profile);
        msg_box=findViewById(R.id.inp_msg);
        send_button=findViewById(R.id.send_btn);


        recyclerView=findViewById(R.id.msgs_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent=getIntent();
        String userid=intent.getStringExtra("userid");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserDetails userDetails=snapshot.getValue(UserDetails.class);
                textView.setText(userDetails.getName());
                if(userDetails.getImageURL().equals("default"))
                    circleImageView.setImageResource(R.mipmap.cheems);
                else
                    Glide.with(MessageActivity.this).load(userDetails.getImageURL()).into(circleImageView);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=msg_box.getText().toString();
                if(!msg.equals(""))
                    chatmessage(firebaseUser.getUid(),userid,msg);
                msg_box.setText("");
            }
        });
        readmessages(firebaseUser.getUid(),userid);
    }


    private void chatmessage(String sender,String receiver,String message) {

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> msg_details= new HashMap<>();
        msg_details.put("sender",sender);
        msg_details.put("receiver",receiver);
        msg_details.put("message",message);

        reference.child("Chats").push().setValue(msg_details);
    }
    private void readmessages(String send_id,String recd_id) {
        messageDetailsList=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                messageDetailsList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    MessageDetails messageDetails=dataSnapshot.getValue(MessageDetails.class);
                    if((messageDetails.getSender().equals(send_id)&&messageDetails.getReceiver().equals(recd_id)) ||( messageDetails.getSender().equals(recd_id)&&messageDetails.getReceiver().equals(send_id)))
                    {
                        messageDetailsList.add(messageDetails);
                    }
                    messageAdapter=new MessageAdapter(getApplicationContext(),messageDetailsList);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}