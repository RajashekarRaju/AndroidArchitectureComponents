package com.developersbreach.androidarchitecturecomponents.utils;

import android.content.Context;

import com.developersbreach.androidarchitecturecomponents.AppExecutors;
import com.developersbreach.androidarchitecturecomponents.MainActivityViewModelFactory;
import com.developersbreach.androidarchitecturecomponents.data.MovieRepository;
import com.developersbreach.androidarchitecturecomponents.data.database.MovieDatabase;
import com.developersbreach.androidarchitecturecomponents.data.network.MovieNetworkDataSource;

public class InjectorUtils {

    // Create single instance for repository class from rest of class instances created.
    private static MovieRepository provideRepository(Context context) {
        MovieDatabase database = MovieDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        MovieNetworkDataSource networkDataSource =
                MovieNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return MovieRepository.getInstance(database.movieDao(), networkDataSource, executors);
    }

    public static MainActivityViewModelFactory mainActivityViewModelFactory(Context context) {
        MovieRepository repository = provideRepository(context.getApplicationContext());
        return new MainActivityViewModelFactory(repository);
    }
}
