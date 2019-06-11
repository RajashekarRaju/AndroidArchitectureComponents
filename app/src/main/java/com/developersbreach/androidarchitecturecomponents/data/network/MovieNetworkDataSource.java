package com.developersbreach.androidarchitecturecomponents.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.developersbreach.androidarchitecturecomponents.AppExecutors;
import com.developersbreach.androidarchitecturecomponents.data.database.MovieEntity;
import com.developersbreach.androidarchitecturecomponents.utils.QueryUtils;

import java.net.URL;
import java.util.List;

public class MovieNetworkDataSource {

    private static final String LOG_TAG = MovieNetworkDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private final Context mContext;
    private static MovieNetworkDataSource INSTANCE;

    private final MutableLiveData<List<MovieEntity>> mDownloadedMovies;
    private final AppExecutors mExecutors;

    private MovieNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedMovies = new MutableLiveData<List<MovieEntity>>();
        fetchMovies();
    }

    /**
     * Get the singleton for this class
     */
    public static MovieNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.e(LOG_TAG, "Getting the network data source");
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new MovieNetworkDataSource(context.getApplicationContext(), executors);
                Log.e(LOG_TAG, "Made new network data source");
            }
        }
        return INSTANCE;
    }

    public LiveData<List<MovieEntity>> getDownloadedMovies() {
        return mDownloadedMovies;
    }

    private void fetchMovies() {
        Log.e(LOG_TAG, "Fetch movies started");
        mExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL requestUrl = QueryUtils.createUrl(QueryUtils.uriBuilder());
                    String responseString = QueryUtils.getResponseFromHttpUrl(requestUrl);
                    List<MovieEntity> movieList = MovieJsonParser.extractMovieDataFromJson(responseString);
                    mDownloadedMovies.postValue(movieList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
