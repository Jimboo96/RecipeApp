package com.example.jimbo.recipeapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeView extends Activity {
    ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> recipeUrlList = new ArrayList<String>();
    ArrayList<String> publisherList = new ArrayList<String>();

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
        publisherList = in.getStringArrayListExtra("publisherList");

        Object[] objNames = titleList.toArray();
        final String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        Object[] objNames2 = recipeUrlList.toArray();
        final String[] strLinks = Arrays.copyOf(objNames2, objNames2.length, String[].class);

        Object[] objNames3 = publisherList.toArray();
        final String[] publishers = Arrays.copyOf(objNames3, objNames3.length, String[].class);

        CustomListAdapter adapter=new CustomListAdapter(this, strNames,strLinks, imageUrlList, publishers);
        list= findViewById(R.id.listView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String Selecteditem= strLinks[+position];
                //Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                Intent browse = new Intent(Intent.ACTION_VIEW , Uri.parse(Selecteditem));
                startActivity( browse );
            }
        });
    }
}
