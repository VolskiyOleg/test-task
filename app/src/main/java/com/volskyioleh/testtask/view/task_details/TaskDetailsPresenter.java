package com.volskyioleh.testtask.view.task_details;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.volskyioleh.testtask.model.db.entities.TaskModel;
import com.volskyioleh.testtask.repository.Repository;
import com.volskyioleh.testtask.view.main.MainContract;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class TaskDetailsPresenter implements TaskDetailsContract.Presenter {
    private static final String TAG = "MainPresenter";


    private TaskDetailsContract.View mView;


    public TaskDetailsPresenter(TaskDetailsContract.View mView) {
        this.mView = mView;

    }


    @Override
    public void getTask(Context context, int id) {
        mView.showTaskInfo(Repository.getReposetory().getTaskById(context, id));

    }

    @Override
    public void updateTask(Context context, TaskModel taskModel) {
        Completable.fromAction(() -> {
            Repository.getReposetory().updateTask(context, taskModel);
            mView.done();
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
