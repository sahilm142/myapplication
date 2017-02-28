package com.example.dell.myapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mref,cref;
    TextView textView;
    Button save;
    ListView courses_list;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList=new ArrayList<>();
    AlertDialog addcourse_dialog;
    String course_node;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=FirebaseDatabase.getInstance();
        mref=database.getReference().child("courses");//mref.setValue("courses");
        courses_list=(ListView)findViewById(R.id.courses_list);
        textView=(TextView)findViewById(R.id.textView2);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        courses_list.setAdapter(arrayAdapter);
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                    for (DataSnapshot child:children
                            ) {
                        String a=child.getKey();
                        arrayList.add(a);
                        arrayAdapter.notifyDataSetChanged();
                    }


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        final Intent intent=new Intent(this,course.class);
        courses_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("course_name",arrayList.get(i));
               startActivity(intent);
            }
        });

    }




    public  void function(View v)
    {
        if(v==findViewById(R.id.addcourse_id))
        {
            //Toast.makeText(this, "button add Course clicked", Toast.LENGTH_LONG).show();
            final View addcourse_view=getLayoutInflater().inflate(R.layout.addcourse_dialog,null);
            save=(Button)addcourse_view.findViewById(R.id.addcourse_save);
            AlertDialog.Builder mbuilder=new AlertDialog.Builder(this);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(getApplicationContext(),"save",Toast.LENGTH_LONG).show();
                    addcourse_dialog.dismiss();
                    EditText course=(EditText)addcourse_view.findViewById(R.id.addcourse_edittext);
                    String course_name=course.getText().toString();
                    arrayList.add(course_name);
                   arrayAdapter.notifyDataSetChanged();
                    cref=mref.child(course_name);
                    MainActivity.student student=new MainActivity.student(1,3);
                    //int [] a={0,1,2};
                    cref.setValue(course_name);
                }
            });
            mbuilder.setView(addcourse_view);
            addcourse_dialog=mbuilder.create();
            addcourse_dialog.show();
        }
    }
    public static class student
    {
        int roll,cgpa;
        public student(int a,int b) {
        this.roll=a;this.cgpa=b;
        }
    }

}
