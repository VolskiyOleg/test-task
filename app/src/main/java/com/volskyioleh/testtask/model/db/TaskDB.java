package com.volskyioleh.testtask.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.volskyioleh.testtask.model.db.dao.TaskDao;
import com.volskyioleh.testtask.model.db.entities.EnumTypeConverter;
import com.volskyioleh.testtask.model.db.entities.TaskModel;

@Database(entities = { TaskModel.class}, version = TaskDB.VERSION)
@TypeConverters({ EnumTypeConverter.class })
public abstract class TaskDB extends RoomDatabase {

    static final int VERSION = 1;
    private static TaskDB mInstance;
    public static TaskDB getDatabase(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), TaskDB.class, "task_db")
                    .build();
        }
        return mInstance;
    }

        public static void destroyInstances() {
        mInstance = null;
    }

    public abstract TaskDao taskDao();

}

