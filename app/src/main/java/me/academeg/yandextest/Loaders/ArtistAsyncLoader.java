package me.academeg.yandextest.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.ArrayList;

import me.academeg.yandextest.Api.Api;
import me.academeg.yandextest.Api.ApiArtist;


public class ArtistAsyncLoader extends AsyncTaskLoader<ArrayList<ApiArtist>> {

    private boolean networkException;

    public ArtistAsyncLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<ApiArtist> loadInBackground() {
        ArrayList<ApiArtist> artists;
        networkException = false;
        try {
            artists = new Api().getArtists();
        } catch (IOException e) {
            artists = null;
            networkException = true;
            e.printStackTrace();
        }
        return artists;
    }

    public boolean isNetworkException() {
        return networkException;
    }
}
