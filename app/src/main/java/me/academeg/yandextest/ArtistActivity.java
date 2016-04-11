package me.academeg.yandextest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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


public class ArtistActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String
            EXTRA_CUSTOM_TABS_SESSION = "android.support.customtabs.extra.SESSION";

    private static final String
            EXTRA_CUSTOM_TABS_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";

    private ApiArtist artist;

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
        TextView linkTV = (TextView) findViewById(R.id.tv_link);
        linkTV.setOnClickListener(this);

        artist = getIntent().getParcelableExtra("artist");
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

            if (artist.getLink() != null) {
                linkTV.setText(artist.getLink());
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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_link) {
            if (artist.getLink() == null) {
                return;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(artist.getLink()));
            Bundle extras = new Bundle();
            extras.putBinder(EXTRA_CUSTOM_TABS_SESSION, null);
            extras.putInt(EXTRA_CUSTOM_TABS_TOOLBAR_COLOR,
                    ContextCompat.getColor(this, R.color.colorPrimary));
            intent.putExtras(extras);
            startActivity(intent);
        }
    }
}
