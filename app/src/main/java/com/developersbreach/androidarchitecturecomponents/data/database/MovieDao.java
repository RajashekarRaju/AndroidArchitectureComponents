package com.developersbreach.androidarchitecturecomponents.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(List<MovieEntity> movieList);

    @Query("SELECT * From table_movie")
    LiveData<List<MovieEntity>> getAllMovies();

    @Query("DELETE From table_movie")
    void deleteAllMovies();
}
