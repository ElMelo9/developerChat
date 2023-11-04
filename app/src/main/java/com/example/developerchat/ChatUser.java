package com.example.developerchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatUser extends AppCompatActivity {

    private String name;
    private String lastName;
    private String uid;
    private String status;

    private TextView user_name;
    private TextView user_status;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    CardView btnSend;
    EditText txtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);

        uid=getIntent().getStringExtra("uid");
        name=getIntent().getStringExtra("name");
        lastName=getIntent().getStringExtra("lasName");
        status=getIntent().getStringExtra("status");
        user_name=findViewById(R.id.user_name);
        user_status=findViewById(R.id.user_status);
        user_name.setText(name+" "+lastName);
        user_status.setText(status);
        btnSend=findViewById(R.id.btnSend);
        txtMensaje=findViewById(R.id.txtMensaje);


        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        DatabaseReference reference = firebaseDatabase.getReference().child(firebaseAuth.getUid());

        reference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}