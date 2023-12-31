package com.example.chatapp.Repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.model.ChatGroup;
import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.views.GroupsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class Repository {

    // It acts as a bridge between the ViewModel and the data source
    //MutableLiveData is used to hold and observe data changes
    MutableLiveData<List<ChatGroup>> chatGroupMutableLiveData;

    FirebaseDatabase database;
    DatabaseReference reference;   //The Root reference
    DatabaseReference groupReference;

    MutableLiveData<List<ChatMessage>> messagesLiveData;

    public Repository(){
        this.chatGroupMutableLiveData = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        messagesLiveData = new MutableLiveData<>();
    }

    //Auth
    public void firebaseAnonymousAuth(Context context){
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //Authentication is successful:
                            Intent i = new Intent(context, GroupsActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    }
        });
    }

    //Getting Current User ID
    public String getCurrentUserId(){
       return FirebaseAuth.getInstance().getUid();
       //The UID is the unique identifier assigned by Firebase authentication
        //for each user when they sign up or authenticate in your app
    }

    //SignOut Functionality
    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    //Getting Chat Groups available from Firebase realtime DB

    public MutableLiveData<List<ChatGroup>> getChatGroupmutableLiveData() {
        List<ChatGroup> groupsList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupsList.clear();
                //it is mandatory, to prevent any duplication of received data from Firebase.

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ChatGroup group = new ChatGroup(dataSnapshot.getKey());
                        groupsList.add(group);
                    //a data Snapshot represent a specific location in the Firebase DB and can contain data/child data.
                    //Get Children method is a method of data snapshot class that
                    // returns an iterable of all the immediate children of the current snapshot.
                    //For each loop iterates through each child of snapshot inside the loop.
                    //Data snapshot represents each child data one by one.
                }
                chatGroupMutableLiveData.postValue(groupsList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return chatGroupMutableLiveData;
    }

    //Creating a new group
    public void createNewChatGroup(String groupName){
        //this function is to create and push data to Firebase
        reference.child(groupName).setValue(groupName);
    }

    //Getting Messages Live Data
    public MutableLiveData<List<ChatMessage>> getMessagesLiveData(String groupName) {
        groupReference = database.getReference().child(groupName);

        List<ChatMessage> messagesList = new ArrayList<>();

        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren() ){
                    ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                    messagesList.add(message);
                }

                messagesLiveData.postValue(messagesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return messagesLiveData;
    }

    public void sendMessage(String messageText, String chatGroup){
        //send the message to the correct chat group
        //In this function I can use the same Firebase instance, but I cant use the database reference because it's as valuable
        //It depends on the chat group child

        //Inside this reference method I need to pass my child(my chat group node)
        DatabaseReference ref = database
                .getReference(chatGroup);

        //check is the message text is not null, then need to send it to the database reference chat message
        if(!messageText.trim().equals("")) {
            ChatMessage msg = new ChatMessage(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(), // Get User ID
                    messageText,                                        //Get the messages itself
                    System.currentTimeMillis()                         //Get the timestamp
            );//This is my message object

            //need to push it to the database reference
            //first, need to create an arbitrary key
            String randomKey = ref.push().getKey();
            //last step
            ref.child(randomKey).setValue(msg);
            //********* move to the ViewModel and call this function
        }
    }

}
