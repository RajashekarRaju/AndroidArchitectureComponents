package com.developersbreach.androidarchitecturecomponents.data.network;

import android.text.TextUtils;
import android.util.Log;

import com.developersbreach.androidarchitecturecomponents.data.database.MovieEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class MovieJsonParser {

    // Url to get posterPath to display into ImageView
    private static final String URL_POSTER_PATH = "http://image.tmdb.org/t/p/w500";

    /**
     * Return a list of {@link MovieEntity} objects that has been built up from parsing a JSON response.
     */
    static List<MovieEntity> extractMovieDataFromJson(String newJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding new to
        List<MovieEntity> movieEntityList = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newJSON);
            // Extract the JSONArray associated with the key called "results",
            // which represents a list of features (or new).
            JSONArray newArray = baseJsonResponse.getJSONArray("results");

            for (int i = 0; i < newArray.length(); i++) {

                JSONObject jsonObject = newArray.getJSONObject(i);

                // Extract the value for the key called "original_title"
                int id = 0;
                if (jsonObject.has("id")) {
                    id = jsonObject.getInt("id");
                }

                // Extract the value for the key called "original_title"
                String title = null;
                if (jsonObject.has("original_title")) {
                    title = jsonObject.getString("original_title");
                }

                // Extract the value for the key called "poster_path"
                String posterPath = null;
                if (jsonObject.has("poster_path")) {
                    posterPath = jsonObject.getString("poster_path");
                }

                // Appending posterPath value to URL_POSTER_PATH to fetch ImageView correctly
                String imagePosterPath = URL_POSTER_PATH + posterPath;

                // Create a new {@link MovieEntity} object with the id, title, imagePosterPath from the
                // JSON response.
                MovieEntity movieEntity = new MovieEntity(id, title, imagePosterPath);

                // Add the new {@link MovieEntity} to the list of movies.
                movieEntityList.add(movieEntity);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the new JSON results", e);
        }
        // Return the list of Movies
        return movieEntityList;
    }
}
