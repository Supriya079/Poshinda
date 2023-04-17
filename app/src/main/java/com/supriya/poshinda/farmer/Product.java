package com.supriya.poshinda.farmer;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.supriya.poshinda.AccessStorage;
import com.supriya.poshinda.R;
import com.supriya.poshinda.Room.DataDAO;
import com.supriya.poshinda.Room.PoshindaDB;
import com.supriya.poshinda.Room.ProductEntity;

import java.io.IOException;

public class Product extends AppCompatActivity {

    EditText productName,productPrice,productImg;
    Button btnSaveProduct;
    DataDAO dao;
    Dialog dialogM;
    Uri uri = null,intentCameraUri=null;
    private static final int CAMERA = 9;
    private static final int GALLERY = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productImg = findViewById(R.id.editProductImg);
        productName = findViewById(R.id.editProductName);
        productPrice = findViewById(R.id.editProductPrice);
        btnSaveProduct = findViewById(R.id.productBtn);

        dao = PoshindaDB.getInstance(getApplicationContext()).dataDAO();

        btnSaveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productName.getText().toString().isEmpty() || productPrice.getText().toString().isEmpty()
                        || productImg.getHint().toString().isEmpty()){
                    Toast.makeText(Product.this,
                            "Data missing",
                            Toast.LENGTH_SHORT).show();
                }else {
                    ProductEntity productEntity = new ProductEntity();
                    productEntity.setProductName(productName.getText().toString());
                    productEntity.setProductPrice(productPrice.getText().toString());
                    productImg.setHintTextColor(Color.BLACK);
                    productEntity.setProductImg(productImg.getHint().toString());
                    dao.insertProduct(productEntity);
                    final Intent i = new Intent(getApplicationContext(), FarmerActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint({"ObsoleteSdkInt", "UseCompatLoadingForDrawables", "QueryPermissionsNeeded"})
    public void takeImage(View view) {
        //Create Dialog
        dialogM = new Dialog(Product.this);
        dialogM.setContentView(R.layout.custom_dialog_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogM.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        Window window = dialogM.getWindow();
        window.setGravity(Gravity.BOTTOM);

        dialogM.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogM.setCancelable(false); //Optional
        dialogM.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog
        dialogM.show();

        ImageView camera = dialogM.findViewById(R.id.cameraOp);
        ImageView gallery = dialogM.findViewById(R.id.galleryOp);

        camera.setOnClickListener(v -> {
            dialogM.dismiss();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager())!= null){
                Uri imagePath = AccessStorage.createCameraImage(this);
                if (imagePath != null){
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imagePath);
                    startActivityForResult(intent,CAMERA);
                    intentCameraUri = intent.getData();
                }
            }

        });

        gallery.setOnClickListener(v -> {
            dialogM.dismiss();
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            if (intent.resolveActivity(getPackageManager())!= null){
                startActivityForResult(Intent.createChooser(intent,"Title"),GALLERY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK ){
            String camerapath = AccessStorage.getImgPath(intentCameraUri,this);// it will return the Capture image path
            productImg.setHint(camerapath);

            Log.d("CP",camerapath);
            Toast.makeText(Product.this, "Picture Selected from Camera", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == GALLERY && resultCode == Activity.RESULT_OK ) {
            assert data != null;
            uri = data.getData();
            Toast.makeText(Product.this, "Picture Selected from Gallery", Toast.LENGTH_SHORT).show();
            try {
                Bitmap bitDemo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                String gallPath = AccessStorage.saveImageToGallery(bitDemo, this);
                productImg.setHint(gallPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        else if (requestCode == FILES && resultCode == Activity.RESULT_OK){
//            Uri content_describer = data.getData();
//            String src = content_describer.getPath();
//            source = new File(src);
//            Log.d("src is ", source.toString());
//            String filename = content_describer.getLastPathSegment();
//            Log.d("FileName is ",filename);
//            destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ filename);
//            Log.d("Destination is ", destination.toString());
//            intentFileUri = Uri.parse(filename);
//            sourceFilePath = AccessStorage.getSourceFilePath(this,intentFileUri);
//            Log.d("getP",""+sourceFilePath);
//            String filePath = AccessStorage.saveFileToStorage(this,sourceFilePath);
//            mediaEdittext.setHint(filePath);
//        }
        else {
            Toast.makeText(Product.this, "Picture Not Selected ", Toast.LENGTH_SHORT).show();
        }
    }

}