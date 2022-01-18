package com.example.booklistingapp;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.net.URL;

public class BookList implements Serializable {
    private final String mTitle;
    private final Bitmap mThumbnail;
    private final String mBuyLink;
    private final String mAuthor;
    private final String mPublisher;
    private final String mDate;
    private final String mDis;
    private final String mUrl;
    public BookList(String title,String buyLink,Bitmap thumbnail,String author,String publisher,String date,String url,String dis) {
        mTitle = title;
        mBuyLink = buyLink;
        mThumbnail = thumbnail;
        mAuthor = author;
        mPublisher = publisher;
        mDate=date;
        mUrl=url;
        mDis=dis;
    }
    public BookList() {
        mTitle = "";
        mBuyLink = "";
        mThumbnail = null;
        mAuthor = "";
        mPublisher = "";
        mDate="";
        mUrl="";
        mDis="";
    }

    public Bitmap getmThumbnail() {
        return mThumbnail;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmBuyLink() {
        return mBuyLink;
    }

    public String getmPublisher() {
        return mPublisher;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDate() {
        return mDate;
    }
    public String getmDis() {
        return mDis;
    }
    public String getmUrl() {
        return mUrl;
    }
}
