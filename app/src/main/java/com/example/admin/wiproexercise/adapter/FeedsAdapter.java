package com.example.admin.wiproexercise.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.wiproexercise.R;
import com.example.admin.wiproexercise.model.Row;


import java.util.List;


/**
 * Created by Maroti Mulange on 21-08-2018.
 */

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.MyViewHolder> {

    private final String TAG = getClass().getSimpleName();

    private List<Row> feedsList;
    private Context context;

    public FeedsAdapter(List<Row> feedsList) {
        this.feedsList = feedsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private ImageView ivPicture;

        public MyViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.row_view_feed_title);
            tvDescription = view.findViewById(R.id.row_view_feed_description);
            ivPicture = view.findViewById(R.id.row_view_feed_iv);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_feed, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Row feed = feedsList.get(position);

        holder.tvTitle.setText(feed.getTitle());
        holder.tvDescription.setText(feed.getDescription());

        Uri newURI;

        //Check empty images
        if (!TextUtils.isEmpty(feed.getImageHref())) {
            newURI = Uri.parse(feed.getImageHref());

            Glide.with(context)
                    .load(newURI)
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivPicture);
        } else {
            holder.ivPicture.setImageDrawable(null);
        }

    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }
}
