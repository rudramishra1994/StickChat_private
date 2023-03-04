package edu.northeastern.a7team45;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.a7team45.dao.Movie;
import edu.northeastern.a7team45.dao.MovieDetail;
import edu.northeastern.a7team45.dao.Result;
import edu.northeastern.a7team45.dao.ResultWithMovieDetails;
import edu.northeastern.a7team45.util.ActivityUtils;

public class ServiceActivity extends AppCompatActivity {

    private EditText searchEditTitle;
    private List<MovieDetail> queriedMovies;
    private EditText searchEditYear;
    private Button searchButton;
    private Button resetButton;
    private ProgressBar progressBar;
    private MovieRecyclerViewAdapter movieAdapter;
    private RecyclerView movieListRecyclerView;
    private static final String TAG = "ServiceActivity";
    private  String api_key;

    private Context serviceActivityContext;

    private String base_url;
    private  String movieDetailByIdURL;
    private Handler movieHandler = new Handler();
    private boolean isSearchComplete = false;

    private String allMovieSearchURL;
    private ResultWithMovieDetails resultWithMovieDetails;
    Thread searchMovieThreadByTitle;
    GetMoviesThreadRunnable getMoviesThreadRunnable;

    private volatile boolean movieFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceActivityContext = this;
        setContentView(R.layout.service_activity);


        getMoviesThreadRunnable = new GetMoviesThreadRunnable();

        base_url = getResources().getString(R.string.allmovieSearch_url);
        movieDetailByIdURL =getResources().getString(R.string.moviedetail_url);
        api_key = getResources().getString(R.string.api_key);

        Spinner spinner = findViewById(R.id.my_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        searchEditTitle = findViewById(R.id.title_search);
        searchEditYear = findViewById(R.id.year_search);


        movieListRecyclerView = findViewById(R.id.appusersview);
        searchButton = findViewById(R.id.search_button);

        if(savedInstanceState!=null){
            if(!savedInstanceState.getString("title").isBlank())
                searchEditTitle.setText(savedInstanceState.getString("title"));
            if(!savedInstanceState.getString("year").isBlank())
                searchEditYear.setText(savedInstanceState.getString("year"));
            queriedMovies = (ArrayList)savedInstanceState.getSerializable("movieList");
            if(queriedMovies!=null && queriedMovies.size()>0) movieListRecyclerView.setVisibility(View.VISIBLE);
            else queriedMovies = new ArrayList<>();

            if(savedInstanceState.getBoolean("searchInProgress")){
                createThread();
            }
        }else{
            queriedMovies = new ArrayList<>();
        }
        searchButton.setOnClickListener(v -> {
            if(searchEditTitle!=null && searchEditTitle.getText().toString()!=null && !searchEditTitle.getText().toString().isBlank()){
                ActivityUtils.hideSoftKeyboard(ServiceActivity.this);
                progressBar.setVisibility(View.VISIBLE);
                movieListRecyclerView.setVisibility(View.GONE);
                String title = searchEditTitle.getText().toString();
                String year = searchEditYear.getText().toString();
                String  type = spinner.getSelectedItem().toString();
                //allMovieSearchURL= String.format(allMovieSearchURL,api_key,searchEditTitle.getText().toString(),searchEditYear.getText().toString(),spinner.getSelectedItem().toString());
                allMovieSearchURL= String.format(base_url,api_key,title,year,type);
                createThread();
            }else{
                Snackbar.make(v, R.string.no_title, Snackbar.LENGTH_LONG)
                        .show();
            }
            });

        resetButton = findViewById(R.id.reset_button);

        resetButton.setOnClickListener(v -> resetChanges());

        movieAdapter = new MovieRecyclerViewAdapter(queriedMovies, this);
        movieListRecyclerView.setAdapter(movieAdapter);
        // The first parameter is number of columns and second parameter is orientation i.e Vertical or Horizontal

        int numColumns = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 4;

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(numColumns, StaggeredGridLayoutManager.VERTICAL);
        movieListRecyclerView.setItemAnimator(null);
        // Attach the layout manager to the recycler view
        movieListRecyclerView.setLayoutManager(gridLayoutManager);
        progressBar = findViewById(R.id.progress_spinner);
    }

    private void resetChanges() {
        //emptying title and year.
        searchEditTitle.setText("");
        searchEditYear.setText("");
        movieAdapter.swapData(null);
        movieListRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("progress_visibility",progressBar.getVisibility());
        outState.putBoolean("isSearchComplete",isSearchComplete);
        if(searchEditTitle!=null){
            outState.putString("title",searchEditTitle.getText().toString());
        }
        if(searchEditYear!=null){
            outState.putString("year",searchEditYear.getText().toString());
        }
        if(searchMovieThreadByTitle!=null && searchMovieThreadByTitle.isAlive()){
            outState.putBoolean("searchInProgress",searchMovieThreadByTitle.isAlive());
        }
        if(movieAdapter!=null && movieAdapter.getmValues()!=null){
            outState.putSerializable("movieList", new ArrayList<>(movieAdapter.getmValues()));
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        int progress_visibility= savedInstanceState.getInt("progress_visibility");
        // if the progressBar was visible before orientation-change
        if(progress_visibility == View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }
        // init the loader, so that the onLoadFinished is called
        //responseList = savedInstanceState.getParcelableArrayList("responseList");
//        if (movieTitle != null) {
//            Bundle args = new Bundle();
//            args.putParcelableArrayList("responseList", new ArrayList<searchService.Detail>(responseList));
//            getSupportLoaderManager().initLoader(LOADER_ID, args, this);
//        }
    }

    class GetMoviesThreadRunnable implements Runnable {

        private volatile Boolean isRunning = true;

        public void setRunning(Boolean running) {
            isRunning = running;
        }

        public  void terminate(){
            isRunning = false;
        }
        @Override
        public void run() {


            movieFound = true;
            if (isRunning) {
                try {
                    Result result ;
                    URL url = new URL(allMovieSearchURL);
                    String resp = ActivityUtils.httpResponse(url);
                    if(!resp.contains("Error")) {
                        Gson gson = new Gson();
                        result = gson.fromJson(resp, Result.class);
                        resultWithMovieDetails = new ResultWithMovieDetails(result);
                        if(result.getSearch()!=null){
                            for(Movie movie : result.getSearch()){
                                URL movieDetailURL = new URL(String.format(movieDetailByIdURL,api_key,movie.imdbID));
                                String movieDetailString = ActivityUtils.httpResponse(movieDetailURL);
                                MovieDetail movieDetail = gson.fromJson(movieDetailString,MovieDetail.class);
                                resultWithMovieDetails.addToList(movieDetail);

                            }

                        }

                    }else{
                        movieFound =false;
                    }

                } catch (MalformedURLException e) {
                    Log.e(TAG,"MalformedURLException");
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    Log.e(TAG,"ProtocolException");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e(TAG,"IOException");
                    e.printStackTrace();
                }
                final boolean movieExists = movieFound;
                movieHandler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    movieListRecyclerView.setVisibility(View.VISIBLE);
                    if(movieExists) {
                         movieAdapter.swapData(resultWithMovieDetails.getMovieDetailList());
                        movieListRecyclerView.setVisibility(View.VISIBLE);
                        isSearchComplete = true;
                    }
//                    else
//                        Snackbar.make(serviceActivityContext, R.string.no_title, Snackbar.LENGTH_LONG)
//                                .show();

                });
            }
        }

    }


    private void createThread(){
        if(searchMovieThreadByTitle==null || (searchMovieThreadByTitle !=null && !searchMovieThreadByTitle.isAlive()) ||
                (searchMovieThreadByTitle!=null && searchMovieThreadByTitle.getState().toString().equalsIgnoreCase("terminated"))){
            getMoviesThreadRunnable.setRunning(true);
            searchMovieThreadByTitle = new Thread(getMoviesThreadRunnable);
            searchMovieThreadByTitle.start();
        }
    }


}
