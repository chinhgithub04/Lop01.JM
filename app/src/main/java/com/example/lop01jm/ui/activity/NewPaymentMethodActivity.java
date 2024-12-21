package com.example.lop01jm.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lop01jm.R;
import com.example.lop01jm.data.model.PaymentMethod;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewPaymentMethodActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private EditText edtName, edtNumber, edtDate, edtCVV;
    private String paymentMethodId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_payment_methods);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        edtName = findViewById(R.id.edtName);
        edtNumber = findViewById(R.id.edtNumber);
        edtDate = findViewById(R.id.edtDate);
        edtCVV = findViewById(R.id.edtCVV);

        Button btn_back = findViewById(R.id.btn_back);
        Button btnNewPaymentMethods = findViewById(R.id.btnNewPaymentMethods);

        paymentMethodId = getIntent().getStringExtra("paymentMethodId");
        if (paymentMethodId != null) {
            loadPaymentMethod(paymentMethodId);
            btnNewPaymentMethods.setText("Lưu");
        }

        btn_back.setOnClickListener(v -> finish());
        btnNewPaymentMethods.setOnClickListener(v -> savePaymentMethod());
    }

    private void loadPaymentMethod(String id) {
        String userId = auth.getCurrentUser().getUid();
        db.collection("users").document(userId).collection("paymentMethods").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        PaymentMethod paymentMethod = documentSnapshot.toObject(PaymentMethod.class);
                        if (paymentMethod != null) {
                            edtName.setText(paymentMethod.getName());
                            edtNumber.setText(paymentMethod.getNumber());
                            edtDate.setText(paymentMethod.getExpiryDate());
                            edtCVV.setText(paymentMethod.getCvv());
                        }
                    }
                });
    }

    private void savePaymentMethod() {
        String name = edtName.getText().toString().trim();
        String number = edtNumber.getText().toString().trim();
        String expiryDate = edtDate.getText().toString().trim();
        String cvv = edtCVV.getText().toString().trim();
        int logoResId = R.drawable.img_logo_visa; // Example logo resource ID

        if (name.isEmpty() || number.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            // Show error message
            return;
        }

        PaymentMethod newPaymentMethod = new PaymentMethod(paymentMethodId, name, number, expiryDate, logoResId, cvv);
        String userId = auth.getCurrentUser().getUid();

        if (paymentMethodId != null) {
            db.collection("users").document(userId).collection("paymentMethods").document(paymentMethodId).set(newPaymentMethod)
                    .addOnSuccessListener(aVoid -> finish())
                    .addOnFailureListener(e -> {
                        // Handle the error
                    });
        } else {
            db.collection("users").document(userId).collection("paymentMethods").add(newPaymentMethod)
                    .addOnSuccessListener(documentReference -> finish())
                    .addOnFailureListener(e -> {
                        // Handle the error
                    });
        }
    }
}