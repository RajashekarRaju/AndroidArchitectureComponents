package com.developersbreach.androidarchitecturecomponents.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


import com.developersbreach.androidarchitecturecomponents.data.MovieRepository;
import com.developersbreach.androidarchitecturecomponents.data.database.MovieEntity;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private LiveData<List<MovieEntity>> mMovieLiveData;
    private MovieRepository mMovieRepository;

    public MainActivityViewModel(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
        mMovieLiveData = mMovieRepository.getMovieLiveData();
    }

    public LiveData<List<MovieEntity>> getMovieData() {
        return mMovieLiveData;
    }
}
