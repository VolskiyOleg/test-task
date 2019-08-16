package com.volskyioleh.testtask.view.main;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.volskyioleh.testtask.model.db.entities.TaskModel;

import java.util.List;

public interface MainContract {
    interface View {
        void showList(LiveData<List<TaskModel>> taskModel);
    }

    interface Presenter {
        void onListLoad(Context context);
        void onDestroy();
    }
}
