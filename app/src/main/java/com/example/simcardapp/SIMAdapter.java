package com.example.simcardapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SIMAdapter extends RecyclerView.Adapter<SIMAdapter.SIMViewHolder> {
    private final Context context;
    private final List<SIMModel> simList;

    public SIMAdapter(Context context, List<SIMModel> simList) {
        this.context = context;
        this.simList = simList;
    }

    @NonNull
    @Override
    public SIMViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sim, parent, false);
        return new SIMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SIMViewHolder holder, int position) {
        SIMModel sim = simList.get(position);

        holder.simTypeTextView.setText("Provider Telepon: " + sim.getSimType());
        holder.simNumberTextView.setText("Nomor Telepon: " + sim.getSimNumber());
    }

    @Override
    public int getItemCount() {
        return simList.size();
    }

    static class SIMViewHolder extends RecyclerView.ViewHolder {
        TextView simTypeTextView, simNumberTextView;

        public SIMViewHolder(@NonNull View itemView) {
            super(itemView);
            simTypeTextView = itemView.findViewById(R.id.sim_type_text_view);
            simNumberTextView = itemView.findViewById(R.id.sim_number_text_view);
        }
    }
}
