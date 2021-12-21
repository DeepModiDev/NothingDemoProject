package com.deepmodi.androidservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.deepmodi.androidservice.adapters.CustomAutoCompleteTextAdapter;
import com.deepmodi.androidservice.myservices.MyService;
import com.deepmodi.androidservice.myservices.SimpleAsyncTask;
import com.deepmodi.androidservice.myservices.SimpleAsyncTaskLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap> {

    private TextView textView1 = null;
    private ImageView carImageView = null;
    private AutoCompleteTextView autoCompleteTextView;

    private static final String TEXT_STATE = "currentText";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE,textView1.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textView1);
        carImageView = findViewById(R.id.carImageView);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        ArrayList<String> users = new ArrayList<>();
        users.add("Deep");
        users.add("Alka");
        users.add("Piyush");

        CustomAutoCompleteTextAdapter adapter = new CustomAutoCompleteTextAdapter(this,users);
        autoCompleteTextView.setAdapter(adapter);

        if(savedInstanceState != null){
            textView1.setText(savedInstanceState.getString(TEXT_STATE));
        }

        LoaderManager manager = LoaderManager.getInstance(this);
        manager.initLoader(0,null,this);

    }

    public void startTask(View view){
        textView1.setText("This is the initial string.");
        new SimpleAsyncTask(textView1).execute();
    }


    @NonNull
    @Override
    public Loader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {
        return new SimpleAsyncTaskLoader(this,
                "https://media.wired.com/photos/5d09594a62bcb0c9752779d9/1:1/w_1500,h_1500,c_limit/Transpo_G70_TA-518126.jpg");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap data) {
        carImageView.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Bitmap> loader) {

    }
}