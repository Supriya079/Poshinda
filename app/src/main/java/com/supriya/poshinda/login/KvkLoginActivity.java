package com.supriya.poshinda.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.supriya.poshinda.R;
import com.supriya.poshinda.kvk.KvkActivity;

public class KvkLoginActivity extends AppCompatActivity {

    EditText id;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kvk_login);

        id = findViewById(R.id.KvkId);
        btnLogin = findViewById(R.id.KvkBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int input = Integer.parseInt(id.getText().toString());
                if (input == 123){
                    startActivity(new Intent(KvkLoginActivity.this, KvkActivity.class));
                }else {
                    Toast.makeText(KvkLoginActivity.this, "Wrong Id", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}