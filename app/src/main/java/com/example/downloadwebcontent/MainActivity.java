package com.example.downloadwebcontent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final String mailRu = "https//mail.ru/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask downloadTask = new DownloadTask();
        try {
            String result = downloadTask.execute(mailRu).get();
            Log.i("URL", result);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    result.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result.toString();
        }
    }
}