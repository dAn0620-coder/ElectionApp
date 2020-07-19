package com.example.electionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginPhoneActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText phone_no;
    private EditText OTP_no;
    private Button resend_btn;
    private Button send_btn;
    private CountryCodePicker code_picker;
    String verification_ID;
    PhoneAuthProvider.ForceResendingToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        mAuth = FirebaseAuth.getInstance();
        phone_no = (EditText) findViewById(R.id.phone_number_edt);
        OTP_no = (EditText) findViewById(R.id.OTP_text);
        resend_btn = (Button) findViewById(R.id.resend_otp_btn);
        send_btn = (Button) findViewById(R.id.send_otp_btn);
        code_picker = (CountryCodePicker) findViewById(R.id.ccp);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!phone_no.getText().toString().isEmpty() && phone_no.getText().toString().length() == 10){

                    String phone_num = "+"+code_picker.getSelectedCountryCode()+phone_no.getText().toString();
                    requestOTP(phone_num);

                }else{

                    phone_no.setError("Phone Number is no valid");

                }

            }
        });
        resend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!phone_no.getText().toString().isEmpty() && phone_no.getText().toString().length() == 10){

                    String phone_num = "+"+code_picker.getSelectedCountryCode()+phone_no.getText().toString();
                    requestOTP(phone_num);

                }else{

                    phone_no.setError("Enter a valid phone number first");

                }

            }
        });
    }

    private void requestOTP(String phone_num) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone_num, 60L, TimeUnit.SECONDS, LoginPhoneActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                OTP_no.setVisibility(View.VISIBLE);
                resend_btn.setVisibility(View.VISIBLE);
                resend_btn.setEnabled(true);
                verification_ID = s;
                token = forceResendingToken;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(LoginPhoneActivity.this,"ERROR : "+ e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}