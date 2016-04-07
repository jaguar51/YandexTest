package me.academeg.yandextest;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import me.academeg.yandextest.Adapters.ArtistsAdapter;
import me.academeg.yandextest.Components.SimpleDividerItemDecoration;


public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView artistsRV;
    private ArtistsAdapter adapter;

    private SwipeRefreshLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.artists);
        setSupportActionBar(toolbar);

        adapter = new ArtistsAdapter();
        artistsRV = (RecyclerView) findViewById(R.id.rr_artists_list);
        artistsRV.setLayoutManager(new LinearLayoutManager(this));
        artistsRV.setAdapter(adapter);
        SimpleDividerItemDecoration divider = new SimpleDividerItemDecoration(this);
        artistsRV.addItemDecoration(divider);

        layout = (SwipeRefreshLayout) findViewById(R.id.swl_layout);
        layout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

    }
}
