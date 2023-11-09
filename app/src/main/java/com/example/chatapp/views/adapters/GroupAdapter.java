package com.example.chatapp.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ItemCardBinding;
import com.example.chatapp.model.ChatGroup;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private ArrayList<ChatGroup> groupArrayList;

    public GroupAdapter(ArrayList<ChatGroup> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemCardBinding binding = DataBindingUtil
                .inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_card,
                        parent,
                        false
                );
        return new GroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        // binds data to an existing ViewHolder
        // Populates the Views in the ViewHolder with Data from the Dataset
        ChatGroup currentUser = groupArrayList.get(position);
        holder.itemCardBinding.setChatGroup(currentUser);
    }

    @Override
    public int getItemCount() {
        //return how many items in the recycler view or the list to be displayed.
        return groupArrayList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{
        // cache references to the individual views with in an item layout
        // of a recyclerView list
        private ItemCardBinding itemCardBinding;

        public GroupViewHolder(ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.itemCardBinding = itemCardBinding;
            //telling this adapter that our layout is using the data binding library

            //here need to handle the click events on Recycler View items
            itemCardBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                }
            });
        }
    }
}
