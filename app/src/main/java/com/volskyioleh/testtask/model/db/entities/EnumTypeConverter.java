package com.volskyioleh.testtask.model.db.entities;

import android.arch.persistence.room.TypeConverter;

public class EnumTypeConverter {

    @TypeConverter
    public static String fromCategoryToString(TaskStatus taskStatus) {
        if (taskStatus == null)
            return null;
        return taskStatus.toString();
    }

    @TypeConverter
    public static TaskStatus fromStringToCategory(String  taskStatus) {
        if (taskStatus == null)
            return null;
        return TaskStatus.valueOf(taskStatus.toUpperCase());
    }
}
