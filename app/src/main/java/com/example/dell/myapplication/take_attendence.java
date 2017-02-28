package com.example.dell.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class take_attendence extends AppCompatActivity {

    String course_name;
    ListView listView;
    ArrayList<String> roll=new ArrayList<>();
    ArrayList<String> name=new ArrayList<>();
    ArrayAdapter arrayAdapter;
    custom_adapter Custom_adapter;
    DatabaseReference cref,mref;
    Boolean [] atten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);
        Intent intent=getIntent();
        course_name=intent.getStringExtra("course_name");
        TextView textView=(TextView)findViewById(R.id.textView5);
        textView.setText(course_name);
        listView=(ListView)findViewById(R.id.lsitview);
        cref= FirebaseDatabase.getInstance().getReference().child("courses").child(course_name);
        mref=FirebaseDatabase.getInstance().getReference().child("attendees").child(course_name);
        //arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,roll);
        //roll.add("aaa");name.add("aaa");
       Custom_adapter=new custom_adapter(this,roll,name);
        listView.setAdapter(Custom_adapter);
        cref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                atten=new Boolean[(int) dataSnapshot.getChildrenCount()+1];
                int i=0;
                for (DataSnapshot child:children
                        ) {
                    atten[i]=false;
                    String a=child.getKey();
                    String b=child.getValue(String.class);
                    roll.add(a);name.add(b);
                    Custom_adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               CheckBox checkBox= (CheckBox) view.findViewById(R.id.checkBox);
               if(checkBox.isChecked())
               {
                   atten[i]=true;
               }
               else
               {
                   atten[i]=false;
               }
           }
       });
    }
    public void function(View view)
    {
        if(view==findViewById(R.id.button2));
        {
            for(boolean i:atten)
            {
                if(i==true)
                {

                }
            }
        }
    }

}
