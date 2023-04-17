package com.supriya.poshinda.farmer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.supriya.poshinda.R;
import com.supriya.poshinda.adaptergrid;
import com.supriya.poshinda.modelgrid;

import java.util.ArrayList;

public class FarmerTipsFragment extends Fragment {

    TextView dash,mPrice,gSchemes,fTips;
    GridView gridViewTips;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_farmer_tips_fragment,container,false);
        dash = view.findViewById(R.id.homeBottom);
        mPrice = view.findViewById(R.id.marketPriceBottom);
        gSchemes = view.findViewById(R.id.govSchemesBottom);
        fTips = view.findViewById(R.id.farmingTipsBottom);
        gridViewTips = view.findViewById(R.id.gridViewTips);

        ArrayList<modelgrid> monsoonCropArrayList = new ArrayList<modelgrid>();
        monsoonCropArrayList.add(new modelgrid("Plough",R.drawable.plough));
        monsoonCropArrayList.add(new modelgrid("Harrows",R.drawable.harrows));
        monsoonCropArrayList.add(new modelgrid("Cultivator",R.drawable.cultivator));
        monsoonCropArrayList.add(new modelgrid("Rotary Tiller",R.drawable.rotary_tiller));
        monsoonCropArrayList.add(new modelgrid("Combined Harvester",R.drawable.combined_harvester));
        monsoonCropArrayList.add(new modelgrid("Leveller",R.drawable.leveller));
        monsoonCropArrayList.add(new modelgrid("Fertilizer Broadcasters",R.drawable.fertilizerbroadcasters));
        monsoonCropArrayList.add(new modelgrid("Fertilizer Drills",R.drawable.fertilizerdrills));
        monsoonCropArrayList.add(new modelgrid("Fertilizers Sprinklers",R.drawable.fertilizerssprinklers));
        monsoonCropArrayList.add(new modelgrid("Spray Pumps",R.drawable.sprayerpump));
        monsoonCropArrayList.add(new modelgrid("Crop Rotation",R.drawable.croprotation));
        monsoonCropArrayList.add(new modelgrid("Mulching",R.drawable.mulching));
        monsoonCropArrayList.add(new modelgrid("Cover Cropping",R.drawable.covercropping));
        monsoonCropArrayList.add(new modelgrid("Cross-slope Farming",R.drawable.crossslopefarming));

        adaptergrid adapter = new adaptergrid(getContext(), monsoonCropArrayList);
        gridViewTips.setAdapter(adapter);

        gridViewTips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        gotoUrl("https://www.youtube.com/watch?v=7PekXMywZO4");
                        break;
                    case 1:
                        gotoUrl("https://www.youtube.com/watch?v=5Vqq6R5Ri8E");
                        break;
                    case 2:
                        gotoUrl("https://www.youtube.com/watch?v=aVbfMXiE748");
                        break;
                    case 3:
                        gotoUrl("https://www.youtube.com/watch?v=FWzFT0Psm7g");
                        break;
                    case 4:
                        gotoUrl("https://www.youtube.com/watch?v=ymgdUEXLcuw&t=4s");
                        break;
                    case 5:
                        gotoUrl("https://www.youtube.com/watch?v=69CGYQn2VLs");
                        break;
                    case 6:
                        gotoUrl("https://www.youtube.com/watch?v=6hzgHbbwJLc");
                        break;
                    case 7:
                        gotoUrl("https://www.youtube.com/watch?v=Wl967gPwlFA");
                        break;
                    case 8:
                        gotoUrl("https://www.youtube.com/watch?v=Z8JAG3qdarI");
                        break;
                    case 9:
                        gotoUrl("https://www.youtube.com/watch?v=xjdcjgWcGSc");
                        break;
                    case 10:
                        gotoUrl("https://www.youtube.com/watch?v=5dJI_wLEsj4");
                        break;
                    case 11:
                        gotoUrl("https://www.youtube.com/watch?v=c-Xg1vfmhKo");
                        break;
                    case 12:
                        gotoUrl("https://www.youtube.com/watch?v=Af6HIkY7IIM");
                        break;
                    case 13:
                        gotoUrl("https://www.youtube.com/watch?v=WN0zKSEKZSM");
                        break;
                }
            }
        });

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
