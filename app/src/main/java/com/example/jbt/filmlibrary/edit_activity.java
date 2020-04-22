package com.example.jbt.filmlibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class edit_activity extends AppCompatActivity implements View.OnClickListener{
    Button b;
    Button b2;
    helperdb hdb;
    EditText su,body,url;
    ProgressDialog progressDialog;
    Button show;
    ImageView im;
    int num;

//for share icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        MenuItem shareItem=(MenuItem) menu.findItem(R.id.share);

       ShareActionProvider mshare = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,su.getText().toString());
        mshare.setShareIntent(shareIntent);
        return true;
    }


    //for the rating bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);
        num = getIntent().getIntExtra("id",0);
        hdb=new helperdb(this,"filmdb", null, 1);
        show=(Button) findViewById(R.id.showbtn);
        b=(Button) findViewById(R.id.okbtn);
        b2=(Button) findViewById(R.id.button3);
        su=(EditText) findViewById(R.id.subject);
        body=(EditText) findViewById(R.id.body);
        url=(EditText) findViewById(R.id.url);
        im=(ImageView) findViewById(R.id.imageView3);
        RatingBar rbar=(RatingBar) findViewById(R.id.ratingBar);


       rbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(edit_activity.this,String.valueOf(rating),Toast.LENGTH_SHORT).show();
            }
        });

        if(getIntent().getBooleanExtra("internet",false)==true) {//IN CASE WE PRESS IT COMES FROM SEARCH ACTIVITY


        ((TextView)findViewById(R.id.subject)).setText(getIntent().getStringExtra("Title"));
        ((TextView)findViewById(R.id.body)).setText(getIntent().getStringExtra("subject"));
        ((TextView)findViewById(R.id.url)).setText(getIntent().getStringExtra("url"));

        }

        if(getIntent().getBooleanExtra("manual",false)==true)  //IN CASE IT COMES FROM MAIN LIST
        {
            ((TextView)findViewById(R.id.subject)).setText(getIntent().getStringExtra("subject"));
            ((TextView)findViewById(R.id.body)).setText(getIntent().getStringExtra("body"));
            ((TextView)findViewById(R.id.url)).setText(getIntent().getStringExtra("url"));
        }


        show.setOnClickListener(this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                if(getIntent().getBooleanExtra("manual",false)==true){//IN CASE MAIN ACTIVITY MAKE UPDATE
                film f=new film(su.getText().toString(), body.getText().toString(),url.getText().toString());
                hdb.updatefilm(f,num);
                Intent in=new Intent(edit_activity.this,MainActivity.class);
                startActivity(in);}
                else
                {
                    film f=new film(su.getText().toString(), body.getText().toString(),url.getText().toString());
                    String name=su.getText().toString();
                    if(hdb.isExist(name)==true)
                      {
                  Toast.makeText(edit_activity.this, "film already exist", Toast.LENGTH_SHORT).show();
                       }
                    else {
                         hdb.addName(f);   //ADD OBJECT TO THE DB


                 Intent in = new Intent(edit_activity.this, MainActivity.class);
                  startActivity(in);
}

                }
            }
        });




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//EXIT


    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.showbtn:          //SHOW IMAGE BUTTON
                Ion.with(edit_activity.this)   //USE ION TO LOAD IMAGE TO THE IMAGE VIEW
                        .load(url.getText().toString())// LOAD THE IMAGE FROM THE INTERNET FROM THE URL EDITTEXT
                        .withBitmap()
                        .intoImageView(im);

                break;

        }



    }


}
