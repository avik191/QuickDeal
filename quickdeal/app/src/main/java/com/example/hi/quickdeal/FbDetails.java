package com.example.hi.quickdeal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class FbDetails extends AppCompatActivity {

    TextView tv;
    ImageView img;
    int acc=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_details);

        tv= (TextView) findViewById(R.id.tv);
        img= (ImageView) findViewById(R.id.img);

        Bundle b=getIntent().getExtras();
        acc=b.getInt("acc");
        if(acc==0) {
            Profile p = b.getParcelable("profile");
            Uri uri = p.getProfilePictureUri(100, 100);
            tv.setText(p.getFirstName() + " " + p.getLastName());
            String s = uri.toString();

            Picasso.with(FbDetails.this).load(s).into(img);
        }
        else {
            GoogleSignInAccount account = b.getParcelable("account");

            tv.setText(account.getDisplayName());
            String s=account.getPhotoUrl().toString();
            Picasso.with(FbDetails.this).load(s).into(img);

        }

    }

    public void logout(View v)
    {
        if(acc==0) {
            LoginManager.getInstance().logOut();
        }
        Toast.makeText(FbDetails.this,"successfully logged out",Toast.LENGTH_LONG).show();
        Intent i=new Intent(FbDetails.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
