package com.supriya.poshinda.consumer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.supriya.poshinda.AccessStorage;
import com.supriya.poshinda.R;
import com.supriya.poshinda.Room.CartEntity;
import com.supriya.poshinda.Room.DataDAO;
import com.supriya.poshinda.Room.PoshindaDB;
import com.supriya.poshinda.Room.ProductEntity;
import com.supriya.poshinda.farmer.FarmerActivity;
import com.supriya.poshinda.farmer.FarmerHomeFragment;

import java.io.File;
import java.util.List;

public class ConsumerHomeFragment extends Fragment {

    ImageView empty_imageview;
    TextView no_data,dash,mPrice,cart;
    RecyclerView customerProductRecycler;
    DataDAO dataDAO;
    RecyclerAdapter recyclerAdapter;
    Bitmap bmImg;
    Boolean isPressed=false;
    ImageButton cartpage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_consumer_home_fragment,container,false);
        empty_imageview = view.findViewById(R.id.empty_imageview);
        no_data = view.findViewById(R.id.no_data);
        customerProductRecycler = view.findViewById(R.id.consumerProductRecyclerview);
        dash = view.findViewById(R.id.cusHomeBottom);
        mPrice = view.findViewById(R.id.cusMarketPriceBottom);
        cart = view.findViewById(R.id.cusCartBottom);
        cartpage = view.findViewById(R.id.cartPage);

        dataDAO = PoshindaDB.getInstance(getContext()).dataDAO();

        cartpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),CartActivity.class));
            }
        });

         cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),CartActivity.class));
            }
        });

        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ConsumerActivity.class));
            }
        });

        mPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://agmarknet.gov.in/");
            }
        });

      //  productDAO = PoshindaDB.getInstance(getContext()).dataDAO();
        recyclerAdapter = new RecyclerAdapter(dataDAO.getAllProduct());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        if (recyclerAdapter.getItemCount() !=0 ){
            customerProductRecycler.setLayoutManager(gridLayoutManager);
            customerProductRecycler.setAdapter(recyclerAdapter);
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }else {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ConsumerHomeFragment.RecyclerAdapter.ViewHolder>{

        List<ProductEntity> data;
        //List<CartEntity> products;

        public RecyclerAdapter(List<ProductEntity> productEntities) {
            this.data = productEntities;
        }

        @NonNull
        @Override
        public ConsumerHomeFragment.RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_design_consumer,parent,false);
            return new ConsumerHomeFragment.RecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ConsumerHomeFragment.RecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

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

            holder.itemCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean check=dataDAO.is_exist(productEntity.getProductId());
                    if (check==false){
                        int pid = productEntity.getProductId();
                        String pname = productEntity.getProductName();
                        int price = Integer.parseInt(productEntity.getProductPrice());
                        int qnt = 1;
                        File roomReturnedPath = new File(productEntity.getProductImg());
                        String path = roomReturnedPath.toString();
                        dataDAO.insertrecord(new CartEntity(pid,pname,price,qnt,path));
                        Toast.makeText(getContext(), "Added To Cart", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "Already their", Toast.LENGTH_SHORT).show();
                    }
//                    if (isPressed == true){
//                 //           holder.itemCart.setColorFilter(Color.parseColor("#165D2E"));
//                            isPressed = false;
//                            dataDAO.deleteById(productEntity.getProductId());
//                            Toast.makeText(getContext(), "Data deleted from cart", Toast.LENGTH_SHORT).show();
//                        //notifyItemRemoved(position);
//                    }else{
//                  //      holder.itemCart.setColorFilter(Color.argb(255,255,0,0));
//                        isPressed = true;
//                     //   PoshindaDB db = Room.databaseBuilder(getContext(),PoshindaDB.class,"Poshinda").allowMainThreadQueries().build();
//                        //  DataDAO dataDAO = db.dataDAO();
//                        Boolean check=dataDAO.is_exist(productEntity.getProductId());
//                        if (check==false){
//                            int pid = productEntity.getProductId();
//                            String pname = productEntity.getProductName();
//                            int price = Integer.parseInt(productEntity.getProductPrice());
//                            int qnt = 1;
//                            File roomReturnedPath = new File(productEntity.getProductImg());
//                            String path = roomReturnedPath.toString();
//                            dataDAO.insertrecord(new CartEntity(pid,pname,price,qnt,path));
//                            Toast.makeText(getContext(), "Added To Cart", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(getContext(), "Already their", Toast.LENGTH_SHORT).show();
//                        }
//                        //showBottomSheetDialog();
//                    }

                }

                private void showBottomSheetDialog() {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                    bottomSheetDialog.setContentView(R.layout.bottom_dialog_cart);

                    bottomSheetDialog.show();
                }
            });

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
            ImageButton itemCart;
            Button gotocart;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                productP = itemView.findViewById(R.id.item_location);
                productN = itemView.findViewById(R.id.item_name);
                imageViewProduct = itemView.findViewById(R.id.grid_image);
                recyclerLayout = itemView.findViewById(R.id.recyclerlayout);
                itemCart = itemView.findViewById(R.id.item_Cart);
                gotocart = itemView.findViewById(R.id.goToCartBtn);
            }
        }

    }
}

