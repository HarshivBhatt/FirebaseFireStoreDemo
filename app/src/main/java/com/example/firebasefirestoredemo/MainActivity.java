package com.example.firebasefirestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText e1;
    Button b1, buttonget;
    FirebaseFirestore firestore;
    TextView tex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1=findViewById(R.id.editText);
        b1=findViewById(R.id.button);
        buttonget=findViewById(R.id.buttonget);
        firestore=FirebaseFirestore.getInstance();

        tex=findViewById(R.id.t);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionReference cr=firestore.collection("employees");
                String name=e1.getText().toString();
                EmployeeData ed=new EmployeeData(name);

                cr.add(ed).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this,"added suuccessfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this,"sorry my friend!!!",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        buttonget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore.collection("employees").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(!queryDocumentSnapshots.isEmpty())
                        {
                            List<DocumentSnapshot > employeedata=queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d: employeedata)
                            {
                                EmployeeData e=d.toObject(EmployeeData.class);
                                String name=e.getName();
                                tex.setText(name);
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"data nathi ",Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });

    }
}
