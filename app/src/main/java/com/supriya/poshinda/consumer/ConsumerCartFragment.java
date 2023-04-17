package com.supriya.poshinda.consumer;

import static android.service.controls.ControlsProviderService.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.supriya.poshinda.R;
import com.supriya.poshinda.Room.CartEntity;
import com.supriya.poshinda.Room.DataDAO;
import com.supriya.poshinda.Room.PoshindaDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ConsumerCartFragment extends Fragment implements PaymentResultListener {

    RecyclerView recyclerView;
    TextView rateView;
    Button payBtn;
    int sum=0;
    List<CartEntity> cartEntityList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_cart_fragment,container,false);

        rateView = view.findViewById(R.id.rateview);
        payBtn = view.findViewById(R.id.paymentBtn);
        recyclerView = view.findViewById(R.id.recview);
        getroomdata();

        //payment integration
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getroomdata();

                String samount= String.valueOf(sum);
                Toast.makeText(getContext(), "sssssss: "+samount, Toast.LENGTH_SHORT).show();

                // rounding off the amount.
                int amount = Math.round(Float.parseFloat(samount) * 100);

                // initialize Razorpay account.
                Checkout checkout = new Checkout();

                // set your id as below
                checkout.setKeyID("rzp_test_rbMuPNmWCJpz6z");

                // set image
                checkout.setImage(R.drawable.poshinda);

                // initialize json object
                JSONObject object = new JSONObject();
                try {
                    // to put name
                    object.put("name", "Poshinda");

                    // put description
                    object.put("description", "Test payment");

                    // to set theme color
                    object.put("theme.color", "#25383C");

                    // put the currency
                    object.put("currency", "INR");

                    // put amount
                    object.put("amount", amount);

                    // put mobile number
                    // object.put("prefill.contact", "9876543210");

                    // put email
                    // object.put("prefill.email", "poshinda@gmail.com");

                    // open razorpay to checkout activity
                    checkout.open(getActivity(), object);
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Issue during payment", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onPaymentSuccess(String s) {
        // this method is called on payment success.
        Toast.makeText(getContext(), "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        Toast.makeText(getContext(), "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }

    private void getroomdata() {
        DataDAO dataDAO = PoshindaDB.getInstance(getContext()).dataDAO();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartEntityList = dataDAO.getAllCartEntry();

        ConsumerAdapter adapter = new ConsumerAdapter(cartEntityList,rateView);
        recyclerView.setAdapter(adapter);

        int i;
        for (i = 0;i<cartEntityList.size();i++){
            sum = cartEntityList.get(i).getPrice();
//           sum = sum+(cartEntityList.get(i).getPrice()*cartEntityList.get(i).getQnt());
            Toast.makeText(getContext(), "Priceeee Toatal: "+sum, Toast.LENGTH_SHORT).show();
        }

//        totalSum = cartEntityList.get(i).getPrice();

        rateView.setText("Total Amount: INR "+sum);

    }
}

//rzp_test_rbMuPNmWCJpz6z
//y1QsXLOgZ0iWwVjVoUK0NoNY