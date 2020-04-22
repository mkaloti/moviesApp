package com.example.jbt.filmlibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class search extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Button b;
    private ListView list;
    private ArrayAdapter adapter;
    private ProgressDialog progressDialog;
    Button search;
    EditText searchedit;
    String details;

    String poster;
    int flag=0;
    String title;


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        title=(String)adapterView.getItemAtPosition(i);//GET THE CLICKED ITEM AND ASSIGN IT TO TITLE
        flag=1;      //IN CASE ITEM CLICKED
        String txty=title.replaceAll(" ","%20");  //TO REPLACE THE SPACE
        new filmtask().execute("http://www.omdbapi.com/?t=" + txty + "&y=&plot=short&r=json");   //JSON API TO GET THE JSON FOR ONE FILM
    }//THE EXECUTE TO CALL THE ASYNKTASK

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search=(Button) findViewById(R.id.searchbtn);
        searchedit = (EditText) findViewById(R.id.searchedit);
        b=(Button) findViewById(R.id.button5);
        list=(ListView) findViewById(R.id.filmlist);
        adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1);   //SIMPLE ADAPTER FOR LIST

        list.setOnItemClickListener(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchedita=searchedit.getText().toString();
                String searcheditb=searchedita.replaceAll(" ","%20");

                new filmtask().execute("http://www.omdbapi.com/?s="+searcheditb+"&y=&plot=short&r=json");   //GET ALL THE FILM JSON








            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public  class filmtask extends AsyncTask <String,Void,String>{    //GENERIC PARAMETRERS FOR THE DOINBACKGROUND AND ONPROGRESSUPDATE

        @Override
        protected void onPreExecute() {      //BEFORE THE EXECUTION OF THE ASYNKTASK
            super.onPreExecute();
            progressDialog = new ProgressDialog(search.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String...params) {//AT THE BACKGROUND
            URL url;
            StringBuilder builder = new StringBuilder();//CREATE A STRING BUILDER

            try {
                url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = reader.readLine();
                while (line != null) {
                    builder.append(line + "\n");    //PUT THE JSON OBJECT INTO STRING BUILDER
                    line = reader.readLine();
                }
            } catch (Exception e) {
                Log.e(" the error : " , e.toString());
            }


            return builder.toString();    //RETURN THE STRING BUILDER TO USE IT AS A PARAMETER FOR THE POST EXECUTE
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (flag == 0) {//IN CASE SEARCH BUTTON CLICKED

            try {
                JSONObject o = new JSONObject(s);
                JSONArray a=o.getJSONArray("Search");//PUT ALL THE JSON OBJECTS WITH THE "SEARCH" TITLE AND PUT IN JSON OBJECT ARRAY

                for(int i=0;i<a.length();i++)//GO FOR EACH JSON OBJECT
                {
                    JSONObject c=a.getJSONObject(i);
                    String title=c.getString("Title");  //GET THE JSON WITH TITLE NAME
                    adapter.add(title);//ADD THE FILM TITLE TO THE SIMPLE ADAPTER
                }
                list.setAdapter(adapter);   //ADD THE ADAPTER TO THE LIST


            } catch (Exception e){

            }}else if (flag==1){  //IN CASE ITEM CLICKED IN THE LIST
                try {
                    JSONObject o = new JSONObject(s);//ONE JSON OBJECT
                  details=o.getString("Plot");//FROM JSON GET THE PLOT
                    poster=o.getString("Poster");//FROM JSON GET POSTER
                }catch (Exception e){}

                Intent in = new Intent(search.this,edit_activity.class); //PUT VALUES TO USE IN THE INTENT
                in.putExtra("Title" ,title );
                in.putExtra("subject" ,details);
                in.putExtra("url" ,poster);
                in.putExtra("internet",true);

                startActivity(in);}
        }
    }
}
