package com.example.dell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class course extends AppCompatActivity {

    Intent intent1;
    FirebaseDatabase database;
    DatabaseReference mref,cref;
    TextView textView;
    String course_name;
    Button save;
    ListView listView;
    AlertDialog addstudent_dialog;
    String email;
    ArrayList<String> name_list=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        database=FirebaseDatabase.getInstance();
        textView=(TextView)findViewById(R.id.textView);
        listView=(ListView)findViewById(R.id.student_list);
        Intent intent=getIntent();
        course_name=intent.getStringExtra("course_name");
        email=intent.getStringExtra("email");
        mref=database.getReference().child("faculty").child(email).child("courses").child(course_name).child("students");
        textView.setText(course_name);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name_list);
        listView.setAdapter(arrayAdapter);
         intent1=new Intent(this,take_attendence.class);
        intent1.putExtra("course_name",course_name);
        intent1.putExtra("email",email);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                for (DataSnapshot child:children
                        ) {
                    String a=child.getKey();
                    String b=child.getValue(String.class);
                    name_list.add(b);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void function(View v)
    {
        if(v==findViewById(R.id.enroll))
        {   final View addstudent_view=getLayoutInflater().inflate(R.layout.enroll_dialog,null);
            AlertDialog.Builder mbuilder=new AlertDialog.Builder(this);
            save=(Button)addstudent_view.findViewById(R.id.save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText name=(EditText)addstudent_view.findViewById(R.id.name);
                    EditText roll=(EditText)addstudent_view.findViewById(R.id.roll);
                    String Name=name.getText().toString();
                    String Roll=roll.getText().toString();
                    name_list.add(Name);
                    arrayAdapter.notifyDataSetChanged();
                    addstudent_dialog.dismiss();
                    cref=mref.child(Roll);cref.setValue(Name);
                }
            });
            mbuilder.setView(addstudent_view);
            addstudent_dialog=mbuilder.create();
            addstudent_dialog.show();

        }
        if(v==findViewById(R.id.take_atttendence))
        {
         startActivity(intent1);
        }
    }
}
