package com.example.firshermensguardian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {
private EditText Name,Password,Number,Email;
Button register;
TextView login;
private  DatabaseReference rootRef;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        login=findViewById(R.id.Rlogin);
        Name=findViewById(R.id.rName);
        Password=findViewById(R.id.rPassword);
        Number=findViewById(R.id.rNumber);
        Email=findViewById(R.id.rEmail);
        register=findViewById(R.id.rRegister);
        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<Object,String> mapPasswords=new HashMap<>();
                String emailPattern="[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
                String emailPattern2="[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
                String userName=Name.getText().toString();
                String userEmail=Email.getText().toString();
                String userPassword=Password.getText().toString();
                String userNumber=Number.getText().toString();
                if(userEmail.isEmpty()||userName.isEmpty()||userPassword.isEmpty()||userNumber.isEmpty()){
                    Toast.makeText(SignInActivity.this,"No credentials can be left empty",Toast.LENGTH_SHORT).show();
                }else if(!(userEmail.matches(emailPattern))&&!(userEmail.matches(emailPattern2))){
                    Toast.makeText(SignInActivity.this, "Enter a valid Email ID", Toast.LENGTH_SHORT).show();
                }else if(!(userNumber.length()==10)){
                    Toast.makeText(SignInActivity.this, "Enter a Valid Phone Number!", Toast.LENGTH_SHORT).show();

                }else{
                    registerUser(userName, userEmail, userPassword, userNumber);
                }
            }

            private void registerUser(String userName, String userEmail, String userPassword, String userNumber) {
                mAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        HashMap<String,Object>map=new HashMap();
                        HashMap<String,Object>map2=new HashMap<>();
                        map.put("name",userName);
                        map.put("email",userEmail);
                        map.put("number",userNumber);

                        map.put("id",mAuth.getCurrentUser().getUid());

                        map.put("imageurl","default");
                        map2.put(userName,userPassword);

                        rootRef.child("Passwords").child(mAuth.getCurrentUser().getUid()).setValue(map2);

                    rootRef.child("User").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();
                                Toast.makeText(SignInActivity.this,"Welcome to Fisherman's Guardian",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignInActivity.this, MapsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(SignInActivity.this,"Some Error Has occured!Please Try Again",Toast.LENGTH_SHORT).show();
                            Log.i("not working :-(((",e.getMessage());
                        }
                    });
                    }
                });
            }
        });
    }
}