package com.example.movlist;

public class Movie {
    private String title;
    private int year;
    private String genre;
    private String poster;

    public Movie(String title, int year, String genre, String poster) {
        this.title = title != null ? title : "Untitled";
        this.year = (year >= 1880 && year <= 2100) ? year : -1;
        this.genre = genre != null ? genre : "Unknown";
        this.poster = poster != null ? poster : "poster_placeholder";
    }

    public String getTitle() { return title; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public String getPoster() { return poster; }
}