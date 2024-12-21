package com.example.lop01jm.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private String email;
    private String name;
    private String phoneNumber;
    private Date DoB;
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    public User(String email, String name, String phoneNumber, Date doB, List<PaymentMethod> paymentMethods) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        DoB = doB;
        this.paymentMethods = paymentMethods;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDoB() {
        return DoB;
    }

    public void setDoB(Date doB) {
        DoB = doB;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}