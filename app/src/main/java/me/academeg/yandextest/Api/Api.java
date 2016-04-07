package me.academeg.yandextest.Api;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Api {

    private String HOST;


    public Api() {
        HOST = "http://download.cdn.yandex.net/mobilization-2016/artists.json";
    }

    public ArrayList<ApiArtist> getArtists() throws IOException {
        ArrayList<ApiArtist> artists = null;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(HOST)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        try {
            JSONArray result = new JSONArray(response.body().string());
            artists = new ArrayList<>(result.length());
            for (int i = 0; i < result.length(); i++) {
                artists.add(ApiArtist.parse(result.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return artists;
    }

}
