package com.example.developerchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Register_activity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtLastName;
    private Button btnSave;

    private String name;
    private String email;
    private String password;
    private String lastName;

    private String uid;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtLastName = findViewById(R.id.txtLastName);
        btnSave = findViewById(R.id.btnSave);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReferences = FirebaseDatabase.getInstance().getReference();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = txtName.getText().toString();
                email = txtEmail.getText().toString();
                password =txtPassword.getText().toString();
                lastName = txtLastName.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !lastName.isEmpty()) {

                    if (password.length() > 0) {

                        registerUser();
                    }else{

                        Toast.makeText(Register_activity.this, "la contraseña debe terner minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(Register_activity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void registerUser() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uid = firebaseAuth.getCurrentUser().getUid();

                            Map<String,Object> map = new HashMap<>();
                            map.put("name",name);
                            map.put("lastName",lastName);
                            map.put("email",email);
                            map.put("password",password);
                            map.put("status","hey ahora estoy usando developerChat!");
                            map.put("uid",uid);


                            databaseReferences.child("Users").child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@androidx.annotation.NonNull Task<Void> task2) {
                                    if (task2.isSuccessful()) {
                                    startActivity(new Intent(Register_activity.this,dashboardMainActivity.class));
                                    finish();
                                    }else{
                                        Toast.makeText(Register_activity.this, "No se pudieron registrar los datos",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register_activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}