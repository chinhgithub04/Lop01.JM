package com.example.lop01jm.data.model;


public class PaymentMethod  {
    private String id;
    private String name;
    private String number;
    private String expiryDate;
    private int logoResId;
    private String cvv;

    public PaymentMethod(){}

    public PaymentMethod(String id, String name, String number, String expiryDate, int logoResId, String cvv) {
       this.id = id;
        this.name = name;
        this.number = number;
         this.expiryDate = expiryDate;
        this.logoResId = logoResId;
        this.cvv = cvv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public int getLogoResId() {
        return logoResId;
    }

    public String getCvv() {
        return cvv;
    }
}