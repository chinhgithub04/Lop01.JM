package com.example.lop01jm.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lop01jm.R;
import com.example.lop01jm.data.model.PaymentMethod;
import com.example.lop01jm.ui.activity.NewPaymentMethodActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder> {

    private List<PaymentMethod> paymentMethodList;
    private Context context;
    private FirebaseFirestore db;

    public PaymentMethodAdapter(Context context, List<PaymentMethod> paymentMethodList) {
        this.context = context;
        this.paymentMethodList = paymentMethodList;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentMethod paymentMethod = paymentMethodList.get(position);
        holder.tvName.setText(paymentMethod.getName());
        holder.tvNumber.setText(paymentMethod.getNumber());
        holder.tvDate.setText(paymentMethod.getExpiryDate());
        holder.imgLogo.setImageResource(paymentMethod.getLogoResId());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewPaymentMethodActivity.class);
            intent.putExtra("paymentMethodId", paymentMethod.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            String userId = "user1";
            db.collection("users").document(userId).collection("paymentMethods").document(paymentMethod.getId()).delete()
                    .addOnSuccessListener(aVoid -> {
                    })
                    .addOnFailureListener(e -> {
                    });
        });
    }

    @Override
    public int getItemCount() {
        return paymentMethodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvNumber;
        TextView tvDate;
        ImageView imgLogo;
        Button btnEdit;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvDate = itemView.findViewById(R.id.tvDate);
            imgLogo = itemView.findViewById(R.id.img_logo);
            btnEdit = itemView.findViewById(R.id.btnNewPaymentMethod);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}