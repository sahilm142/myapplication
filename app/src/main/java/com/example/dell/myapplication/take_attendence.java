package com.example.dell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class take_attendence extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String course_name;
    ListView listView;
    ArrayList<String> roll=new ArrayList<>();
    ArrayList<String> name=new ArrayList<>();
    ArrayAdapter arrayAdapter;
    custom_adapter Custom_adapter;
    DatabaseReference cref,mref;
    Boolean [] atten;
    String email;
    int countstudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);
        Intent intent=getIntent();
        course_name=intent.getStringExtra("course_name");
        email=intent.getStringExtra("email");
        TextView textView=(TextView)findViewById(R.id.textView5);
        textView.setText(course_name);
        listView=(ListView)findViewById(R.id.lsitview);
        cref= FirebaseDatabase.getInstance().getReference().child("faculty").child(email).child("courses").child(course_name).child("students");
      //  mref=FirebaseDatabase.getInstance().getReference().child("attendees").child(course_name);
        //arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,roll);
        //roll.add("aaa");name.add("aaa");

       Custom_adapter=new custom_adapter(this,roll,name);
        listView.setAdapter(Custom_adapter);
        listView.setOnItemClickListener(this);
        cref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                atten=new Boolean[(int) dataSnapshot.getChildrenCount()];
                int i=0;
                for (DataSnapshot child:children
                        ) {
                    atten[i]=false;i=i+1;
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
    }
    /*added on 23/03/2017*/
/*
     public void showTimePickerDialog(View v) {
    DialogFragment newFragment = new TimePickerFragment();
    newFragment.show(getSupportFragmentManager(), "timePicker");
}
public static class TimePickerFragment extends DialogFragment
                            implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
    }
}*//*end*/
    public void function(View view)
    {
        if(view==findViewById(R.id.button2));
        {
            EditText dateview=(EditText)findViewById(R.id.editText2);
            String date=dateview.getText().toString().replace('/',',');
            mref=FirebaseDatabase.getInstance().getReference().child("faculty").child(email).child("courses").child(course_name).child("attendees").child(date);
            mref.setValue("today");
            int j=0;
            for(boolean i:atten)
            {
                if(i==true)
                {
                 mref.child(roll.get(j)).setValue("Present");
                }
                else
                {
                    mref.child(roll.get(j)).setValue("Absent");
                }
                j=j+1;
            }

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CheckBox checkBox= (CheckBox) view.findViewById(R.id.checkBox);
        if(checkBox.isChecked())
        {
            atten[i]=false;
            checkBox.setChecked(false);
        }
        else
        {
            checkBox.setChecked(true);
            atten[i]=true;
        }
    }
}
