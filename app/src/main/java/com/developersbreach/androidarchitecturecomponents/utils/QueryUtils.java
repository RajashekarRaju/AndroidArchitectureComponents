package com.developersbreach.androidarchitecturecomponents.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    public QueryUtils() {
    }

    /**
     * SCHEME_AUTHORITY = Encodes and sets the authority and scheme.
     * API_KEY = This is unique key given to the user for access from their server.
     * Obtain your API_KEY from TMDB site.
     */
    private static final String SCHEME_AUTHORITY = "https://api.themoviedb.org";
    private static final String APPEND_PATH_FIRST = "3";
    private static final String APPEND_PATH_SECOND = "movie";
    private static final String POPULAR_MOVIE_PATH = "popular";
    private static final String API_PARAM = "api_key";
    private static final String API_KEY = "YOUR_API_KEY";

    /**
     * Builds Uri used to fetch movie data from the server. This data is based on the query
     * capabilities of the movie database provider that we are using.
     * API_KEY is used to query specific data from the server
     * @return The String to use to query the movie database server.
     */
    public static String uriBuilder() {

        Uri baseUri = Uri.parse(SCHEME_AUTHORITY);
        // Constructs a new Builder.
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder
                .appendPath(APPEND_PATH_FIRST)
                .appendPath(APPEND_PATH_SECOND)
                .appendPath(POPULAR_MOVIE_PATH)
                .appendQueryParameter(API_PARAM, API_KEY);
        // Returns a string representation of the object.
        return uriBuilder.build().toString();
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
}
