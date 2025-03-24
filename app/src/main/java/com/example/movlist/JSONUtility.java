package com.example.movlist;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class JSONUtility {
    public static ArrayList<Movie> loadMovies(Context context) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.movies);
            Scanner scanner = new Scanner(is);
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) builder.append(scanner.nextLine());
            scanner.close();

            JSONArray jsonArray = new JSONArray(builder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    // Title check
                    String title = obj.optString("title", null);
                    if (title == null || title.trim().isEmpty()) {
                        Log.w("JSON_WARNING", "Skipping entry: Missing or empty title");
                        continue;
                    }

                    // Year check
                    int year = -1;
                    Object yearObj = obj.opt("year");
                    if (yearObj instanceof Integer) {
                        year = (Integer) yearObj;
                    } else if (yearObj instanceof String) {
                        try {
                            year = Integer.parseInt((String) yearObj);
                        } catch (NumberFormatException ignored) {}
                    }

                    if (year < 1880 || year > 2100) {
                        Log.w("JSON_WARNING", "Skipping entry: Invalid year for " + title);
                        continue;
                    }

                    // Genre check
                    String genre = obj.optString("genre", null);
                    if (genre == null || genre.trim().isEmpty()) {
                        Log.w("JSON_WARNING", "Skipping entry: Missing genre for " + title);
                        continue;
                    }

                    // Poster check
                    String poster = obj.optString("poster", null);
                    if (poster == null || poster.trim().isEmpty()) {
                        Log.w("JSON_WARNING", "Missing poster for " + title + ". Using placeholder.");
                        poster = "poster_placeholder.png";
                    }

                    movies.add(new Movie(title, year, genre, poster));
                } catch (Exception e) {
                    Log.e("JSON_ERROR", "Invalid entry at index " + i + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            Log.e("JSON_ERROR", "Error loading JSON: " + e.getMessage());
        }
        return movies;
    }
}