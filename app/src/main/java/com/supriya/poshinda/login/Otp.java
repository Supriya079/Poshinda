package com.supriya.poshinda.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.supriya.poshinda.R;
import com.supriya.poshinda.consumer.ConsumerActivity;
import com.supriya.poshinda.databinding.ActivityOtpBinding;
import com.supriya.poshinda.farmer.FarmerActivity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {

    Button loginWithOTP;
    ActivityOtpBinding binding;
    String verificationCode;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    String phoneNo,userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        loginWithOTP = findViewById(R.id.loginOtpPage);
        phoneNo = getIntent().getStringExtra("phoneNo");
        userType = getIntent().getStringExtra("preference");
        //String code = getIntent().getStringExtra("code");
        sendVerificationCodeToUser(phoneNo);

        loginWithOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.verificationPin.getText().toString();
                if (code.isEmpty() || code.length() <6){
                    binding.verificationPin.setError("Wrong OTP...");
                    binding.verificationPin.requestFocus();
                    return;
                }
                binding.progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });
        Objects.requireNonNull(getSupportActionBar()).hide();

    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 "+91"+ phoneNo,
                60,
                TimeUnit.SECONDS,
                this,
                mCallBacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                binding.progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Otp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,code);
        signInUser(credential);
    }

    private void signInUser(PhoneAuthCredential credential) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(Otp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // check who am i project to get login with preference
                            if (userType.equals("Farmer")){
                                Intent intent = new Intent(Otp.this, FarmerActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                Toast.makeText(Otp.this, "Account login successfully", Toast.LENGTH_SHORT).show();
                            }else if (userType.equals("Consumer")){
                                Intent intent = new Intent(Otp.this, ConsumerActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                Toast.makeText(Otp.this, "Account login successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Otp.this, "Can't Login", Toast.LENGTH_SHORT).show();
                            }
                            finish();
//                            Intent intent = new Intent(Otp.this, ConsumerActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                            Toast.makeText(Otp.this, "Account login successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}