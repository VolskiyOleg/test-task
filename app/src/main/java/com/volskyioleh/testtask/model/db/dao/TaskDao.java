package com.volskyioleh.testtask.model.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.volskyioleh.testtask.model.db.entities.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {


    @Query("SELECT * FROM TaskModel ORDER BY mDueDate ASC")
    LiveData<List<TaskModel>> getAll();

    @Query("SELECT * FROM TaskModel WHERE id= :id ORDER BY mDueDate ASC ")
    LiveData<TaskModel> getTaskById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskModel taskModels);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(TaskModel taskModels);

    @Delete
    int delete(TaskModel taskModel);

}
