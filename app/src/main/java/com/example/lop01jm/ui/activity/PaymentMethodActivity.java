package com.example.lop01jm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lop01jm.R;
import com.example.lop01jm.data.model.PaymentMethod;
import com.example.lop01jm.ui.adapter.PaymentMethodAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private PaymentMethodAdapter adapter;
    private List<PaymentMethod> paymentMethods;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_methods);

        db = FirebaseFirestore.getInstance();
        paymentMethods = new ArrayList<>();
        adapter = new PaymentMethodAdapter(this, paymentMethods);

        RecyclerView recyclerView = findViewById(R.id.rvShippingAddress);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btn_back = findViewById(R.id.btn_back);
        Button btn_new = findViewById(R.id.btnNewPaymentMethods);

        btn_back.setOnClickListener(v -> finish());
        btn_new.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentMethodActivity.this, NewPaymentMethodActivity.class);
            startActivity(intent);
        });

        loadPaymentMethods();
    }

    private void loadPaymentMethods() {
        String userId = "user1";
        db.collection("users").document(userId).collection("paymentMethods")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        return;
                    }
                    paymentMethods.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("name");
                        String number = document.getString("number");
                        String expiryDate = document.getString("expiryDate");
                        int logoResId = document.getLong("logoResId").intValue();
                        String cvv = document.getString("cvv");
                        paymentMethods.add(new PaymentMethod(document.getId(), name, number, expiryDate, logoResId, cvv));
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}