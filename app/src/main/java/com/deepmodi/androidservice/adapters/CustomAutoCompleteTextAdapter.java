package com.deepmodi.androidservice.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.deepmodi.androidservice.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAutoCompleteTextAdapter extends ArrayAdapter<String> implements Filterable,ItemCountCallback {

    private static final int TEXT_VIEW = 0;
    private static final int ADD_NEW_USER_VIEW = 1;
    private final ArrayList<String> usersList;
    private String currentItemState = "TEXT_VIEW";

    public CustomAutoCompleteTextAdapter(@NonNull Context context, ArrayList<String> usersList) {
        super(context, TEXT_VIEW);
        this.usersList = new ArrayList<>(usersList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d("TAG", "getView: "+getItemType());
        if(getItemViewType(position) == TEXT_VIEW){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_username,parent,false);
            TextView textView = convertView.findViewById(R.id.textViewUserName);
            textView.setText(getItem(position));
            return convertView;
        }else{
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_add_user,parent,false);
            Button button = convertView.findViewById(R.id.itemViewAddUserBtn);
            button.setOnClickListener(v->{
                Toast.makeText(parent.getContext(), "Button Clicked.", Toast.LENGTH_SHORT).show();
            });
            return convertView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(!getItemType().equals("ADD_NEW_USER_VIEW")) {
            return TEXT_VIEW;
        }else{
            return ADD_NEW_USER_VIEW;
        }
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<String> tempList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                tempList.addAll(usersList);
            }else{
                String filterpattern = constraint.toString().toLowerCase().trim();
                for(String user : usersList){
                    if(user.toLowerCase().trim().contains(filterpattern)){
                        tempList.add(user);
                    }
                }
            }

            if(tempList.size() == 0){
                tempList.add("ADD_NEW_USER_VIEW");
            }else{
                setItemType("TEXT_VIEW");
            }

            results.count = tempList.size();
            results.values = tempList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            Log.d("TAG", "publishResults: "+((ArrayList<String>)results.values).size());
            if(((ArrayList<String>)results.values).get(0).equals("ADD_NEW_USER_VIEW")){
                setItemType("ADD_NEW_USER_VIEW");
            }
            addAll(((ArrayList)results.values));
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return resultValue.toString();
        }
    };

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public String getItemType() {
        return this.currentItemState;
    }

    @Override
    public void setItemType(String isAddType) {
        this.currentItemState = isAddType;
    }
}
