package com.deepmodi.androidservice.myservices;


import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void,Integer,String> {

    private WeakReference<TextView> tv;

    public SimpleAsyncTask(TextView tv) {
        this.tv = new WeakReference<>(tv);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        this.tv.get().setText(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this.tv.get().setText(TextUtils.concat("Completed: ", values[0].toString()," % "));
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random random = new Random();
        int item = random.nextInt(11);
        int s = item * 1000;

        try{
            for(int i=0; i<item; i++){
                int perCount=( i * 100 )/item;
                publishProgress(perCount);
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "Awake at last after sleeping for " + s + " milliseconds!";
    }
}
