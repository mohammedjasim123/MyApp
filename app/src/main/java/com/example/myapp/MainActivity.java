package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;
//import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    EditText name,email,password;
    TextView name_, email_, password_;
    Button add,view1;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.txt_name);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_pwd);
        add = findViewById(R.id.btn_add);
        view1 = findViewById(R.id.btnfetch);
        name_ = findViewById(R.id.tv_name);
        email_ = findViewById(R.id.tv_email);
        password_ = findViewById(R.id.tv_password);
        firestore=FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                Map<String,Object> Users= new HashMap<>();
                Users.put("Name",Name);
                Users.put("Email",Email);
                Users.put("Password",Password);

                firestore.collection("User").add(Users)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this, "Details stored successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Details cannot be stored", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference reference = firestore.collection("User").document("36xO0YxXQGw6WWqrBeCg");
                reference.addSnapshotListener(MainActivity.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        name_.setText(value.getString("Name"));
                        email_.setText(value.getString("Email"));
                        //password_.setText(value.getString("Password"));
                    }
                });
            }
        });
    }
}