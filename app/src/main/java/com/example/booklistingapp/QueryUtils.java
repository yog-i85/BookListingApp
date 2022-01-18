package com.example.booklistingapp;

import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public final class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<BookList> fetchData(String requestUrl){
        URL url=createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object

        // Return the {@link Event}
        return extractFeatureFromJson(jsonResponse);
    }

    public static BookList fetchSingleData(String requestUrl){
        URL url=createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object

        // Return the {@link Event}
        return jsonBook(jsonResponse);
    }

    private static List<BookList> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<BookList> books = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try{
            JSONObject data=new JSONObject(jsonResponse);
            JSONArray items= data.getJSONArray("items");
            for(int i=0;i< items.length();i++){
                JSONObject itemsObject=items.getJSONObject(i);
                JSONObject volumeInfo=itemsObject.getJSONObject("volumeInfo");
                String title =volumeInfo.getString("title");
                String url =itemsObject.getString("selfLink");
                String dis ="";
                dis=volumeInfo.getString("description");
                String author="";
                JSONArray authors=volumeInfo.getJSONArray("authors");
                for(int j=0;j< authors.length();j++){
                    author+=authors.getString(j);
                    if(j!=authors.length()-1){
                        author+=" ,";
                    }
                }
                String publisher="";
                if(volumeInfo.has("publisher"))
                    publisher=volumeInfo.getString("publisher");
                else
                    publisher="not available";
                String date;
                if(volumeInfo.has("publishedDate"))
                    date =volumeInfo.getString("publishedDate");
                else
                    date="not available";
                JSONObject saleInfo =itemsObject.getJSONObject("saleInfo");
                String buyLink;
                if(saleInfo.has("buyLink"))
                    buyLink=saleInfo.getString("buyLink");
                else
                    buyLink="";
                JSONObject imageLinks =volumeInfo.getJSONObject("imageLinks");
                String thumbnail=imageLinks.getString("thumbnail");
                StringBuffer newString
                        = new StringBuffer(thumbnail);

                // Insert the strings to be inserted
                // using insert() method
                newString.insert(4 , "s");
                thumbnail=newString.toString();
                Bitmap bitmap=ImageExtract.fetchImage(thumbnail);
               // http://books.google.com/books/content?id=sDifDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api
                books.add(new BookList(title,buyLink,bitmap,author,publisher,date,url,dis));
            }
        }catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }
        return books;
    }


    private static BookList jsonBook(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        BookList books=new BookList();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try{
            JSONObject items=new JSONObject(jsonResponse);
//            JSONArray items= data.getJSONArray("items");
//            for(int i=0;i< items.length();i++){
                JSONObject itemsObject=items;
                JSONObject volumeInfo=itemsObject.getJSONObject("volumeInfo");
                String title =volumeInfo.getString("title");
                String url =itemsObject.getString("selfLink");
                String dis ="";
                dis=volumeInfo.getString("description");
                String author="";
                JSONArray authors=volumeInfo.getJSONArray("authors");
                for(int j=0;j< authors.length();j++){
                    author+=authors.getString(j);
                    if(j!=authors.length()-1){
                        author+=" ,";
                    }
                }
                String publisher="";
                if(volumeInfo.has("publisher"))
                    publisher=volumeInfo.getString("publisher");
                else
                    publisher="not available";
                String date;
                if(volumeInfo.has("publishedDate"))
                    date =volumeInfo.getString("publishedDate");
                else
                    date="not available";
                JSONObject saleInfo =itemsObject.getJSONObject("saleInfo");
                String buyLink;
                if(saleInfo.has("buyLink"))
                    buyLink=saleInfo.getString("buyLink");
                else
                    buyLink="";
                JSONObject imageLinks =volumeInfo.getJSONObject("imageLinks");
                String thumbnail=imageLinks.getString("thumbnail");
                StringBuffer newString
                        = new StringBuffer(thumbnail);

                // Insert the strings to be inserted
                // using insert() method
                newString.insert(4 , "s");
                thumbnail=newString.toString();
                Bitmap bitmap=ImageExtract.fetchImage(thumbnail);
                // http://books.google.com/books/content?id=sDifDwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api
                books=new BookList(title,buyLink,bitmap,author,publisher,date,url,dis);
//            }
        }catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }
        return books;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse="";
        if(url==null){
            return jsonResponse;
        }
        HttpsURLConnection urlConnection=null;
        InputStream inputStream=null;
        try{
            urlConnection=(HttpsURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    jsonResponse=readFromInputStream(inputStream);
                }
            }
            else{
                Log.e(LOG_TAG, "Error massage"+urlConnection.getResponseCode() );
            }
        }
        catch(IOException e){
            Log.e(LOG_TAG, "makeHttpRequest: Problem retrieving the book JSON results.",e );
        }
        finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while(line!=null){
                stringBuilder.append(line);
                line=reader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url=null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
}
