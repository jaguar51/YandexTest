package me.academeg.yandextest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import me.academeg.yandextest.Adapters.ArtistsAdapter;
import me.academeg.yandextest.Adapters.ItemClickListener;
import me.academeg.yandextest.Api.ApiArtist;
import me.academeg.yandextest.Components.SimpleDividerItemDecoration;
import me.academeg.yandextest.Loaders.ArtistAsyncLoader;


public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener,
        LoaderManager.LoaderCallbacks<ArrayList<ApiArtist>> {

    private final int ARTISTS_LOADER_ID = 0;
    private boolean isFirstLoad = true;

    private RecyclerView artistsRV;
    private ArtistsAdapter adapter;
    private ArrayList<ApiArtist> stockArtistsList;

    private SwipeRefreshLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.artists);
        setSupportActionBar(toolbar);

        adapter = new ArtistsAdapter(this, null);
        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent in = new Intent(MainActivity.this, ArtistActivity.class);
                Pair<View, String> p1 = Pair.create(view.findViewById(R.id.iv_avatar), "avatar");
                Pair<View, String> p2 = Pair.create(view.findViewById(R.id.tv_genres), "genres");
                Pair<View, String> p3 = Pair.create(view.findViewById(R.id.tv_count_albums_tracks),
                        "albums_tracks");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, p1, p2, p3);
                in.putExtra("artist", adapter.getData().get(position));
                startActivity(in, options.toBundle());
            }
        });
        artistsRV = (RecyclerView) findViewById(R.id.rr_artists_list);
        artistsRV.setLayoutManager(new LinearLayoutManager(this));
        artistsRV.addItemDecoration(new SimpleDividerItemDecoration(this));
        artistsRV.setAdapter(adapter);

        layout = (SwipeRefreshLayout) findViewById(R.id.swl_layout);
        layout.setOnRefreshListener(this);

        if (savedInstanceState != null) {
            isFirstLoad = savedInstanceState.getBoolean("isFirstLoad");
        }

        getSupportLoaderManager().initLoader(ARTISTS_LOADER_ID, null, this);
        if (isFirstLoad || adapter.getItemCount() == 0) {
            getSupportLoaderManager().getLoader(ARTISTS_LOADER_ID).forceLoad();
            isFirstLoad = false;
            layout.post(new Runnable() {
                @Override
                public void run() {
                    layout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isFirstLoad", isFirstLoad);
    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().getLoader(ARTISTS_LOADER_ID).forceLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (stockArtistsList == null) {
            stockArtistsList = adapter.getData();
        }
        if (newText.equals("")) {
            adapter.changeData(stockArtistsList);
        } else {
            adapter.changeData(performSearch(stockArtistsList, newText));
        }
        return true;
    }

    /**
     * Goes through the given list and filters it according to the given query.
     * @param artists list given as search sample
     * @param query to be searched
     * @return new filtered list
     */
    private ArrayList<ApiArtist> performSearch(ArrayList<ApiArtist> artists, String query) {

        // First we split the query so that we're able
        // to search word by word (in lower case).
        String[] queryByWords = query.toLowerCase().split("\\s+");

        // Empty list to fill with matches.
        ArrayList<ApiArtist> artistsFiltered = new ArrayList<>();

        // Go through initial releases and perform search.
        for (ApiArtist artist : artists) {

            // Content to search through (in lower case).
            String content = artist.getName().toLowerCase();

            for (String word : queryByWords) {
                // There is a match only if all of the words are contained.
                int numberOfMatches = queryByWords.length;

                // All query words have to be contained,
                // otherwise the release is filtered out.
                if (content.contains(word)) {
                    numberOfMatches--;
                } else {
                    break;
                }

                // They all match.
                if (numberOfMatches == 0) {
                    artistsFiltered.add(artist);
                }
            }
        }

        return artistsFiltered;
    }


//    The creation of the loader to load the data from the server
    @Override
    public Loader<ArrayList<ApiArtist>> onCreateLoader(int id, Bundle args) {
        ArtistAsyncLoader loader = null;
        if (id == ARTISTS_LOADER_ID) {
            loader = new ArtistAsyncLoader(this);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ApiArtist>> loader, ArrayList<ApiArtist> data) {
        ArtistAsyncLoader artistLoader = (ArtistAsyncLoader) loader;
        if (artistLoader.isNetworkException()) {
            Toast.makeText(this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
        }
        if (data != null) {
            adapter.changeData(data);
        }
        layout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ApiArtist>> loader) {

    }
}
