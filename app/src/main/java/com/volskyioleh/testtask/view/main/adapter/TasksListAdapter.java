package com.volskyioleh.testtask.view.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.volskyioleh.testtask.R;
import com.volskyioleh.testtask.model.db.entities.TaskModel;
import com.volskyioleh.testtask.view.create_task.CreateTaskActivity;
import com.volskyioleh.testtask.view.task_details.TaskDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.TaskListHolder> {

    private final Context mContext;
    private List<TaskModel> mArticles;

    public TasksListAdapter(List<TaskModel> itemsList, Context context) {
        mArticles = itemsList;
        mContext = context;
    }

    @NonNull
    @Override
    public TaskListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new TaskListHolder(view);
    }

    public void setTaskList(List<TaskModel> itemsList) {
        mArticles = itemsList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListHolder taskListHolder, int position) {
        TaskModel model = mArticles.get(position);
        taskListHolder.mTitleTv.setText(model.getCaption());
        taskListHolder.mDateTv.setText(getDate(model.getDueDate()));
        taskListHolder.itemView.setOnClickListener(v -> {
            int id = model.getId();
            mContext.startActivity(new Intent(mContext, TaskDetailsActivity.class).putExtra(TaskDetailsActivity.ID_EXTRA, model.getId()));
        });
    }

    public String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class TaskListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.byLineTv)
        TextView mTitleTv;

        @BindView(R.id.dateTv)
        TextView mDateTv;

        TaskListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
