package com.example.campin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StaticInfoAdapter extends RecyclerView.Adapter<StaticInfoAdapter.StaticInfoViewHolder>{

    private ArrayList<StaticInfoModel> items;
    int row_index = -1;

    public StaticInfoAdapter(ArrayList<StaticInfoModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public StaticInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.static_info_item,parent, false);
        StaticInfoViewHolder staticInfoViewHolder = new StaticInfoViewHolder(view);
        return staticInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaticInfoViewHolder holder, int position) {
        StaticInfoModel currentItem = items.get(position);
        holder.judul.setText(currentItem.getJudul());
        holder.deskripsi.setText(currentItem.getDeskripsi());
        holder.imageView.setImageResource(currentItem.getGambar());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class StaticInfoViewHolder extends RecyclerView.ViewHolder{

        TextView judul, deskripsi;
        ImageView imageView;
        RelativeLayout relativeLayout;

        public StaticInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            judul     = itemView.findViewById(R.id.judul_info);
            deskripsi = itemView.findViewById(R.id.deskripsi_info);
            imageView = itemView.findViewById(R.id.gambar_info);
            relativeLayout = itemView.findViewById(R.id.relativeLayout_info);

        }
    }

}
