package com.example.booklistingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.GONE;

public class Discription extends AppCompatActivity {
    BookList current;
    TextView mTitle,mAuthor,mPublication,mDiscription;
    Button mButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discription);
        //examine the intent that was used to launch this activity
        //in order to figure out if we are creating a new pet editing an existing one
        Intent intent = getIntent();
//        current=(BookList)intent.getSerializableExtra("book");
        Uri uri=intent.getData();
        String url=uri.toString();
        Toast.makeText(this,url,Toast.LENGTH_LONG).show();

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
//            doInBackgroung
                current=QueryUtils.fetchSingleData(url);
//                        Toast.makeText(MainActivity.this,uriBuilder.toString(),Toast.LENGTH_SHORT).show();
                Log.e("main activity", "run:  background thread---->"+Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                //PostExecute
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Set empty state text to display "No book found."
                        mTitle=findViewById(R.id.dis_title);
                        mTitle.setText(current.getmTitle());
                        mAuthor=findViewById(R.id.dis_author);
                        mAuthor.setText(current.getmAuthor());
                        mPublication=findViewById(R.id.dis_pub);
                        mPublication.setText(current.getmPublisher()+" in year "+ current.getmDate());
                        mDiscription=findViewById(R.id.dis);
                        mDiscription.setText(current.getmDis());
                    }
                });
            }
        };
        Thread thread=new Thread(runnable);
        thread.start();

        mButton=(Button)findViewById(R.id.buy);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buyUrl = current.getmBuyLink();
                if(!buyUrl.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(buyUrl));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Discription.this, "Book not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
