package com.example.jimbo.recipeapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeView extends ListActivity {
    ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> recipeUrlList = new ArrayList<String>();

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
        String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);

        this.setListAdapter(new ArrayAdapter<String>(
                this, R.layout.recipe_view,
                R.id.Itemname,strNames));
    }
}
