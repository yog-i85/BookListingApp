package com.example.booklistingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<BookList> {

    public BookAdapter(@NonNull Context context, @NonNull ArrayList<BookList> bookLists) {
        super(context, 0, bookLists);
    }
    @Override
    public View getView(int position, View convetView, ViewGroup parent){
        View listItemView=convetView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }
        BookList current=getItem(position);
        TextView title=listItemView.findViewById(R.id.title);
        title.setText(current.getmTitle());

        Bitmap thumbnail=current.getmThumbnail();
        ImageView imageView=listItemView.findViewById(R.id.image_view);
        Log.d("Manifest file========", "run: "+Thread.currentThread().getName());
//      background thread
//        Runnable runnable1=new Runnable() {
//            @Override
//            public void run() {
//                bitmap =ImageExtract.fetchImage(thumbnail);
//                Thread.currentThread().interrupt();
//                Log.e("sdjkhsdfshgkkkk", "run: "+Thread.currentThread().getName());
//            }
//        };
//        Thread thread1=new Thread(runnable1);
//        thread1.start();
        imageView.setImageBitmap(thumbnail);


        TextView publication=listItemView.findViewById(R.id.publication);
        publication.setText(current.getmPublisher());
        TextView author=listItemView.findViewById(R.id.author);
        String author1=current.getmAuthor();
        author1="by "+author1;
        author.setText(author1);
        TextView date=listItemView.findViewById(R.id.publish_date);
        date.setText(current.getmDate());
        return listItemView;
    }
}
