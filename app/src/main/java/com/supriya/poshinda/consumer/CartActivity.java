package com.supriya.poshinda.consumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.supriya.poshinda.R;
import com.supriya.poshinda.Room.CartEntity;
import com.supriya.poshinda.Room.DataDAO;
import com.supriya.poshinda.Room.PoshindaDB;
import com.supriya.poshinda.farmer.SeasonCard;
import com.supriya.poshinda.farmer.SeasonMonsoonCrop;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    FrameLayout frameCartLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cart_consumer,new ConsumerCartFragment()).commit();

    }

}