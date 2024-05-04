package com.bhanu.reminders;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EXACT_ALARM = 59;
    private RecyclerView remindersList;
    private ReminderAdapter adapter;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Request the SCHEDULE_EXACT_ALARM permission
        ActivityCompat.requestPermissions(this, Arrays.asList(Manifest.permission.SCHEDULE_EXACT_ALARM).toArray(new String[0]), REQUEST_CODE_EXACT_ALARM);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        remindersList = findViewById(R.id.remindersList);
        remindersList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReminderAdapter(myDb.getAllReminders(), myDb);
        remindersList.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("reminderChannel", name, importance);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel.setDescription(description);
            }
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        FloatingActionButton fabAddReminder = findViewById(R.id.fabAddReminder);
        fabAddReminder.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddReminderActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setReminders(myDb.getAllReminders());
        adapter.notifyDataSetChanged();
    }
}
