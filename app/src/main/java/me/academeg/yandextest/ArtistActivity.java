package me.academeg.yandextest;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import me.academeg.yandextest.Api.ApiArtist;


public class ArtistActivity extends AppCompatActivity {

    private ImageView avatarIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        avatarIV = (ImageView) findViewById(R.id.iv_avatar);
        TextView genresTV = (TextView) findViewById(R.id.tv_genres);
        TextView countAlbumsTracksTV = (TextView) findViewById(R.id.tv_count_albums_tracks);
        TextView titleBiographyTV = (TextView) findViewById(R.id.tv_title_biography);
        TextView biographyTV = (TextView) findViewById(R.id.tv_biography);

        ApiArtist artist = getIntent().getParcelableExtra("artist");
        Bitmap thumbAvBitmap = getIntent().getParcelableExtra("avatar");
        if (thumbAvBitmap != null) {
            avatarIV.setImageBitmap(thumbAvBitmap);
            setUpAvatarImageView();
        }
        if (artist != null) {
            toolbar.setTitle(artist.getName());
            if (artist.getAvatar() != null) {
//                Load original avatar
                Picasso.with(this)
                        .load(artist.getAvatar().getOriginalPath())
                        .noPlaceholder()
                        .into(avatarIV, new Callback() {
                            @Override
                            public void onSuccess() {
                                setUpAvatarImageView();
                            }

                            @Override
                            public void onError() {

                            }
                        });
            }

            if (genresTV != null) {
                genresTV.setText(TextUtils.join(", ", artist.getGenres()));
            }

            String album = getResources().getQuantityString(R.plurals.album,
                    artist.getCountAlbums(),
                    artist.getCountAlbums());

            String track = getResources().getQuantityString(R.plurals.track,
                    artist.getCountTracks(),
                    artist.getCountTracks());

            countAlbumsTracksTV.setText(String.format("%s   •   %s", album, track));

            if (artist.getDescription() != null) {
                biographyTV.setText(String.format("%s %s", artist.getName(),
                        artist.getDescription()));
            } else {
                biographyTV.setVisibility(View.GONE);
                titleBiographyTV.setVisibility(View.GONE);
            }
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpAvatarImageView() {
        avatarIV.setPadding(0, 0, 0, 0);
        avatarIV.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }
}
