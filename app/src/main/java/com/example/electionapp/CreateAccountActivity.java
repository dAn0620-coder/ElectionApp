package com.example.electionapp;

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

public class CreateAccountActivity extends AppCompatActivity {

    private EditText reg_email;
    private EditText reg_password;
    private EditText reg_confirm_password;
    private Button reg_btn;
    private Button reg_login_btn;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
        reg_email = (EditText) findViewById(R.id.reg_email_field);
        reg_password = (EditText) findViewById(R.id.reg_password_field);
        reg_confirm_password = (EditText) findViewById(R.id.reg_confirm_password_field);
        reg_btn = (Button) findViewById(R.id.create_acc_btn);
        reg_login_btn = (Button) findViewById(R.id.reg_login_btn);
        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = reg_email.getText().toString();
                String password = reg_password.getText().toString();
                String confirm_password = reg_confirm_password.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirm_password)){

                    if (password.equals(confirm_password)){

                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){

                                    firebaseUser = mAuth.getCurrentUser();
                                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                Toast.makeText(CreateAccountActivity.this,"Please Verify Email sent to the email address to proceed furthur", Toast.LENGTH_LONG).show();

                                            }

                                        }
                                    });
                                    Intent mainIntent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                    startActivity(mainIntent);
                                    finish();

                                }else{

                                    String error = task.getException().getMessage();
                                    Toast.makeText(CreateAccountActivity.this,"ERROR : "+error, Toast.LENGTH_LONG).show();

                                }

                            }
                        });

                    }else{

                        Toast.makeText(CreateAccountActivity.this, "ERROR : Confirm password does not match", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

    }
}