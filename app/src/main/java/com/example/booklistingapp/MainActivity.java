package com.example.booklistingapp;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    private TextView mEmptyStateTextView;
    private BookAdapter mAdapter;
    List<BookList> books;
    private static final String GOOGLE_BOOKS_REQUEST_URL="https://www.googleapis.com/books/v1/volumes?";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = findViewById(R.id.list);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        // Create a new {@link ArrayAdapter} of earthquakes
        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new BookAdapter(this, new ArrayList<BookList>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        SharedPreferences pref= getSharedPreferences(getString(R.string.topic_to_search),MODE_PRIVATE);
        SharedPreferences.Editor myEdit = pref.edit();
        bookListView.setAdapter(mAdapter);
        String subject = pref.getString(
                getString(R.string.topic_to_search),
                getString(R.string.default_topic));
        Uri baseUri = Uri.parse(GOOGLE_BOOKS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", subject);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
//            doInBackgroung
                books=QueryUtils.fetchData(uriBuilder.toString());
//                Log.e("main activity", "run:  background thread---->"+Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                //PostExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("main activity", "run:  main thread---->"+Thread.currentThread().getName());
                        ProgressBar progressBar = findViewById(R.id.progress_circular);
                        progressBar.setVisibility(GONE);
                        // Set empty state text to display "No book found."
                        if(checkNetwork()) {
                            mEmptyStateTextView.setText(R.string.no_books);
                            // Clear the adapter of previous book data
                            mAdapter.clear();
                            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
                            // data set. This will trigger the ListView to update.
                            if (books != null && !books.isEmpty()) {
                                mAdapter.addAll(books);
                            }
                        }
                        else{
                            mEmptyStateTextView.setText(R.string.no_internet);
//                            Log.e("in main thread","onLoadFinished: "+checkNetwork());
                        }
                    }
                });
            }
        };
        Thread thread=new Thread(runnable);
        thread.start();

        //set clicklistner for list item
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String url = mAdapter.getItem(position).getmBuyLink();
//                if(!url.equals("")) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(url));
//                    startActivity(intent);
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "Book not available", Toast.LENGTH_SHORT).show();
//                }

                Intent intent=new Intent(MainActivity.this,Discription.class);
//
//                Uri currentBookUri= ContentUris.withAppendedId(id);
//                //setting uri on data field of the intent
//                intent.setData(currentBookUri);

                BookList bookList=mAdapter.getItem(position);

//                intent.putExtra("book",bookList);
                intent.setData(Uri.parse(bookList.getmUrl()));
                startActivity(intent);
//                Toast.makeText(MainActivity.this,bookList.getmUrl(),Toast.LENGTH_LONG).show();
            }
        });
//      serach books in list

        SearchView searchView=findViewById(R.id.search_btn);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ProgressBar progressBar = findViewById(R.id.progress_circular);
                progressBar.setVisibility(View.VISIBLE);
                ListView listView = findViewById(R.id.list);
                listView.setVisibility(GONE);
                TextView textView = findViewById(R.id.empty_view);
                textView.setVisibility(GONE);

                Uri baseUri = Uri.parse(GOOGLE_BOOKS_REQUEST_URL);
                Uri.Builder uriBuilder = baseUri.buildUpon();
                myEdit.putString(getString(R.string.topic_to_search), query);
                myEdit.commit();
                uriBuilder.appendQueryParameter("q", query);
//                uriBuilder.appendQueryParameter("limit", "10");
//                uriBuilder.appendQueryParameter("minmag", minMagnitude);
//                uriBuilder.appendQueryParameter("orderby", "time");
//                Toast.makeText(MainActivity.this,uriBuilder.toString(),Toast.LENGTH_SHORT).show();
//                books=QueryUtils.fetchData(uriBuilder.toString());



                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
//            doInBackgroung
                        books=QueryUtils.fetchData(uriBuilder.toString());
//                        Toast.makeText(MainActivity.this,uriBuilder.toString(),Toast.LENGTH_SHORT).show();
                Log.e("main activity", "run:  background thread---->"+Thread.currentThread().getName());
                        Thread.currentThread().interrupt();
                        //PostExecute
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                        Log.e("main activity", "run:  main thread---->"+Thread.currentThread().getName());
                                ProgressBar progressBar = findViewById(R.id.progress_circular);
                                progressBar.setVisibility(GONE);
                                // Set empty state text to display "No book found."
                                if(checkNetwork()) {
                                    mEmptyStateTextView.setText(R.string.no_books);
                                    // Clear the adapter of previous book data
                                    mAdapter.clear();
                                    // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
                                    // data set. This will trigger the ListView to update.
                                    if (books != null && !books.isEmpty()) {
                                        mAdapter.addAll(books);
                                    }
                                }
                                else{
                                    mEmptyStateTextView.setText(R.string.no_internet);
//                            Log.e("in main thread","onLoadFinished: "+checkNetwork());
                                }
                            }
                        });
                    }
                };
                Thread thread=new Thread(runnable);
                thread.start();
//                return new BookList(this, uriBuilder.toString());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    public boolean checkNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}