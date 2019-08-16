package com.volskyioleh.testtask.view.task_details;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.volskyioleh.testtask.model.db.entities.TaskModel;

public interface TaskDetailsContract {
    interface View {
        void done();
        void showTaskInfo(LiveData<TaskModel> taskModel);
    }

    interface Presenter {
        void getTask(Context context, int id);
        void updateTask(Context context, TaskModel taskModel);
    }
}
