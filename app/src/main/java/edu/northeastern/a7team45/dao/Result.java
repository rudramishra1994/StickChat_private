package edu.northeastern.a7team45.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {


    @SerializedName("Search")
    private List<Movie> Search;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("Response")
    private String Response;


    public List<Movie> getSearch() {
        return Search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return Response;
    }



    @Override
    public String toString() {
        return "Result{" +
                "Search=" + Search +
                ", totalResults='" + totalResults + '\'' +
                ", Response='" + Response + '\'' +
                '}';
    }
}
