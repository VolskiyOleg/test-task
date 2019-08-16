package com.volskyioleh.testtask.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.volskyioleh.testtask.model.db.TaskDB;
import com.volskyioleh.testtask.model.db.entities.TaskModel;

import java.util.List;

public class Repository {
    private static Repository mInstance;

    public static Repository getReposetory() {
        if (mInstance == null) {
            mInstance = new Repository();
        }
        return mInstance;
    }

    public LiveData<List<TaskModel>> loadList(Context context) {
        return TaskDB.getDatabase(context).taskDao().getAll();
    }

    public void insertNewTask(Context context, TaskModel taskModel) {
        TaskDB.getDatabase(context).taskDao().insert(taskModel);
    }

    public LiveData<TaskModel> getTaskById(Context context, int id) {
        return TaskDB.getDatabase(context).taskDao().getTaskById(id);
    }

    public void updateTask(Context context, TaskModel taskModel) {
        TaskDB.getDatabase(context).taskDao().update(taskModel);
    }
}
