package me.academeg.yandextest.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.academeg.yandextest.Api.ApiArtist;
import me.academeg.yandextest.R;


public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private ArrayList<ApiArtist> mDataSet;
    private Context mContext;
    private ItemClickListener clickListener;


    public ArtistsAdapter(Context mContext, ArrayList<ApiArtist> mDataSet) {
        this.mContext = mContext;
        this.mDataSet = mDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_artist, parent, false);

        ViewHolder vh = new ViewHolder(v);
        vh.setClickListener(clickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ApiArtist artist = mDataSet.get(position);

        holder.nameTV.setText(artist.getName());

        String album = mContext.getResources().getQuantityString(R.plurals.album,
                artist.getCountAlbums(),
                artist.getCountAlbums());

        String track = mContext.getResources().getQuantityString(R.plurals.track,
                artist.getCountTracks(),
                artist.getCountTracks());

        holder.albumsTracksCountTV.setText(String.format("%s, %s", album, track));

//        set genres tv
        if(artist.getGenres() != null) {
            String join = TextUtils.join(", ", artist.getGenres());
            holder.genresTV.setText(join);
        }

//        load artist avatar
        if (artist.getAvatar() != null) {
            Picasso.with(mContext)
                    .load(artist.getAvatar().getThumbnailPath())
                    .placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_account_box_24dp))
                    .into(holder.avatarIV);
        } else {
            holder.avatarIV.setImageDrawable(
                    ContextCompat.getDrawable(mContext, R.drawable.ic_account_box_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet != null ? mDataSet.size() : 0;
    }

    public ArrayList<ApiArtist> getData() {
        return mDataSet;
    }

    public void changeData(ArrayList<ApiArtist> dataSet) {
        this.mDataSet = dataSet;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView avatarIV;
        public TextView nameTV;
        public TextView genresTV;
        public TextView albumsTracksCountTV;
        private RelativeLayout itemArtistRL;

        private ItemClickListener clickListener;


        public ViewHolder(View itemView) {
            super(itemView);
            avatarIV = (ImageView) itemView.findViewById(R.id.iv_avatar);
            nameTV = (TextView) itemView.findViewById(R.id.tv_name);
            genresTV = (TextView) itemView.findViewById(R.id.tv_genres);
            albumsTracksCountTV = (TextView) itemView.findViewById(R.id.tv_count_albums_tracks);

            itemArtistRL = (RelativeLayout) itemView.findViewById(R.id.rl_item_artist);
            itemArtistRL.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(v, getAdapterPosition());
            }
        }
    }
}
