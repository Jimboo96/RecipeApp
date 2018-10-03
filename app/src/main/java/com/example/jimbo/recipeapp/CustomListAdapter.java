package com.example.jimbo.recipeapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] linkName;
    private final String[] publishers;
    private final ArrayList<String> imgArray;

    public CustomListAdapter(Activity context, String[] itemname, String[] linkName, ArrayList<String> imgArray, String[] publishers) {
        super(context, R.layout.recipe_view, itemname);
        this.context=context;
        this.itemname=itemname;
        this.linkName=linkName;
        this.imgArray=imgArray;
        this.publishers=publishers;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.recipe_view, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.Itemname);
        TextView extratxt = (TextView) rowView.findViewById(R.id.linkName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        Picasso.get().load(imgArray.get(position)).into(imageView);
        txtTitle.setText(itemname[position]);
        extratxt.setText(publishers[position]);

        return rowView;
    };
}