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

public class LoginActivity extends AppCompatActivity {

    private EditText login_email_field;
    private EditText login_password_field;
    private Button login_email_button;
    private Button login_phone_button;
    private Button login_create_button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        login_email_field = (EditText) findViewById(R.id.login_email_ID);
        login_password_field = (EditText) findViewById(R.id.login_password_ID);
        login_phone_button = (Button) findViewById(R.id.Login_phone_Btn);
        login_email_button = (Button) findViewById(R.id.login_btn);
        login_create_button = (Button) findViewById(R.id.login_create_acc_btn);
        login_phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent phoneIntent = new Intent(LoginActivity.this,LoginPhoneActivity.class);
                startActivity(phoneIntent);
                finish();

            }
        });
        login_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent createIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(createIntent);
                finish();

            }
        });
        login_email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login_email = login_email_field.getText().toString();
                String login_password = login_password_field.getText().toString();
                if(!TextUtils.isEmpty(login_email) && !TextUtils.isEmpty(login_password)){

                    mAuth.signInWithEmailAndPassword(login_email,login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Please verify your email sent to your email address before signing in", Toast.LENGTH_LONG).show();
                                }
                            }else{

                                String error = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "ERROR : " + error, Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }

            }
        });
    }
    
}