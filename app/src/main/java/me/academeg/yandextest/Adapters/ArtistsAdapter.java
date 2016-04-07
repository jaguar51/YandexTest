package me.academeg.yandextest.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.academeg.yandextest.R;


public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private ItemClickListener clickListener;


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

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView avatarIV;
        public TextView nameTV;
        public TextView albumsCountTV;
        private RelativeLayout itemArtistRL;

        private ItemClickListener clickListener;


        public ViewHolder(View itemView) {
            super(itemView);
            avatarIV = (ImageView) itemView.findViewById(R.id.iv_avatar);
            nameTV = (TextView) itemView.findViewById(R.id.tv_name);
            albumsCountTV = (TextView) itemView.findViewById(R.id.tv_count_albums_tracks);

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
