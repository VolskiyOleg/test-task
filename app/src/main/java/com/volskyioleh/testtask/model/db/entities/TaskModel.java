package com.volskyioleh.testtask.model.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TaskModel {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private long mDueDate;
    private String mCaption;
    private TaskStatus mStatus;

    public TaskModel(long mDueDate, String mCaption, TaskStatus mStatus) {
        this.mDueDate = mDueDate;
        this.mCaption = mCaption;
        this.mStatus = mStatus;
    }

    public int getId() {
        return id;
    }

    public long getDueDate() {
        return mDueDate;
    }

    public void setDueDate(long mDueDate) {
        this.mDueDate = mDueDate;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public TaskStatus getStatus() {
        return mStatus;
    }

    public void setStatus(TaskStatus mStatus) {
        this.mStatus = mStatus;
    }

    public void setId(int id) {
        this.id = id;
    }
}
