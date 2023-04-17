package com.supriya.poshinda.consumer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.supriya.poshinda.AccessStorage;
import com.supriya.poshinda.LaunchActivity;
import com.supriya.poshinda.R;
import com.supriya.poshinda.Room.CartEntity;
import com.supriya.poshinda.Room.DataDAO;
import com.supriya.poshinda.Room.PoshindaDB;
import com.supriya.poshinda.login.LoginActivity;

import java.io.File;
import java.util.List;

public class ConsumerAdapter extends RecyclerView.Adapter<ConsumerAdapter.myViewHolder>{

    List<CartEntity> cartEntityList;
    TextView rateview;
    Context context;
    Bitmap bmImg;
    int updatedSum;

    public ConsumerAdapter(List<CartEntity> cartEntityList, TextView rateview) {
        this.cartEntityList = cartEntityList;
        this.rateview = rateview;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.recid.setText(String.valueOf(cartEntityList.get(position).getPid()));
        holder.recname.setText(cartEntityList.get(position).getPname());
        holder.recprice.setText(String.valueOf(cartEntityList.get(position).getPrice()));
        holder.recqnt.setText(String.valueOf(cartEntityList.get(position).getQnt()));

        File roomReturnedPath = new File(cartEntityList.get(position).getPath());
        if (roomReturnedPath.getName().endsWith(".pdf"))
        {
            bmImg  = AccessStorage.pdfToBitmap(roomReturnedPath);
        }
        else if (roomReturnedPath.getName().endsWith(".jpg")){
            bmImg = BitmapFactory.decodeFile(cartEntityList.get(position).getPath());
        }
        holder.image.setImageBitmap(bmImg);

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                    DataDAO dataDAO = PoshindaDB.getInstance(context).dataDAO();
                    dataDAO.deleteById(cartEntityList.get(position).getPid());
                    cartEntityList.remove(position);
                    notifyItemRemoved(position);
                    updateprice();
                    Log.d("uppppp","doneeeeeeee"+cartEntityList.size());
                    notifyDataSetChanged();
            }
        });

        holder.incr.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                DataDAO dataDAO = PoshindaDB.getInstance(context).dataDAO();
                int qnt = cartEntityList.get(position).getQnt();
                qnt++;
                cartEntityList.get(position).setQnt(qnt);
                dataDAO.updateQuantityById(qnt,cartEntityList.get(position).getPid());
                notifyDataSetChanged();
                updateprice();
                dataDAO.updateById(updatedSum,cartEntityList.get(position).getPid());
            }
        });

        holder.decr.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                DataDAO dataDAO = PoshindaDB.getInstance(context).dataDAO();
                int qnt = cartEntityList.get(position).getQnt();
                qnt--;
                cartEntityList.get(position).setQnt(qnt);
                notifyDataSetChanged();
                updateprice();
                dataDAO.updateById(updatedSum,cartEntityList.get(position).getPid());
                dataDAO.updateQuantityById(qnt,cartEntityList.get(position).getPid());
                if (qnt <=0){
                    updateprice();
                    dataDAO.deleteById(cartEntityList.get(position).getPid());
                    cartEntityList.remove(position);
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartEntityList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView recid,recname,recqnt,recprice;
        ImageButton delbtn;
        ImageView incr,decr,image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            recid=itemView.findViewById(R.id.recid);
            recname=itemView.findViewById(R.id.recpname);
            recprice=itemView.findViewById(R.id.recpprice);
            recqnt=itemView.findViewById(R.id.recqnt);
            delbtn=itemView.findViewById(R.id.delbtn);
            incr = itemView.findViewById(R.id.incbtn);
            decr = itemView.findViewById(R.id.decbtn);
            image = itemView.findViewById(R.id.cusgrid_image);

        }
    }

    @SuppressLint("SetTextI18n")
    public void updateprice(){
        int sum = 0;
        int empty = 0;
        if (cartEntityList.size() == 0){
            rateview.setText("Total Amount : INR "+empty);
        }
        for (int i=0;i<cartEntityList.size();i++){
            sum+=(cartEntityList.get(i).getPrice()*cartEntityList.get(i).getQnt());
            rateview.setText("Total Amount : INR "+sum);
            Log.d("uppppp","adap sum "+sum);
            updatedSum = sum;
        }
    }

}
