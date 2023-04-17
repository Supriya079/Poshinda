package com.supriya.poshinda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.supriya.poshinda.consumer.ConsumerActivity;
import com.supriya.poshinda.consumer.ConsumerCartFragment;
import com.supriya.poshinda.login.LoginActivity;
import com.supriya.poshinda.login.Otp;

import java.util.HashMap;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private static long maxId=0;
    TextView signintxt;
    AutoCompleteTextView autoCompleteTextViewUsertype;
    private String nameStr, emailStr, mobileStr,user;
    ProgressDialog dialog;
    private String deviceId;
    EditText name, email, mobile;
    Button register;
    FirebaseAuth auth;
    DatabaseReference reference;
    String[] usertype = new String[]{"Farmer", "Consumer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signintxt = findViewById(R.id.signintxt);
        autoCompleteTextViewUsertype = findViewById(R.id.AutoCompleteTextview);
        register = findViewById(R.id.buttonRegister);
        deviceId = Settings.Secure.getString(RegistrationActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        auth = FirebaseAuth.getInstance();
        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        mobile = findViewById(R.id.editTextMobile);
        dialog = new ProgressDialog(RegistrationActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading.....");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, usertype);
        autoCompleteTextViewUsertype.setAdapter(adapter1);

        reference  = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    RegistrationActivity.maxId = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        signintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatePhoneNo()) {
                    mobile.setError("Verify Mobile No");
                }
                if (mobile.length() > 10) {
                    mobile.setError("Mobile No is Invalid");
                }
                registerUser();
            }
        });

        Objects.requireNonNull(getSupportActionBar()).hide();

    }

    private void registerUser() {

        reference  = FirebaseDatabase.getInstance().getReference().child("Users");

        if (!validateEmail() || !validatePhoneNo()){
            return;
        }
        nameStr = name.getEditableText().toString();
        emailStr = email.getEditableText().toString();
        mobileStr = mobile.getText().toString();
        user = autoCompleteTextViewUsertype.getText().toString();

        HashMap<String, String> map = new HashMap<>();
        map.put("Email",emailStr);
        map.put("Name",nameStr);
        map.put("Phone",mobileStr);
        map.put("User",user);

        reference.child(String.valueOf(RegistrationActivity.maxId+1)).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (validate()){
                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    private boolean validate() {
        if (mobileStr.length() < 10 || mobileStr.length() > 10){
            Toast.makeText(RegistrationActivity.this, "Check Mobile Number", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private Boolean validateEmail() {
        String val = email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Field Cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("Invalid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = mobile.getText().toString();
        if (val.isEmpty()) {
            mobile.setError("Field cannot be empty");
            return false;
        }
        else {
            mobile.setError(null);
            return true;
        }
    }

}