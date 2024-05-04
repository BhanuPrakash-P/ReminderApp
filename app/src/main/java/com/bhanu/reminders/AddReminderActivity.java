package com.bhanu.reminders;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {
    private EditText editTextReminderTitle;
    private DatabaseHelper myDb;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        myDb = new DatabaseHelper(this);
        calendar = Calendar.getInstance();
        editTextReminderTitle = findViewById(R.id.editTextReminderTitle);

        findViewById(R.id.buttonSaveReminder).setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddReminderActivity.this, (datePicker, year, month, dayOfMonth) -> {
                @SuppressLint("ScheduleExactAlarm") TimePickerDialog timePickerDialog = new TimePickerDialog(AddReminderActivity.this, (timePicker, hourOfDay, minute) -> {
                    // Combine date and time into one long value
                    long dateTime = new java.util.GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute).getTimeInMillis();
                    boolean isInserted = myDb.insertReminder(editTextReminderTitle.getText().toString(), dateTime);
                    if (isInserted) {
                        Toast.makeText(AddReminderActivity.this, "Reminder Set", Toast.LENGTH_LONG).show();

                        //set alarm
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);;
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                        }
//                        else
//                        {
//                            Log.e("BHANU", "alarmManager is NULL");
//                        }
                        Intent intent = new Intent(this, ReminderBroadcastReceiver.class);
                        intent.putExtra("reminder_title", editTextReminderTitle.getText().toString());
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

// Set the alarm to start at the chosen time
                        //if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM) == PackageManager.PERMISSION_GRANTED) {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        //}

                    } else {
                        Toast.makeText(AddReminderActivity.this, "Reminder Not Set", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }, java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), false);
                timePickerDialog.show();
            }, java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }
}
