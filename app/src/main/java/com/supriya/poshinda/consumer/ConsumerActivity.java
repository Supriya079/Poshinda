package com.supriya.poshinda.consumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.supriya.poshinda.R;
import com.supriya.poshinda.farmer.FarmerActivity;
import com.supriya.poshinda.login.LoginActivity;

public class ConsumerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog dialog;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);

        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(ConsumerActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...");

        NavigationView navigationView = findViewById(R.id.navigation_view_consumer);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_consumer,new ConsumerHomeFragment());
        mDrawerLayout = findViewById(R.id.consumerDrawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.start,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_consumer,new ConsumerHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.cushome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_consumer,new ConsumerHomeFragment()).commit();
                break;
            case R.id.cusMarketPrice:
                gotoUrl("https://agmarknet.gov.in/");
                break;
            case R.id.cusLogout:
                auth.signOut();
                startActivity(new Intent(ConsumerActivity.this, LoginActivity.class));
                finish();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


}