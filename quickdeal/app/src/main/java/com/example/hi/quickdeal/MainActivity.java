package com.example.hi.quickdeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gobuyer(View v)
    {
        Intent i=new Intent(MainActivity.this,Buyer.class);
        startActivity(i);
    }

    public void goseller(View v)
    {
        Intent i=new Intent(MainActivity.this,Seller.class);
        startActivity(i);
    }
}
