package com.example.chatapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chatapp.R;

public class GroupsActivity extends AppCompatActivity {


    //get all the groups available in Firebase Realtime DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
    }
}