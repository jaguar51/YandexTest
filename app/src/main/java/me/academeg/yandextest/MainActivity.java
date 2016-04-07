package me.academeg.yandextest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.academeg.yandextest.Adapters.ArtistsAdapter;
import me.academeg.yandextest.Adapters.ItemClickListener;
import me.academeg.yandextest.Api.ApiArtist;
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
        setSupportActionBar(toolbar);

        adapter = new ArtistsAdapter(this, null);
        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent in = new Intent(MainActivity.this, ArtistActivity.class);
                startActivity(in);
            }
        });
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
