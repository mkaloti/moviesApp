package com.example.jbt.filmlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

//CUSTOMER ADAPTER TO DISPLAY A CUSTOM LIST

public class customadapter extends ArrayAdapter <film> {
    public customadapter(Context context, int resource) {
        super(context, resource);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //RETURN A VIEW TO DISPLAY IT IN THE LIST

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem,null);

        TextView name = (TextView) convertView.findViewById(R.id.title);
        TextView details = (TextView) convertView.findViewById(R.id.details);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        film f =  getItem(position);
        name.setText(f.getName());
        details.setText(f.getDetails());

        if(!f.getUrl().equals("")) {                //GET THE URL AND LOAD IT TO THE IMAGEVIEW
            img.setImageResource(f.getImage());
            Ion.with(getContext())
                    .load(f.getUrl().toString())//LOAD THE IMAGE FROM THE INTERNET
                    .intoImageView(img);
        }
        else                                           //IF THE URL EMPTY LOAD THE DEFAULT IMAGE
        {
            img.setImageResource(R.drawable.movie);

        }

        return convertView;

    }
}
