package com.example.ugaprint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ugaprint.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText name, address;
    Button submit;
    Context context;
    public static final String USERS_COLLECTION = "USERS";
    UserModel new_user = new UserModel();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Bind_Views();
    }

    private void Bind_Views() {
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new_user.Name = name.getText().toString();
                if (new_user.Name.isEmpty()) {

                    Toast.makeText(context, "Name is too Short", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                    return;
                }

                new_user.Address = name.getText().toString();
                if (new_user.Address.isEmpty()) {

                    Toast.makeText(context, "Address is too Short", Toast.LENGTH_SHORT).show();
                    address.requestFocus();
                    return;
                }
                new_user.id = db.collection(USERS_COLLECTION).document().getId();
                progressDialog.setTitle("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
                db.collection(USERS_COLLECTION).document().set(new_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "sucess", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        return;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                        return;
                      //  Log.d(TAG,"OnFailure: failed because ==>" + e.getMessage());
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.hide();

                    }
                });
            }
        });
    }
}

