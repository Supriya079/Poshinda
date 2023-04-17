package com.supriya.poshinda.farmer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.supriya.poshinda.AccessStorage;
import com.supriya.poshinda.R;
import com.supriya.poshinda.Room.DataDAO;
import com.supriya.poshinda.Room.PoshindaDB;
import com.supriya.poshinda.Room.ProductEntity;

import java.io.File;
import java.util.List;

public class FarmerHomeFragment extends Fragment {

    ImageView empty_imageview;
    TextView no_data, dash,mPrice,gSchemes,fTips;
    RecyclerView farmerProductRecycler;
    FloatingActionButton fabAddProduct;
    DataDAO productDAO;
    RecyclerAdapter recyclerAdapter;
    Bitmap bmImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_farmer_home_fragment,container,false);
        empty_imageview = view.findViewById(R.id.empty_imageview);
        no_data = view.findViewById(R.id.no_data);
        farmerProductRecycler = view.findViewById(R.id.farmerProductRecyclerview);
        fabAddProduct = view.findViewById(R.id.fabAddProductFarmer);
        dash = view.findViewById(R.id.homeBottom);
        mPrice = view.findViewById(R.id.marketPriceBottom);
        gSchemes = view.findViewById(R.id.govSchemesBottom);
        fTips = view.findViewById(R.id.farmingTipsBottom);

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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_farmer, new FarmerTipsFragment()).commit();
//                Intent intent = new Intent(getActivity(),FarmerTipsFragment.class);
//                getActivity().startActivity(intent);
            }
        });

        productDAO = PoshindaDB.getInstance(getContext()).dataDAO();
        recyclerAdapter = new RecyclerAdapter(productDAO.getAllProduct());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        if (recyclerAdapter.getItemCount() !=0 ){
            farmerProductRecycler.setLayoutManager(gridLayoutManager);
            farmerProductRecycler.setAdapter(recyclerAdapter);
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }else {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }

        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Product.class));
            }
        });

        return view;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

        List<ProductEntity> data;

        public RecyclerAdapter(List<ProductEntity> productEntities) {
            this.data = productEntities;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_design,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            ProductEntity productEntity = data.get(position);
            holder.productN.setText(productEntity.getProductName());
            holder.productP.setText(productEntity.getProductPrice()+" Rs");
            File roomReturnedPath = new File(productEntity.getProductImg());
            if (roomReturnedPath.getName().endsWith(".pdf"))
            {
                bmImg  = AccessStorage.pdfToBitmap(roomReturnedPath);
            }
            else if (roomReturnedPath.getName().endsWith(".jpg")){
                bmImg = BitmapFactory.decodeFile(productEntity.getProductImg());
            }
            holder.imageViewProduct.setImageBitmap(bmImg);

            holder.recyclerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(),DetailsActivity.class);
//                    intent.putExtra("name", billEntity.getObject_Name());
//                    intent.putExtra("location", billEntity.getLocation());
//                    intent.putExtra("image", billEntity.getImagePath());
//                    intent.putExtra("description",billEntity.getDescription());
//                    intent.putExtra("dob",billEntity.getDate().toString());
//                    startActivity(intent);
                    Toast.makeText(getContext(), "Data clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            LinearLayout recyclerLayout;
            ImageView imageViewProduct;
            TextView productN,productP;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productP = itemView.findViewById(R.id.item_location);
                productN = itemView.findViewById(R.id.item_name);
                imageViewProduct = itemView.findViewById(R.id.grid_image);
                recyclerLayout = itemView.findViewById(R.id.recyclerlayout);
            }
        }

    }}

