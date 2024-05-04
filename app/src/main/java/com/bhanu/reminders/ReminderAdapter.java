package com.bhanu.reminders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private List<Reminder> remindersList;
    private final DatabaseHelper myDb;

    public ReminderAdapter(List<Reminder> remindersList, DatabaseHelper myDb) {
        this.remindersList = remindersList;
        this.myDb = myDb;
    }

    public void setReminders(List<Reminder> remindersList) {
        this.remindersList = remindersList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewReminderTitle;
        public TextView textViewReminderDateTime;
        public Button buttonDeleteReminder;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewReminderTitle = itemView.findViewById(R.id.textViewReminderTitle);
            textViewReminderDateTime = itemView.findViewById(R.id.textViewReminderDateTime);
            buttonDeleteReminder = itemView.findViewById(R.id.buttonDeleteReminder);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reminder reminder = remindersList.get(position);
        holder.textViewReminderTitle.setText(reminder.getTitle());
        holder.textViewReminderDateTime.setText(java.text.DateFormat.getDateTimeInstance().format(new java.util.Date(reminder.getDateTime())));
        holder.buttonDeleteReminder.setOnClickListener(view -> {
            myDb.deleteReminder(reminder.getId());
            remindersList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return remindersList.size();
    }
}
