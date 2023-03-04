package edu.northeastern.a7team45;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import edu.northeastern.a7team45.Services.SearchService;
import edu.northeastern.a7team45.dao.MovieDetail;

public class DetailViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final MovieDetail movieDetails = (MovieDetail) getIntent().getSerializableExtra("movie_detail");
        final String imageUrl =  getIntent().getStringExtra("poster");
        Picasso.with(this).load(imageUrl).into((ImageView) findViewById(R.id.main_backdrop));
        // set title for the appbar
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.main_collapsing);
        collapsingToolbarLayout.setTitle(movieDetails.getTitle());
        ((TextView) findViewById(R.id.grid_title)).setText(movieDetails.getTitle());
        ((TextView) findViewById(R.id.grid_writers)).setText(movieDetails.getWriter());
        ((TextView) findViewById(R.id.grid_actors)).setText(movieDetails.getActors());
        ((TextView) findViewById(R.id.grid_director)).setText(movieDetails.getDirector());
        ((TextView) findViewById(R.id.grid_genre)).setText(movieDetails.getGenre());
        ((TextView) findViewById(R.id.grid_released)).setText(movieDetails.getReleased());
        ((TextView) findViewById(R.id.grid_plot)).setText(movieDetails.getPlot());
        ((TextView) findViewById(R.id.grid_runtime)).setText(movieDetails.getRuntime());

    }

}
