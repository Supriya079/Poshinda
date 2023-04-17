package com.supriya.poshinda.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.supriya.poshinda.R;
import com.supriya.poshinda.RegistrationActivity;
import com.supriya.poshinda.kvk.KvkActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextView signuptxt;
    ImageView admin;
    Button getOtp;
    EditText mobile;
    CountryCodePicker countryCodePicker;
    String number, countryCP, userT;
    AutoCompleteTextView autoCompleteTextView;
    String[] usertype = new String[]{"Farmer", "Consumer"};
    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signuptxt = findViewById(R.id.signuptxt);
        admin = findViewById(R.id.adminKey);
        getOtp = findViewById(R.id.buttonGetOTP);
        mobile = findViewById(R.id.mobileNumber);
        countryCodePicker = findViewById(R.id.ccp);
        autoCompleteTextView = findViewById(R.id.AutoCompleteTextviewL);
        textInputLayout  = findViewById(R.id.TextInputLayoutL);

        //KVK
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, KvkLoginActivity.class));
            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, usertype);
        autoCompleteTextView.setAdapter(adapter1);

        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( mobile.getText() == null){
                    mobile.setError("Compulsory Field");
                }else{
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                    userRef.orderByChild("Phone")
                            .equalTo(mobile.getText().toString())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.getValue() != null){
                                        number = mobile.getText().toString();
                                        userT = autoCompleteTextView.getText().toString();
                                        countryCP = countryCodePicker.getSelectedCountryCodeWithPlus();
                                        Toast.makeText(LoginActivity.this, countryCP, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this,Otp.class);
                                        intent.putExtra("phoneNo",number);
                                        intent.putExtra("preference",userT);
                                        //intent.putExtra("code",countryCP);
                                        startActivity(intent);

//                                        Intent i = new Intent(LoginActivity.this, ConsumerCartFragment.class);
//                                        i.putExtra("pn",number);
//                                        startActivity(intent);

                                    }else {
                                        Toast.makeText(LoginActivity.this, "Register First...", Toast.LENGTH_LONG).show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }
        });

        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        Objects.requireNonNull(getSupportActionBar()).hide();

    }
}