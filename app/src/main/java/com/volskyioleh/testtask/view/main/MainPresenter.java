package com.volskyioleh.testtask.view.main;

import android.content.Context;

import com.volskyioleh.testtask.repository.Repository;

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";


    private MainContract.View mView;


    public MainPresenter(MainContract.View mView) {
        this.mView = mView;

    }


    @Override
    public void onListLoad(Context context) {
        mView.showList(Repository.getReposetory().loadList(context));

    }

    @Override
    public void onDestroy() {
    }
}
