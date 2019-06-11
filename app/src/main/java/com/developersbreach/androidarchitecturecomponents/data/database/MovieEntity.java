package com.developersbreach.androidarchitecturecomponents.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "table_movie")
public class MovieEntity {

    @PrimaryKey
    private final int id;
    private final String title;
    private final String imageView;

    public MovieEntity(int id, String title, String imageView) {
        this.id = id;
        this.title = title;
        this.imageView = imageView;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageView() {
        return imageView;
    }
}
