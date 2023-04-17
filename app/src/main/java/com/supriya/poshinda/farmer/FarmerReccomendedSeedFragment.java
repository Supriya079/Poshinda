package com.supriya.poshinda.farmer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.supriya.poshinda.R;

public class FarmerReccomendedSeedFragment extends Fragment {

    TextView dash,mPrice,gSchemes,fTips;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_farmer_seed_fragment,container,false);
        dash = view.findViewById(R.id.homeBottom);
        mPrice = view.findViewById(R.id.marketPriceBottom);
        gSchemes = view.findViewById(R.id.govSchemesBottom);
        fTips = view.findViewById(R.id.farmingTipsBottom);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.farmerSeedFrame,new SeasonCard());
        if (savedInstanceState == null){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.farmerSeedFrame,new SeasonCard()).commit();
        }

        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),FarmerActivity.class));
            }
        });

        mPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://agmarknet.gov.in/");
            }
        });

        gSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://agricoop.nic.in/en/Major#gsc.tab=0");
            }
        });

        fTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),FarmerActivity.class));
            }
        });

        return view;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}
