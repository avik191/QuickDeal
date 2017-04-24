package com.example.hi.quickdeal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

public class Seller extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    Button login;
    CallbackManager callbackManager;
    AccessTokenTracker accesstokentracker;
    ProfileTracker tracker;
    TextView tv;
    LoginButton button;

    GoogleApiClient googleApiClient;
    static final int req_code=9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_seller);

        callbackManager = CallbackManager.Factory.create();


        login= (Button) findViewById(R.id.fblogin);
       // button= (LoginButton) findViewById(R.id.fblogin);
        tv= (TextView) findViewById(R.id.tv);


        accesstokentracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };

        tracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile profile) {
                if(profile!=null) {
                   // String j = profile.getFirstName() + " " + profile.getLastName() + "/" + profile.getId();
                    //tv.setText("profile tracker"+j);

                    Intent i=new Intent(Seller.this,FbDetails.class);
                    Bundle b=new Bundle();
                    b.putParcelable("profile",profile);
                    b.putInt("acc",0);
                    i.putExtras(b);
                    startActivity(i);
                    finish();
                }
            }
        };

        tracker.startTracking();
        accesstokentracker.startTracking();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(Seller.this,"success", Toast.LENGTH_LONG).show();

                AccessToken token=loginResult.getAccessToken();
                Profile profile= Profile.getCurrentProfile();
                String s = token.getUserId();
               // tv.setText(s);
                if(profile!=null) {
                 //   String j = profile.getFirstName() + " " + profile.getLastName() + "/" + profile.getId()+"/"+token.getUserId();
                 //   tv.setText("onsuccess"+j);
                    Intent i=new Intent(Seller.this,FbDetails.class);
                     Bundle b=new Bundle();
                    b.putParcelable("profile",profile);
                    b.putInt("acc",0);
                    i.putExtras(b);
                    startActivity(i);
                    finish();
                }
                // App code
            }

            @Override
            public void onCancel() {
                // App code
                tv.setText("cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();


    }

    public void googlesignin()
    {

        Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,req_code);
    }

    public void handleresult(GoogleSignInResult result)
    {

        if(result.isSuccess())
        {
            GoogleSignInAccount account=result.getSignInAccount();
            String n=account.getDisplayName();
            //tv.setText(n);
            Intent i=new Intent(Seller.this,FbDetails.class);
            Bundle b=new Bundle();
            b.putParcelable("account",account);
            b.putInt("acc",1);
            i.putExtras(b);
            startActivity(i);
            finish();
        }
    }

    public void registerseller(View v)
    {
        Intent i=new Intent(Seller.this,RegisterSeller.class);
        startActivity(i);
    }

    public void loginfb(View v)
    {
        LoginManager.getInstance().logInWithReadPermissions(Seller.this, Arrays.asList("email"));

    }

    public void logingoogle(View v)
    {
        googlesignin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==req_code)
        {
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleresult(result);
        }
        else
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();

        tracker.startTracking();
        accesstokentracker.stopTracking();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
