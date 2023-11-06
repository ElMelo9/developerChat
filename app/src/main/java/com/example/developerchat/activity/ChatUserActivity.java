package com.example.developerchat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developerchat.adapter.MessagesAdapter;
import com.example.developerchat.models.Messages;
import com.example.developerchat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatUserActivity extends AppCompatActivity {

    private String name;
    private String lastName;
    private String uid;
    private String status;

    private TextView user_name;
    private TextView user_status;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    private String senderId;

    private String senderRoom;
    private String reciverRoom;

    private CardView btnSend;
    private EditText txtMensaje;

    private RecyclerView recyclerView;

    private ArrayList<Messages> messagesArrayList;

    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.green_3));
        }

        // Cambiar el color de la barra de navegación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }
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
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messagesArrayList = new ArrayList<>();
        messagesAdapter = new MessagesAdapter(ChatUserActivity.this,messagesArrayList);
        recyclerView.setAdapter(messagesAdapter);


        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        DatabaseReference reference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());


        senderId=firebaseAuth.getUid();

        senderRoom=senderId+uid;
        reciverRoom=uid+senderId;
        DatabaseReference chatReference = firebaseDatabase.getReference("Chats").child(senderRoom).child("messages");


        chatReference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    Messages messages = dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message =txtMensaje.getText().toString();


                if (message.isEmpty()) {
                    Toast.makeText(ChatUserActivity.this, "Digite un mensaje valido", Toast.LENGTH_SHORT).show();
                    return;
                }
                txtMensaje.setText("");
                Date date = new Date();
                Messages messages = new Messages(message,senderId,date.getTime());
                firebaseDatabase=FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("Chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseDatabase.getReference().child("Chats")
                                        .child(reciverRoom)
                                        .child("messages")
                                        .push()
                                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ChatUserActivity.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });

            }
        });

    }
}