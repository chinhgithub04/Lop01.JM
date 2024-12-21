package com.example.lop01jm.ui.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lop01jm.R;
import com.example.lop01jm.data.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {

    private TextInputEditText edtUserName, edtEmail, edtPhoneNumber, edtDoB;
    private FirebaseFirestore db;
    private Date selectedDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        db = FirebaseFirestore.getInstance();

        Button btn_back, btn_save;
        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btnSave);
        edtUserName = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtDoB = findViewById(R.id.edtDoB);

        btn_back.setOnClickListener(v -> finish());

        btn_save.setOnClickListener(v -> updateUserProfile());

        edtDoB.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (edtDoB.getRight() - edtDoB.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    showDatePickerDialog();
                    return true;
                }
            }
            return false;
        });

        loadUserProfile();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    calendar.set(year1, month1, dayOfMonth);
                    selectedDate = calendar.getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    edtDoB.setText(dateFormat.format(selectedDate));
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void loadUserProfile() {
        String userId = "user1";
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            edtUserName.setText(user.getName());
                            edtEmail.setText(user.getEmail());
                            edtPhoneNumber.setText(user.getPhoneNumber());
                            if (user.getDoB() != null) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                edtDoB.setText(dateFormat.format(user.getDoB()));
                                selectedDate = user.getDoB();
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show());
    }

    private void updateUserProfile() {
        String userId = "user1";
        String email = edtEmail.getText().toString();
        String name = edtUserName.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setDoB(selectedDate);

        db.collection("users").document(userId)
                .update("email", email, "name", name, "phoneNumber", phoneNumber, "DoB", selectedDate)
                .addOnSuccessListener(aVoid -> Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show());
        finish();
    }
}