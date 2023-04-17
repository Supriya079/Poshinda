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

public class SeasonWinterCrop extends Fragment {

    GridView gridViewWin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.season_winter_crop,container,false);
        gridViewWin = view.findViewById(R.id.gridViewWinter);

        ArrayList<modelgrid> winterCropArrayList = new ArrayList<modelgrid>();
        winterCropArrayList.add(new modelgrid("Wheat",R.drawable.wheat));
        winterCropArrayList.add(new modelgrid("Mustard",R.drawable.mustard));
        winterCropArrayList.add(new modelgrid("Almond",R.drawable.almond));
        winterCropArrayList.add(new modelgrid("Broccoli",R.drawable.broccoli));
        winterCropArrayList.add(new modelgrid("Chickpea",R.drawable.chickpea));
        winterCropArrayList.add(new modelgrid("Carrot",R.drawable.carrot));
        winterCropArrayList.add(new modelgrid("Fenugreek",R.drawable.fenugreek));
        winterCropArrayList.add(new modelgrid("Spinach",R.drawable.spinach));
        winterCropArrayList.add(new modelgrid("Radish",R.drawable.radish));
        winterCropArrayList.add(new modelgrid("Coriander",R.drawable.coriander));
        winterCropArrayList.add(new modelgrid("Green Peas",R.drawable.green_peas));
        winterCropArrayList.add(new modelgrid("Capsicum",R.drawable.capsicum));
        winterCropArrayList.add(new modelgrid("Barley",R.drawable.barley));
        winterCropArrayList.add(new modelgrid("Garlic",R.drawable.garlic));
        winterCropArrayList.add(new modelgrid("Beetroot",R.drawable.beetroot));
        winterCropArrayList.add(new modelgrid("Apple",R.drawable.apple));

        adaptergrid adapter = new adaptergrid(getContext(), winterCropArrayList);
        gridViewWin.setAdapter(adapter);

        return view;
    }

}
