package edu.northeastern.a7team45.dao;

import java.util.ArrayList;
import java.util.List;

public class ResultWithMovieDetails {
    private List<MovieDetail> movieDetailList;
    private String totalResults;
    private String Response;

    public ResultWithMovieDetails(Result result) {
        this.totalResults = result.getTotalResults();
        this.Response = result.getResponse();
        movieDetailList = new ArrayList<>();
    }

    public void addToList(MovieDetail detail) {
        movieDetailList.add(detail);
    }

    public List<MovieDetail> getMovieDetailList() {
        return movieDetailList;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return Response;
    }
}
