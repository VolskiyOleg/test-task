package com.volskyioleh.testtask.view.create_task;

import android.content.Context;

import com.volskyioleh.testtask.model.db.entities.TaskModel;
import com.volskyioleh.testtask.repository.Repository;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class CreateTaskPresenter implements CreateTaskContract.Presenter {
    private static final String TAG = "MainPresenter";


    private CreateTaskContract.View mView;


    public CreateTaskPresenter(CreateTaskContract.View mView) {
        this.mView = mView;

    }


    @Override
    public void createTask(Context context, TaskModel taskModel) {
        Completable.fromAction(() -> {
            Repository.getReposetory().insertNewTask(context, taskModel);
            mView.done();
        })
                .subscribeOn(Schedulers.io())
                .subscribe();

    }
}
