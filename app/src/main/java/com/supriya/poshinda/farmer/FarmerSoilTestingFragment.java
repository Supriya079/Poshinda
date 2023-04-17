package com.supriya.poshinda.farmer;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.supriya.poshinda.R;
import com.supriya.poshinda.Room.DataDAO;
import com.supriya.poshinda.Room.PoshindaDB;
import com.supriya.poshinda.Room.ProductEntity;
import com.supriya.poshinda.Room.SoilTestFormEntity;

public class FarmerSoilTestingFragment extends Fragment {

    Button saveSoilTestingReq;
    DataDAO soilFormDAO;
    EditText fName,fAddress,fRequest;
    View view;
    TextView dash,mPrice,gSchemes,fTips;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_farmer_soiltesting_fragment,container,false);
        saveSoilTestingReq = view.findViewById(R.id.submitSoilForm);
        fName = view.findViewById(R.id.editNameSoilTest);
        fAddress = view.findViewById(R.id.editAddSoilTest);
        fRequest = view.findViewById(R.id.editRequestSoilTest);
        dash = view.findViewById(R.id.homeBottom);
        mPrice = view.findViewById(R.id.marketPriceBottom);
        gSchemes = view.findViewById(R.id.govSchemesBottom);
        fTips = view.findViewById(R.id.farmingTipsBottom);
        soilFormDAO = PoshindaDB.getInstance(getContext()).dataDAO();

        saveSoilTestingReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fName.getText().toString().isEmpty() || fAddress.getText().toString().isEmpty()
                        || fRequest.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),
                            "Data missing",
                            Toast.LENGTH_SHORT).show();
                }else {
                    SoilTestFormEntity soilTestFormEntity = new SoilTestFormEntity();
                    soilTestFormEntity.setFarmerName(fName.getText().toString());
                    soilTestFormEntity.setFarmerAddress(fAddress.getText().toString());
                    soilTestFormEntity.setFarmerRequest(fRequest.getText().toString());
                    soilTestFormEntity.setStatus("Not Checked");
                    soilFormDAO.insertFormData(soilTestFormEntity);
                    setViewLayout(R.layout.soilformsubmitted_layout);
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

    private void setViewLayout(int id){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}
