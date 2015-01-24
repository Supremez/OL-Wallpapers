package com.arthurmb.recyclerviewtest.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.arthurmb.recyclerviewtest.R;
import com.arthurmb.recyclerviewtest.fragments.BooksFragment;



public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);




        BooksFragment booksFragment = (com.arthurmb.recyclerviewtest.fragments.BooksFragment) getFragmentManager().findFragmentById(R.id.fragment_last_books_recycler);

        booksFragment.setToolbar(toolbar);

        setSupportActionBar(toolbar);
    }
}