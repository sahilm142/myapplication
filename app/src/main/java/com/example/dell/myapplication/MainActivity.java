package com.example.dell.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements ValueEventListener,AdapterView.OnItemClickListener,FirebaseAuth.AuthStateListener{

    FirebaseDatabase database;
    DatabaseReference mref,cref;
    TextView textView;
    Intent intent;
    Button save;
    ListView courses_list;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList=new ArrayList<>();
    AlertDialog addcourse_dialog;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    ValueEventListener valueEventListener;
    String course_node;
    //View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        intent=new Intent(this,course.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // Toast.makeText(this,"signed in as "+user.getEmail(),Toast.LENGTH_SHORT).show();
       // gotohome();
        if(user!=null)
        {
            Toast.makeText(this,"signed in as "+user.getEmail(),Toast.LENGTH_SHORT).show();
            gotohome();
        }
        else
        {
            final Intent intent1=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent1);
        }

    }
    public  void gotohome()
    {
        mref=database.getReference().child("courses");//mref.setValue("courses");
        courses_list=(ListView)findViewById(R.id.courses_list);
        textView=(TextView)findViewById(R.id.textView2);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        courses_list.setAdapter(arrayAdapter);
        mref.addValueEventListener(this);
        courses_list.setOnItemClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(this);
        }
    }

    @Override
    protected void onPause() {
       // Toast.makeText(this,"ompause",Toast.LENGTH_LONG).show();
        super.onPause();
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
                    cref.setValue(course_name);
                }
            });
            mbuilder.setView(addcourse_view);
            addcourse_dialog=mbuilder.create();
            addcourse_dialog.show();
        }
        if(v==findViewById(R.id.signout))
        {
            Toast.makeText(this,"logged out",Toast.LENGTH_SHORT).show();
             mAuth.signOut();
            final Intent intent1=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent1);
        }
    }


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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        intent.putExtra("course_name",arrayList.get(i));
        startActivity(intent);

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Toast.makeText(getApplicationContext(),"authstate changed",Toast.LENGTH_SHORT);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null)
        {
            //Toast.makeText(this,"signed in as"+ user.getEmail(),Toast.LENGTH_SHORT).show();
          //  final Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
          //  startActivity(intent1);
         //gotohome();
        } else {
          //  final Intent intent1=new Intent(getApplicationContext(),LoginActivity.class);
           // startActivity(intent1);

        }
    }
}
