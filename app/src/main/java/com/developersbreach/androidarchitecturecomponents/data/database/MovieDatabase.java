package com.developersbreach.androidarchitecturecomponents.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
    private static final String DATABASE_NAME = "movie_data";
    private static final String LOG_TAG = MovieDatabase.class.getSimpleName();
    private static volatile MovieDatabase INSTANCE;

    public static MovieDatabase getInstance(final Context context) {
        Log.e(LOG_TAG, "Getting the database");
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, DATABASE_NAME)
                            .build();
                    Log.e(LOG_TAG, "Made new database");
                }
            }
        }
        return INSTANCE;
    }
}
