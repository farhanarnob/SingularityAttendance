package com.farhanarnob.singularityattendance.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farhanarnob.singularityattendance.R;
import com.farhanarnob.singularityattendance.network.room.DataModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {

    private final List<DataModel> storeDataModels;

    public StoreListAdapter(List<DataModel> storeDataModels) {
        this.storeDataModels = storeDataModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView commentText = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_rv, parent, false);
        return new ViewHolder(commentText);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DataModel storeDataModel = storeDataModels.get(position);
        holder.storeNameTV.setText(storeDataModel.getName());
    }

    @Override
    public int getItemCount() {
        return storeDataModels == null ? 0 : storeDataModels.size();
    }

    /**
     * View holder for shopping list items of this adapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeNameTV;

        public ViewHolder(final TextView storeNameTV) {
            super(storeNameTV);
            this.storeNameTV = storeNameTV;
        }
    }
}
