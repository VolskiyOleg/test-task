package com.volskyioleh.testtask.view.main;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.volskyioleh.testtask.R;
import com.volskyioleh.testtask.model.db.entities.TaskModel;
import com.volskyioleh.testtask.view.create_task.CreateTaskActivity;
import com.volskyioleh.testtask.view.main.adapter.TasksListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_list_tv)
    TextView emptyListTv;

    private TasksListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenter(this);

        mAdapter = new TasksListAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);
        mPresenter.onListLoad(this);
    }

    @OnClick(R.id.addTaskBtn)
    public void onClickBtn() {
        startActivity(new Intent(this, CreateTaskActivity.class));
    }

    @Override
    public void showList(LiveData<List<TaskModel>> taskModelsList) {
        taskModelsList.observe(this, taskModels -> {
            if (taskModels == null || taskModels.isEmpty()){
                emptyListTv.setVisibility(View.VISIBLE);
            }else {
                mAdapter.setTaskList(taskModels);
                emptyListTv.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onListLoad(this);
        }
    }
}
