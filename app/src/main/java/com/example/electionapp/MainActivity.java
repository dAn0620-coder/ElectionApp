package com.example.electionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            Intent signinIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(signinIntent);
            finish();
        }else{
            Toast.makeText(MainActivity.this,"User logged in", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            Intent signinIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(signinIntent);
            finish();
        }else{
            Toast.makeText(MainActivity.this,"User logged in", Toast.LENGTH_LONG).show();
        }

    }
}