package com.example.movlist;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);

        ArrayList<Movie> movies = JSONUtility.loadMovies(this);
        if (movies.isEmpty()) {
            Toast.makeText(this, "Failed to load movies.", Toast.LENGTH_LONG).show();
        }

        MovieAdapter adapter = new MovieAdapter(this, movies);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovies.setAdapter(adapter);
    }
}