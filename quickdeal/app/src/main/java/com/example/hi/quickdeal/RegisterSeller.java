package com.example.hi.quickdeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterSeller extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_seller);
    }

    public void signin(View v)
    {
        Intent i=new Intent(RegisterSeller.this,Seller.class);
        startActivity(i);
    }

    public void register(View v)
    {
        Intent i=new Intent(RegisterSeller.this,Verification.class);
        startActivity(i);
    }
}
