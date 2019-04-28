package com.ads.rockbander.datafetch;

import android.os.AsyncTask;

import com.ads.rockbander.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataFetcher extends AsyncTask<Void, Void, Void> {
    String quote = "";
    String author = "";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://quotes.rest/qod.json?category=art");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            String dataJson = "";
            while(line != null){
                line = bufferedReader.readLine();
                dataJson += line;
            }

            JSONObject root = new JSONObject(dataJson);
            JSONObject jsonContents = root.getJSONObject("contents");
            JSONArray quotes = jsonContents.getJSONArray("quotes");
            quote = quotes.getJSONObject(0).getString("quote");
            author = quotes.getJSONObject(0).getString("author");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.tvQuote.setText(quote + " ~ "+ author);
    }
}
