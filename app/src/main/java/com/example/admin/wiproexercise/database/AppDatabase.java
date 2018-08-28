package com.example.admin.wiproexercise.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.admin.wiproexercise.model.Row;

/**
 * Created by  Maroti Mulange on 27-08-2018.
 * <p>
 * Fetching data from web services and stores into the local database,
 * We will use it when mobile data is turned off.
 */

@Database(entities = {Row.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract FeedsDao feedsDao();

    public static AppDatabase getInstance(Context context) {

        String DATABASE_NAME = "wexercise.sqlite";

        if (appDatabase == null && context != null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }

        return appDatabase;
    }

    public static void destroyInstance() {
        appDatabase = null;
    }

}