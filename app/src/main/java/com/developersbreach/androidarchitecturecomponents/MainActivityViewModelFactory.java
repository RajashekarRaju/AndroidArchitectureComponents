package com.developersbreach.androidarchitecturecomponents;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.developersbreach.androidarchitecturecomponents.data.MovieRepository;
import com.developersbreach.androidarchitecturecomponents.viewModel.MainActivityViewModel;


public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRepository mRepository;

    public MainActivityViewModelFactory(MovieRepository repository) {
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository);
    }
}
