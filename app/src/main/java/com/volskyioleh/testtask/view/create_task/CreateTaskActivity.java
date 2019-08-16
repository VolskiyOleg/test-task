package com.volskyioleh.testtask.view.create_task;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.volskyioleh.testtask.R;
import com.volskyioleh.testtask.model.db.entities.TaskModel;
import com.volskyioleh.testtask.model.db.entities.TaskStatus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTaskActivity extends AppCompatActivity implements CreateTaskContract.View {

    private CreateTaskContract.Presenter mPresenter;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.caption)
    TextInputLayout editTextCaption;

    @BindView(R.id.date)
    TextInputLayout editTextDate;
    @BindView(R.id.date_lay)
    FrameLayout editTextDateLay;

    @BindView(R.id.create_task)
    Button createTaskBtn;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ButterKnife.bind(this);
        mPresenter = new CreateTaskPresenter(this);
        updateLabel();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

        };

        editTextDate.getEditText().setOnClickListener(v -> {
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
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        editTextDate.getEditText().setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick(R.id.create_task)
    public void onCLick() {
        TaskModel taskModel = new TaskModel(myCalendar.getTimeInMillis(),
                editTextCaption.getEditText().getText().toString(), TaskStatus.values()[spinner.getSelectedItemPosition()]);

        mPresenter.createTask(this, taskModel);
    }


    @Override
    public void done() {
        finish();
    }
}
