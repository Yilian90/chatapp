package com.example.chatapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivityLoginBinding;
import com.example.chatapp.viewmodel.MyViewModel;

public class loginActivity extends AppCompatActivity {

    MyViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        //ViewModelProvider is responsible for creating and managing view model instances
        //get method to retrieves the an instance of the view model class (MyViewModel)
        //The ViewModelProvide will create a new instance of MyViewModel if one it doesn't exist for the
        //associated component, or it will return the existing instance if it already exists.

        ActivityLoginBinding activityLoginBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_login);

        activityLoginBinding.setVModel(viewModel);
    }
}