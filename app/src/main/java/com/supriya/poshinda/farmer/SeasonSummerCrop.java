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

public class SeasonSummerCrop extends Fragment {

    GridView gridViewSum;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.season_summer_crop,container,false);
        gridViewSum = view.findViewById(R.id.gridViewSummer);

        ArrayList<modelgrid> summerCropArrayList = new ArrayList<modelgrid>();
        summerCropArrayList.add(new modelgrid("Watermelon",R.drawable.watermelon));
        summerCropArrayList.add(new modelgrid("Muskmelon",R.drawable.muskmelon));
        summerCropArrayList.add(new modelgrid("Cucumber",R.drawable.cucumber));
        summerCropArrayList.add(new modelgrid("Bitter Gourd",R.drawable.bitter_gourd));
        summerCropArrayList.add(new modelgrid("Groundnut",R.drawable.groundnut));
        summerCropArrayList.add(new modelgrid("Pumpkin",R.drawable.pumpkin));
        summerCropArrayList.add(new modelgrid("Cowpea (Lobia)",R.drawable.cowpea_lobia));
        summerCropArrayList.add(new modelgrid("Grapes",R.drawable.grapes));
        summerCropArrayList.add(new modelgrid("Avocado",R.drawable.avocado));
        summerCropArrayList.add(new modelgrid("Lady Finger",R.drawable.lady_finger));
        summerCropArrayList.add(new modelgrid("Lettuce",R.drawable.lettuce));
        summerCropArrayList.add(new modelgrid("Sweet Potato",R.drawable.sweet_potato));
        summerCropArrayList.add(new modelgrid("Mango",R.drawable.mango));
        summerCropArrayList.add(new modelgrid("Tinda",R.drawable.tinda));
        summerCropArrayList.add(new modelgrid("Long Beans",R.drawable.long_beans));
        summerCropArrayList.add(new modelgrid("Guava",R.drawable.guava));

        adaptergrid adapter = new adaptergrid(getContext(), summerCropArrayList);
        gridViewSum.setAdapter(adapter);

        return view;
    }

}
