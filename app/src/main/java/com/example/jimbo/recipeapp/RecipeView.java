package com.example.jimbo.recipeapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeView extends Activity {
    ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> recipeUrlList = new ArrayList<String>();

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        Intent in = getIntent();

        // Get JSON values from previous intent
        titleList = in.getStringArrayListExtra("titleList");
        imageUrlList = in.getStringArrayListExtra("imageUrlList");
        recipeUrlList = in.getStringArrayListExtra("recipeUrlList");

        Object[] objNames = titleList.toArray();
        final String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        Object[] objNames2 = recipeUrlList.toArray();
        final String[] strLinks = Arrays.copyOf(objNames2, objNames2.length, String[].class);

        Integer[] imgid={
                R.drawable.food1,
                R.drawable.food2,
                R.drawable.food2,
                R.drawable.food3,
                R.drawable.food3,
                R.drawable.food4,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food5,
                R.drawable.food6
        };


        //for(int i= 0; i < imageUrlList.size();i++) {
          //  try {
                //imgid[i] = drawableFromUrl(imageUrlList.get(i));
            //} catch (IOException e) {
              //  e.printStackTrace();
            //}
        //}

        //Resources res = getResources();
        //int resID = res.getIdentifier(imageString , "drawable", getPackageName());
        //imageView.setImageResource(resID);

        CustomListAdapter adapter=new CustomListAdapter(this, strNames,strLinks, imgid);
        list=(ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= strLinks[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }
}
