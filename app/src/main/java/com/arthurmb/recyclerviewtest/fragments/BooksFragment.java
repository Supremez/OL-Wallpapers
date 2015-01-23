package com.arthurmb.recyclerviewtest.fragments;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arthurmb.recyclerviewtest.OnItemClickListener;
import com.arthurmb.recyclerviewtest.R;
import com.arthurmb.recyclerviewtest.activities.DetailActivity;
import com.arthurmb.recyclerviewtest.models.Book;
import com.arthurmb.recyclerviewtest.models.BookList;
import com.arthurmb.recyclerviewtest.network.Api;
import com.arthurmb.recyclerviewtest.views.adapters.BookAdapter;
import com.arthurmb.recyclerviewtest.views.views.RecyclerInsetsDecoration;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.StringReader;
import java.util.ArrayList;

public class BooksFragment extends Fragment {

    public static SparseArray<Bitmap> photoCache = new SparseArray<Bitmap>(1);

    private ProgressDialog loadingDialog;
    private ArrayList<Book> books;
    private RecyclerView bookRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_books, container, false);

        // Configure the recyclerview
        bookRecycler = (RecyclerView) rootView.findViewById(R.id.fragment_last_books_recycler);
        int COLUMNS = 2;
        bookRecycler.setLayoutManager(new GridLayoutManager(getActivity(), COLUMNS));
        bookRecycler.addItemDecoration(new RecyclerInsetsDecoration(getActivity()));
        bookRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        // Init and show progress dialog
        loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setMessage(getResources().getString(R.string.loading_books));
        loadingDialog.show();

        // Load books from API
        Ion.with(getActivity())
                .load(Api.getLastBooks())
                .asString()
                .setCallback(booksCallback);

        return rootView;
    }

    // In future series this will be implemented with a view model presenter model
    private FutureCallback<String> booksCallback = new FutureCallback<String>() {


        @Override
        public void onCompleted(Exception e, String result) {

            if (e == null) {

                // Set the malformed JSON as lenient JsonReader
                result = Api.cleanJSON(result);
                JsonReader reader = new JsonReader(new StringReader(result));
                reader.setLenient(true);

                // Serialize reader into objects
                Gson gson = new Gson();
                BookList bookList = gson.fromJson(reader, BookList.class);
                books = bookList.getWallpapers();


                BookAdapter recyclerAdapter = new BookAdapter(books);
                recyclerAdapter.setOnItemClickListener(recyclerRowClickListener);

                // Update adapter
                bookRecycler.setAdapter(recyclerAdapter);

                // Dismiss loading dialog
                loadingDialog.dismiss();


            } else {
                Log.d("[DEBUG]", "BooksFragment onCompleted - ERROR: " + e.getMessage());
            }
        }
    };

    private OnItemClickListener recyclerRowClickListener = new OnItemClickListener() {

        @Override
        public void onClick(View v, int position) {

            Book selectedBook = books.get(position);

            Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
            detailIntent.putExtra("position", position);
            detailIntent.putExtra("selected_book", selectedBook);

            ImageView coverImage = (ImageView) v.findViewById(R.id.item_book_img);
            ((ViewGroup) coverImage.getParent()).setTransitionGroup(false);
            photoCache.put(position, coverImage.getDrawingCache());

            // Setup the transition to the detail activity
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                    new Pair<View, String>(coverImage, "cover" + position));

            startActivity(detailIntent, options.toBundle());
        }
    };
}