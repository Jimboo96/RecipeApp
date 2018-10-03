package com.example.jimbo.recipeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ImageChanger{
    private EditText editText;
    private TextView infoText;
    private ProgressDialog pd;
    ArrayList<HashMap<String, String>> recipeList = new ArrayList<HashMap<String, String>>();

    ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> recipeUrlList = new ArrayList<String>();
    ArrayList<String> publisherList = new ArrayList<String>();

    String TAG_TITLE = "title";
    String TAG_IMAGE_RUL = "image_url";
    String TAG_RECIPE_URL = "source_url";
    String TAG_PUBLISHER = "publisher";

    //Using an api called food2fork https://www.food2fork.com/about/api
    final private String API_KEY = "fd22eec9b203cd2d912c40c118c5cc49";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        infoText = findViewById(R.id.infoText);

        ImageChangerThread imageChangerThread = new ImageChangerThread();
        imageChangerThread.setNotifier(this);
        imageChangerThread.start();
    }

    protected void fetchButtonClicked(View v) {
        if(!editText.getText().toString().equals("")) {
            String url = "https://www.food2fork.com/api/search?key=" + API_KEY + "&q=" + editText.getText().toString();
            new JsonTask().execute(url);
        } else {
            infoText.setText("You have to search for something!");
        }
    }

    @Override
    public void changeImage(final String imageString) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = findViewById(R.id.imageView);
                Resources res = getResources();
                int resID = res.getIdentifier(imageString , "drawable", getPackageName());
                imageView.setImageResource(resID);
            }
        });
    }

    public void proceedToRecipes(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            String countOfRecipes = jObject.getString("count");
            JSONArray jArray = jObject.getJSONArray("recipes");

            // looping through All objects.
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject c = jArray.getJSONObject(i);

                // Storing each json item in variable
                String title = c.getString(TAG_TITLE);
                String imageUrl = c.getString(TAG_IMAGE_RUL);
                String recipeUrl = c.getString(TAG_RECIPE_URL);
                String publisher = c.getString(TAG_PUBLISHER);

                Log.d("title", title);
                Log.d("imageUrl", imageUrl);
                Log.d("recipeUrl", recipeUrl);
                Log.d("publisher", publisher);

                titleList.add(title);
                imageUrlList.add(imageUrl);
                recipeUrlList.add(recipeUrl);
                publisherList.add(publisher);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Go to recipeView.
        Intent in = new Intent(getApplicationContext(), RecipeView.class);
        in.putExtra("titleList", titleList);
        in.putExtra("imageUrlList", imageUrlList);
        in.putExtra("recipeUrlList", recipeUrlList);
        in.putExtra("publisherList", publisherList);
        startActivity(in);

        titleList.clear();
        imageUrlList.clear();
        recipeUrlList.clear();
        publisherList.clear();
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Fetching recipes...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            editText.setText("");
            infoText.setText("");

            String count = "0";
            try {
                JSONObject jObject = new JSONObject(result);
                count = jObject.getString("count");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int countOfRecipes = Integer.parseInt(count);
            if(countOfRecipes > 0) {
                proceedToRecipes(result);
            }else {
                infoText.setText("Could not find any recipes!");
            }
        }
    }
}
