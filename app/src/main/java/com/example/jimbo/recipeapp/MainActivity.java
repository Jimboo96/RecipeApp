package com.example.jimbo.recipeapp;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements ImageChanger{
    private EditText editText;
    private TextView infoText;
    private ProgressDialog pd;

    //Using an api called food2fork https://www.food2fork.com/about/api
    private String API_KEY = "fd22eec9b203cd2d912c40c118c5cc49";
    private String url;

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
        String userInput = editText.getText().toString();
        url = "https://www.food2fork.com/api/search?key=" + API_KEY + "&q=" + userInput;
        new JsonTask().execute(url);
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Fetching recipe...");
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
            infoText.setText(result);
            proceedToRecipes(result);
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
        //Here we go to the next intent.
        Log.d("recipes",result);
    }
}
