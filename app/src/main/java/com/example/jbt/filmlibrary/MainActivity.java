package com.example.jbt.filmlibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView mylist;
    ImageView plus;
    customadapter adapter;
    ImageView img;
    helperdb hdb;



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    //CREATE MENU (EDIT AND DELETE THE MOVIE ) WHEN ITEM LONG CLICKED
        switch (v.getId()){

            case R.id.listy:
                getMenuInflater().inflate(R.menu.mymenu2,menu);
                menu.setHeaderTitle("List Options:");
                break;

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
       ListView list=(ListView) findViewById(R.id.listy);
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index=info.position;
        film f=(film) list.getItemAtPosition(info.position);
            switch (item.getItemId())
            {
                case R.id.edit :

                    /* IN CASE WE PRESS EDIT IN THE LIST MENU ITEM IT WILL SEND US TO THE EDIT ACTIVITY
                    AND SEND THE DATA BY INTENT TO USE IT IN THE EDIT ACTIVITY*/

                    Intent in = new Intent(MainActivity.this,edit_activity.class);
                    in.putExtra("subject" , f.getName() );
                    in.putExtra("body" , f.getDetails() );
                    in.putExtra("url",f.getUrl());
                    in.putExtra("id",f.getId());
                    in.putExtra("manual",true); // THIS TO KNOW THAT IT COMES FROM EDIT TO MAKE UPDATE NOT ADD
                    startActivity(in);
                    break;

                case R.id.delete:
                    hdb.removefilm(f.getId()); // REMOVE THE SELECTED ITEM FROM THE DATABASE
                    adapter.remove(f);         // REMOVE THE SELECTED ITEM FROM THE ADAPTER
                    break;
            }
        return super.onContextItemSelected(item);
    }

    @Override
    // THIS FUNCTION TO MAKE EDIT WHEN THE ITEM IN MAIN LIST SHORT CLICKED
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        film n = (film) adapterView.getItemAtPosition(i);
        // SAVE THE DATA IN INTENT TO USE IT IN THE EDIT ACTIVITY
        Intent in = new Intent(MainActivity.this,edit_activity.class);
        in.putExtra("subject" , n.getName() );
        in.putExtra("body" , n.getDetails() );
        in.putExtra("url",n.getUrl());
        in.putExtra("id",n.getId());
        in.putExtra("manual",true); //THIS TO KNOW THAT IT COMES FROM ITEM CLICK, TO MAKE EDIT NOT ADD
        startActivity(in);
    }


    //TO CREATE THE MENU (DELETE ALL MOVIES AND EXIT)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumy , menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.delete){

            hdb.removeallfilm();        //IF DELETE ALL MOVIES SELECTED, DELETE ALL THE ITEM
            adapter.clear();            //TO CLEAR THE ADAPTER AFTER DELETING THE DATABASE

        }
        else if (item.getItemId()==R.id.exit)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hdb=new helperdb(this,"filmdb", null, 1);  // SQLopenhelper db constructor
        mylist=(ListView) findViewById(R.id.listy);
        plus=(ImageView) findViewById(R.id.imageView2);
        adapter = new customadapter(this,R.layout.listitem);
        img=(ImageView) findViewById(R.id.imageView2);
        mylist.setOnItemClickListener(this);

      adapter.addAll(hdb.getAllNames());    //get all the film objects and add it to the customer adapter
        mylist.setAdapter(adapter);         //add the adapter to the main list

    registerForContextMenu(mylist);

    img.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);   //show alert dialog
        builder.setMessage("How you want to add the movie?");
        builder.setTitle("+Add Movie");
        builder.setPositiveButton("FROM INTERNET", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       Intent in=new Intent(MainActivity.this,search.class);        //go to search activity in case internet selected
                        startActivity(in);
                    }
                });




        builder.setNegativeButton("MANUALLY",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in=new Intent(MainActivity.this,edit_activity.class);//go to edit activity in case manual selected
                        startActivity(in);
                    }
                });
        builder.show();

                    }
            });
    }


    @Override
    protected void onRestart() {        //in case we restart the app
        super.onRestart();
        adapter.clear();                //CLEAR THE ADAPTER IN ORDER NOT TO CLEAR THE OLD LIST
        adapter.addAll(hdb.getAllNames());//GET ALL THE FILM OBJECTS AGAIN FROM THE DB AND ADD THEM TO THE ADAPTER
    }
}
