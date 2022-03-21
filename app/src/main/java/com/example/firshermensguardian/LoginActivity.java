
package com.example.firshermensguardian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView register;
EditText Email,password;
FirebaseAuth mAtuth;
Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email=findViewById(R.id.logemail);
        password=findViewById(R.id.logpassword);
        register=findViewById(R.id.resgister_text);
   login=findViewById(R.id.LoginButton);
   mAtuth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String emailText=Email.getText().toString();
             String passwordText=password.getText().toString();
             if(passwordText.isEmpty()||emailText.isEmpty()){
                 Toast.makeText(LoginActivity.this,"No credentials can be left empty",Toast.LENGTH_SHORT).show();
             }else if(passwordText.length()<6){
                 Toast.makeText(LoginActivity.this,"Password must contain minimum 6 characters",Toast.LENGTH_SHORT).show();
             }else{
                 loginUser(emailText,passwordText);
                 InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                 mgr.hideSoftInputFromWindow(login.getWindowToken(), 0);
             }
            }
        });

    }
    private void loginUser(String email,String password){
        mAtuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 Intent intent;
                if(task.isSuccessful()){

                    Toast.makeText(LoginActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();
                intent=new Intent(LoginActivity.this,MapsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(new Intent(LoginActivity.this,MapsActivity.class));
                intent.putExtra("activity","working-fine");
                finish();


                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e!=null) {
                    Toast.makeText(LoginActivity.this, "Some Error has occured!Check your ID and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}