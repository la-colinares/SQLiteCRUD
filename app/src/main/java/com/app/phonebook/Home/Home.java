package com.app.phonebook.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.app.phonebook.AddNewContact.AddNewContact;
import com.app.phonebook.AsyncTasks.ContactsAsyncTask;
import com.app.phonebook.R;

public class Home extends AppCompatActivity {


    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton btnAdd;

    private LinearLayout noRecords;

    private RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    //for double back press
    private boolean doubleBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_my_contact);
        toolbar.setPaddingRelative(10,0,0,0);


        initViews();
        new ContactsAsyncTask(coordinatorLayout,mRecyclerView,noRecords,this).execute();
        btnAddClick();

    }

    private void btnAddClick() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNewContact.class));
                finish();
            }
        });
    }

    private void initViews() {
        coordinatorLayout =  findViewById(R.id.coordinatorLayout);
        btnAdd =  findViewById(R.id.btn_add);

        noRecords =  findViewById(R.id.no_contact);
        mRecyclerView =  findViewById(R.id.main_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackPressed) {
            super.onBackPressed();
            return;
        }
        doubleBackPressed = true;
        Snackbar.make(coordinatorLayout, "Press back again to exit.", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressed = false;
            }
        }, 2000);
    }
}
