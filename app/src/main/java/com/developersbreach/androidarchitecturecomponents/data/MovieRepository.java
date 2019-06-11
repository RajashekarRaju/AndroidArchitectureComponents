package com.developersbreach.androidarchitecturecomponents.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.developersbreach.androidarchitecturecomponents.AppExecutors;
import com.developersbreach.androidarchitecturecomponents.data.database.MovieDao;
import com.developersbreach.androidarchitecturecomponents.data.database.MovieEntity;
import com.developersbreach.androidarchitecturecomponents.data.network.MovieNetworkDataSource;

import java.util.List;

public class MovieRepository {

    private static final String LOG_TAG = MovieRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private MovieDao mMovieDao;
    private static MovieRepository sInstance;
    private MovieNetworkDataSource mMovieNetworkDataSource;
    private final AppExecutors mExecutors;

    private MovieRepository(MovieDao movieDao, MovieNetworkDataSource movieNetworkDataSource, AppExecutors executors) {
        mMovieDao = movieDao;
        mMovieNetworkDataSource = movieNetworkDataSource;
        mExecutors = executors;

        LiveData<List<MovieEntity>> liveData = mMovieNetworkDataSource.getDownloadedMovies();
        liveData.observeForever(new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable final List<MovieEntity> movieEntityList) {
                mExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mMovieDao.deleteAllMovies();
                        mMovieDao.insertMovies(movieEntityList);
                    }
                });
            }
        });
    }

    public synchronized static MovieRepository getInstance(
            MovieDao movieDao, MovieNetworkDataSource movieNetworkDataSource, AppExecutors executors) {

        Log.e(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieRepository(movieDao, movieNetworkDataSource,
                        executors);
                Log.e(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public LiveData<List<MovieEntity>> getMovieLiveData() {
        return mMovieDao.getAllMovies();
    }
}
