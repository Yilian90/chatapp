package com.example.chatapp.views.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.BR;
import com.example.chatapp.R;
import com.example.chatapp.databinding.RowChatBinding;
import com.example.chatapp.model.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    //always pass your custom view holder class inside RecyclerView Adapter

    private List<ChatMessage> chatMessageList;  //modal and data model or the data source should be list of chat message class
    private Context context;    //use it in the layout inflator later on

    //3. create constructor
    public ChatAdapter(List<ChatMessage> chatMessageList, Context context) {
        this.chatMessageList = chatMessageList;
        this.context = context;
    }

    //4. implement 3 methods for RecyclerView Adapter
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_chat, parent, false);   //import class for R

        RowChatBinding binding = DataBindingUtil.bind(view);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.chatMessage, chatMessageList.get(position));
        //holder.getBinding() -> is obtaining the data binding object associated with a specific view holder.
        //In Android's recycler view, a view holder is used to represent an item in a list or a grid
        //setVariable -> sets a variable named chat message in the data binding object of value chat list .get position.
        //BR.chatMessage -> class that contains references to the variables you've defined in your layout XML file row_chat.xml(variable name:chatMessage)for data binding
        //chatMessageList.get(position) -> setting its value, getting its key and setting its value

        holder.getBinding().executePendingBindings();
        //executePendingBindings() -> This method call triggers the execution of pending bindings
        //It ensures that the data from your model. In this case, chat message is bound to the UI elements specified in your layout.
        //In summary , this code is using data binding to update the UI of a specific item in recycler view.
        //With data from a chat list at a given position.
        //It sets the chat message variable in the data binding object, and the applies these changes to the UI by executing the pending position.

        //Keep in mind that you should have your layout XML file properly configured with the chat messages as we done in row_chat.xml
        //name and type
        //Make sure that the chat list contains the data you want to display in your UI and
        // that the recycler view and data binding are set up correctly in your android app

    }


    @Override
    public int getItemCount() {
        return chatMessageList.size();
        //return the number of the variables in the list.
        //************* go to activity_chat.xml and ChatActivity.java   *** done this java code
    }






    public class MyViewHolder extends RecyclerView.ViewHolder{
        private RowChatBinding binding;

        //1.Constructor, delete ItemView parameter
    public MyViewHolder(RowChatBinding binding) {
        super(binding.getRoot());
        setBinding(binding);
    }

    //2. Getter and Setter for setBinding inside constructor
    public RowChatBinding getBinding() {
        return binding;
    }

    public void setBinding(RowChatBinding binding) {
        this.binding = binding;
    }
}
}
