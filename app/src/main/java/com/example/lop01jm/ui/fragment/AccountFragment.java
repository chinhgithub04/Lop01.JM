package com.example.lop01jm.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lop01jm.R;
import com.example.lop01jm.ui.activity.PaymentMethodActivity;
import com.example.lop01jm.ui.activity.YourProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountFragment extends Fragment {

    private TextView nameTextView, emailTextView;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);
        db = FirebaseFirestore.getInstance();

        RelativeLayout yourprofile, paymethod;
        yourprofile = view.findViewById(R.id.myprofile);
        paymethod = view.findViewById(R.id.paymethod);




        yourprofile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), YourProfileActivity.class);
            startActivity(intent);
        });



        paymethod.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PaymentMethodActivity.class);
            startActivity(intent);
        });


        return view;
    }


}