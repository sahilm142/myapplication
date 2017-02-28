package com.example.dell.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



/**
 * Created by Dell on 26-02-2017.
 */

public class custom_adapter extends ArrayAdapter<String> {
    ArrayList<String> roll,name;
    Context context=getContext();

    public custom_adapter(Context context, ArrayList<String> roll,ArrayList<String> name) {
        super(context,R.layout.attendence_line,roll);
        this.name=name;
        this.roll=roll;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customview=inflater.inflate(R.layout.attendence_line,parent,false);
        TextView textView1= (TextView) customview.findViewById(R.id.textView3);
        TextView textView2= (TextView) customview.findViewById(R.id.textView4);
        textView1.setText(roll.get(position));
        textView2.setText(name.get(position));
        return customview;
    }
}
