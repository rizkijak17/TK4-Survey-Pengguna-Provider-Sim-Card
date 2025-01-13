package com.example.simcardapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class KKAdapter extends RecyclerView.Adapter<KKAdapter.KKViewHolder> {
    private final List<KKModel> kkList;
    private final Context context;

    public KKAdapter(List<KKModel> kkList, Context context) {
        this.kkList = kkList;
        this.context = context;
    }

    @NonNull
    @Override
    public KKViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kk, parent, false);
        return new KKViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KKViewHolder holder, int position) {
        KKModel kk = kkList.get(position);
        holder.nameTextView.setText(kk.getName());


        holder.viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("KK_ID", kk.getId());
            intent.putExtra("KK_NAME", kk.getName());
            context.startActivity(intent);
        });


        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("KK_ID", kk.getId());
            intent.putExtra("KK_NAME", kk.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return kkList.size();
    }

    static class KKViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button viewButton, editButton;

        KKViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.kk_name);
            viewButton = itemView.findViewById(R.id.button_view);
            editButton = itemView.findViewById(R.id.button_edit);
        }
    }
}
