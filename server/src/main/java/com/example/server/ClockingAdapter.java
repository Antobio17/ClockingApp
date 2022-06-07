package com.example.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClockingAdapter extends RecyclerView.Adapter<ClockingAdapter.ViewHolder>{
    private List<String> clocking;
    private LayoutInflater inflater; // Identidicar de que archivo viene el layout
    private Context context;

    public ClockingAdapter(List<String> clocking, Context context)
    {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.clocking = clocking;
    }

    @NonNull
    @Override
    public ClockingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.list_clocking, null);

        return new ClockingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClockingAdapter.ViewHolder holder, int position)
    {
        holder.bindData(this.clocking.get(position));
    }

    @Override
    public int getItemCount()
    {
        return this.clocking.size();
    }

    public void setItems(List<String> items)
    {
        this.clocking = items;
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView text;

        ViewHolder(View itemView)
        {
            super(itemView);
            text = itemView.findViewById(R.id.textClocking);
        }

        void bindData(final String text)
        {
            this.text.setText(text);
        }
    }
}
