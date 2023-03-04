package edu.northeastern.a7team45;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import edu.northeastern.a7team45.dao.MovieDetail;

public class MovieRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecycleView";
    private List<MovieDetail> mValues;
    private final Activity serviceActivity;

    public List<MovieDetail> getmValues() {
        return mValues;
    }

    public MovieRecyclerViewAdapter(List<MovieDetail> items, Activity serviceActivity) {
        mValues = items;
        this.serviceActivity = serviceActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final MovieDetail detail = mValues.get(position);

        //change the details according to what we need from the response- Abhi.A
        final String title = detail.getTitle();
        final String imdbId = detail.getImdbID();

        final String year = detail.getYear();
        holder.mDirectorView.setText(detail.getDirector());
        holder.mTitleView.setText(title);
        holder.mYearView.setText(year);

        final String imageUrl;
        if (!detail.getPoster().equals("N/A")) {
            imageUrl = detail.getPoster();
        } else {
            imageUrl = serviceActivity.getResources().getString(R.string.default_poster);
        }
        holder.mThumbImageView.layout(0, 0, 0, 0);
        try {
            URL url = new URL(imageUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG,"MalformedURLException");
            e.printStackTrace();
        }
        holder.mThumbImageView.setImageURI(Uri.parse(imageUrl));
        Picasso.with(serviceActivity).load(imageUrl).into(holder.mThumbImageView);
        holder.mView.setOnClickListener(v -> {
            Intent intent = new Intent(serviceActivity, DetailViewActivity.class);
            // Pass data object in the bundle and populate details activity.
            intent.putExtra("movie_detail", detail);
            intent.putExtra("poster", imageUrl);

            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(serviceActivity,holder.mThumbImageView, "poster");
            serviceActivity.startActivity(intent, options.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        if (mValues == null) {
            return 0;
        }
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView mTitleView;
        public TextView mYearView;
        public  TextView mDirectorView;
        public  ImageView mThumbImageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.movie_title);
            mYearView =  view.findViewById(R.id.movie_year);
            mThumbImageView = view.findViewById(R.id.thumbnail);
            mDirectorView = view.findViewById(R.id.movie_director);
        }

    }


    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        //Glide.clear(holder.mThumbImageView);
    }

    public void swapData(List<MovieDetail> items) {
        if (items != null) {
            mValues = items;
            notifyDataSetChanged();

        } else {
            mValues = null;
        }
    }
}