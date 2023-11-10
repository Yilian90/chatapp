package com.example.chatapp.Repository;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.model.ChatGroup;
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

public class Repository {

    // It acts as a bridge between the ViewModel and the data source
    //MutableLiveData is used to hold and observe data changes
    MutableLiveData<List<ChatGroup>> chatGroupmutableLiveData;

    FirebaseDatabase database;
    DatabaseReference reference;

    public Repository(){
        this.chatGroupmutableLiveData = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

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
                chatGroupmutableLiveData.postValue(groupsList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return chatGroupmutableLiveData;
    }

    //Creating a new group
    public void createNewChatGroup(String groupName){
        //this function is to create and push data to Firebase
        reference.child(groupName).setValue("");

    }
}
