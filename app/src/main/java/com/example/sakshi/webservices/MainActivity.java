package com.example.sakshi.webservices;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoader.LoadData;
import com.example.networkutil.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView githubName, githubBio;
    EditText userET;
    Button goButton;

    String BASE_URL = "http://api.github.com/users/";
    String data = null;

    String gitName, gitBio, Image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        githubName = findViewById(R.id.githubName);
        githubBio = findViewById(R.id.githubBio);
        userET = findViewById(R.id.userET);
        goButton = findViewById(R.id.goButton);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadData().execute();


            }
        });
    }


    class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String userName = githubName.getText().toString();

            if (userName == null) {
                return null;
            }
            String url = BASE_URL + userName;

            data = NetworkUtil.makeServiceCall(url);

            try {

                JSONObject jsonObject = new JSONObject(data);
                gitName = jsonObject.getString("name");
                gitBio = jsonObject.getString("bio");
                Image = jsonObject.getString("avatar_url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (gitName.equals("null")) {

                githubName.setText(null);
            } else {
                githubName.setText(gitName);
            }

            if (githubBio.equals("null")) {

                githubBio.setText(null);
            } else {
                githubBio.setText(gitBio);
            }

            Glide.with(MainActivity.this).load(Image).into(imageView);


        }

    }
}