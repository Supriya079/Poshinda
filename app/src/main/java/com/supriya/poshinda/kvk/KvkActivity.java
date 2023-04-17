package com.supriya.poshinda.kvk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.supriya.poshinda.R;
import com.supriya.poshinda.Room.DataDAO;
import com.supriya.poshinda.Room.PoshindaDB;
import com.supriya.poshinda.Room.ProductEntity;
import com.supriya.poshinda.Room.SoilTestFormEntity;
import com.supriya.poshinda.farmer.FarmerHomeFragment;

import java.util.List;

public class KvkActivity extends AppCompatActivity {

    RecyclerView farmerKvkRecycler;
    DataDAO requestDAO;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kvk);

        farmerKvkRecycler = findViewById(R.id.farmerRequestRecyclerview);
        requestDAO = PoshindaDB.getInstance(this).dataDAO();
        recyclerAdapter = new RecyclerAdapter(requestDAO.getAllForm());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        if (recyclerAdapter.getItemCount() !=0 ){
            farmerKvkRecycler.setLayoutManager(gridLayoutManager);
            farmerKvkRecycler.setAdapter(recyclerAdapter);
        }else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

        List<SoilTestFormEntity> data;

        public RecyclerAdapter(List<SoilTestFormEntity> soilTestFormEntities) {
            this.data = soilTestFormEntities;
        }

        @NonNull
        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_design_form_list,parent,false);
            return new RecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
            SoilTestFormEntity soilTestFormEntity = data.get(position);
            holder.farmerN.setText(soilTestFormEntity.getFarmerName());
            holder.farmerA.setText(soilTestFormEntity.getFarmerAddress());
            holder.farmerS.setText(soilTestFormEntity.getStatus());

            holder.recyclerlayoutform.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder =  new AlertDialog.Builder(KvkActivity.this);
                    builder.setMessage(soilTestFormEntity.getFarmerRequest()).setTitle("Request")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            int id = soilTestFormEntity.getFarmerId();
                            requestDAO.updateByFarmerId("Accept",id);
                            holder.farmerS.setText("Accept");
                            Toast.makeText(getApplicationContext(), "Accept", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            int id = soilTestFormEntity.getFarmerId();
                            requestDAO.updateByFarmerId("Reject",id);
                            holder.farmerS.setText("Reject");
                            Toast.makeText(getApplicationContext(), "Reject", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout recyclerlayoutform;
            TextView farmerN,farmerA,farmerS;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                farmerN = itemView.findViewById(R.id.form_name);
                farmerA = itemView.findViewById(R.id.form_address);
                farmerS = itemView.findViewById(R.id.form_status);
                recyclerlayoutform = itemView.findViewById(R.id.recyclerlayoutform);
            }
        }
    }

}