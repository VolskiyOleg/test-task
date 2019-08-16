package com.volskyioleh.testtask.view.task_details;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.volskyioleh.testtask.R;
import com.volskyioleh.testtask.model.db.entities.TaskModel;
import com.volskyioleh.testtask.model.db.entities.TaskStatus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskDetailsActivity extends AppCompatActivity implements TaskDetailsContract.View {
    public static final String ID_EXTRA = "id";
    private TaskDetailsContract.Presenter mPresenter;


    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.status)
    TextView statusTv;

    private String bufStringStatus;
    private TaskModel mTaskModel;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

    long lastClickTime = 0;
    final private Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        mPresenter = new TaskDetailsPresenter(this);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        mPresenter.getTask(this, bundle.getInt(ID_EXTRA));

        dateTv.setOnClickListener(v -> {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                startDatePicker();
            }
            lastClickTime = clickTime;
        });

        statusTv.setOnClickListener(v -> {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                showRadioButtonDialog();
            }
            lastClickTime = clickTime;
        });
    }


    private void startDatePicker() {
        final DatePickerDialog.OnDateSetListener datePickerListener = (view, selectedYear, selectedMonth, selectedDay) -> {
            myCalendar.set(Calendar.YEAR, selectedYear);
            myCalendar.set(Calendar.MONTH, selectedMonth);
            myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
        };
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, datePickerListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Cancel",
                (dialog, which) -> {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        myCalendar.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
                        updateLabel();
                        dialog.cancel();
                    }
                });

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "OK", (dialog, which) -> {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        DatePicker datePicker = datePickerDialog
                                .getDatePicker();
                        datePickerListener.onDateSet(datePicker,
                                datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth());
                        updateLabel();
                        dialog.dismiss();
                    }
                });
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        dateTv.setText(sdf.format(myCalendar.getTime()));
        mTaskModel.setDueDate(myCalendar.getTimeInMillis());
    }

    private void updateStatusView() {
        statusTv.setText(mTaskModel.getStatus().toString());
    }


    @Override
    public void done() {
        finish();
    }


    private void showRadioButtonDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("OK", (dialog, id) -> {
            mTaskModel.setStatus(TaskStatus.valueOf(bufStringStatus.toUpperCase()));
            updateStatusView();
        });
        dialogBuilder.setNegativeButton("CANCEL", (dialog, id) -> {
            dialog.dismiss();
        });
        RadioGroup rg = dialogView.findViewById(R.id.radioGroup);
        int index = Arrays.asList(TaskStatus.values()).indexOf(mTaskModel.getStatus());
        ((RadioButton) rg.getChildAt(index)).setChecked(true);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio1:
                    bufStringStatus = TaskStatus.values()[0].toString();
                    break;
                case R.id.radio2:
                    bufStringStatus = TaskStatus.values()[1].toString();
                    break;
                case R.id.radio3:
                    bufStringStatus = TaskStatus.values()[2].toString();
                    break;
                default:
                    break;
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.update:
                mPresenter.updateTask(this, mTaskModel);
                return true;
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showTaskInfo(LiveData<TaskModel> taskModel) {
        taskModel.observe(this, taskModel1 -> {
                    if (taskModel1 != null) {
                        getSupportActionBar().setTitle(taskModel1.getCaption());
                        dateTv.setText(getDate(taskModel1.getDueDate()));
                        statusTv.setText(taskModel1.getStatus().toString());
                        mTaskModel = taskModel1;
                    }
                }
        );
    }

    public String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
