package com.volskyioleh.testtask.view.create_task;

import android.content.Context;

import com.volskyioleh.testtask.model.db.entities.TaskModel;

public interface CreateTaskContract {
    interface View {
        void done();
    }

    interface Presenter {
        void createTask(Context context, TaskModel taskModel);
    }
}
