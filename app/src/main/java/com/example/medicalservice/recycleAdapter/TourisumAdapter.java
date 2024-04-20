package com.example.medicalservice.recycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalservice.R;

import java.util.List;

public class TourisumAdapter extends RecyclerView.Adapter<TourisumAdapter.ViewHolder> {

    private List<Integer> integers;

    public TourisumAdapter(List<Integer> integers) {
        this.integers = integers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_bottom_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageView.setImageResource(integers.get(position));

        //holder.imageView.setLayoutParams();
    }

    @Override
    public int getItemCount() {
        return integers.size();
    }

    static class ViewHolder  extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
        }
    }
}
