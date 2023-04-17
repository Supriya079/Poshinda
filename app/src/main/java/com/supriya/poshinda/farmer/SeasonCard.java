package com.supriya.poshinda.farmer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.supriya.poshinda.R;

public class SeasonCard extends Fragment {

    ImageView iMonsoon,iWinter,iSummer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.season_card,container,false);

        iSummer = view.findViewById(R.id.imgSummer);
        iWinter = view.findViewById(R.id.imgWinter);
        iMonsoon = view.findViewById(R.id.imgMonsoon);

        // On Back press
        // https://stackoverflow.com/questions/5448653/how-to-implement-onbackpressed-in-fragments

        iMonsoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.farmerSeedFrame,new SeasonMonsoonCrop()).addToBackStack(SeasonCard.class.getSimpleName()).commit();
            //    Toast.makeText(getActivity(), "Monsoon Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        iWinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.farmerSeedFrame,new SeasonWinterCrop()).addToBackStack(SeasonCard.class.getSimpleName()).commit();
             //   Toast.makeText(getContext(), "Winter CLicked", Toast.LENGTH_SHORT).show();
            }
        });

        iSummer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.farmerSeedFrame,new SeasonSummerCrop()).addToBackStack(SeasonCard.class.getSimpleName()).commit();
              //  Toast.makeText(getContext(), "Summer CLicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

