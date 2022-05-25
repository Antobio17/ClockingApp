package com.example.clockingapp.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clockingapp.MainActivity;
import com.example.clockingapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>
{
    private List<Schedule> schedules;
    private LayoutInflater inflater; // Identidicar de que archivo viene el layout
    private Context context;

    public ScheduleAdapter(List<Schedule> schedules, Context context)
    {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.list_schedule, null);

        return new ScheduleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position)
    {
        holder.bindData(this.schedules.get(position));
    }

    @Override
    public int getItemCount()
    {
        return this.schedules.size();
    }

    public void setItems(List<Schedule> items)
    {
        this.schedules = items;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView workerName, checkingIn, checkingOut;

        ViewHolder(View itemView)
        {
            super(itemView);
            workerName = itemView.findViewById(R.id.workerName);
            checkingIn = itemView.findViewById(R.id.checkingIn);
            checkingOut = itemView.findViewById(R.id.checkingOut);
        }

        @SuppressLint("SetTextI18n")
        void bindData(final Schedule schedule)
        {
            Worker worker = MainActivity.db.workerDao().findByID(schedule.getWorkerID());
            workerName.setText(worker.getWorker());
            checkingIn.setText(schedule.getCheckingIn().split("\\s+")[1]);
            if(schedule.getCheckingOut() != null) {
                checkingOut.setText(schedule.getCheckingOut().split("\\s+")[1]);
            }
        }
    }
}
