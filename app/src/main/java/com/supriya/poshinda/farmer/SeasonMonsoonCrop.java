package com.supriya.poshinda.farmer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.supriya.poshinda.R;
import com.supriya.poshinda.adaptergrid;
import com.supriya.poshinda.modelgrid;

import java.util.ArrayList;

public class SeasonMonsoonCrop extends Fragment {

    GridView gridViewMon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.season_monsoon_crop,container,false);
        gridViewMon = view.findViewById(R.id.gridViewMonsoon);

        ArrayList<modelgrid> monsoonCropArrayList = new ArrayList<modelgrid>();
        monsoonCropArrayList.add(new modelgrid("Rice",R.drawable.rice));
        monsoonCropArrayList.add(new modelgrid("Maize",R.drawable.maize));
        monsoonCropArrayList.add(new modelgrid("Jowar",R.drawable.jowar));
        monsoonCropArrayList.add(new modelgrid("Bajra",R.drawable.bajra));
        monsoonCropArrayList.add(new modelgrid("Cotton",R.drawable.cotton));
        monsoonCropArrayList.add(new modelgrid("Sugar Cane",R.drawable.sugarcane));
        monsoonCropArrayList.add(new modelgrid("Pomegranate",R.drawable.pomegranate));
        monsoonCropArrayList.add(new modelgrid("Pears",R.drawable.pears));
        monsoonCropArrayList.add(new modelgrid("Cherry",R.drawable.cherry));
        monsoonCropArrayList.add(new modelgrid("Cauliflower",R.drawable.cauliflower));
        monsoonCropArrayList.add(new modelgrid("Green Chilli",R.drawable.green_chilli));
        monsoonCropArrayList.add(new modelgrid("Soyabean",R.drawable.soyabean));
        monsoonCropArrayList.add(new modelgrid("Brinjal",R.drawable.brinjal));
        monsoonCropArrayList.add(new modelgrid("Tomato",R.drawable.tomato));
        monsoonCropArrayList.add(new modelgrid("Jute",R.drawable.jute));
        monsoonCropArrayList.add(new modelgrid("Custard Apple",R.drawable.custard_apple));

        adaptergrid adapter = new adaptergrid(getContext(), monsoonCropArrayList);
        gridViewMon.setAdapter(adapter);

        return view;
    }

}
