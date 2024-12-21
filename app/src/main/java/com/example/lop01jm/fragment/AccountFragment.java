package com.example.lop01jm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lop01jm.R;
import com.example.lop01jm.data.model.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountFragment extends Fragment {

    private FirebaseFirestore db;
    private TextView nameTextView;
    private TextView emailTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        db = FirebaseFirestore.getInstance();

        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);

        loadUserData();

        return view;
    }

    private void loadUserData() {
        String userId = "user1";
        db.collection("users").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String name = document.getString("name");
                    String email = document.getString("email");

                    nameTextView.setText(name);
                    emailTextView.setText(email);
                }
            } else {
            }
        });
    }
}