package com.example.dell.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Dell on 01-03-2017.
 */

public class SignupFragment extends Fragment implements View.OnClickListener {
    FirebaseAuth mAuth;
    View view;
   // Button signup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_signup, container, false);
        mAuth = FirebaseAuth.getInstance();
     Button  signup = (Button)view.findViewById(R.id.signup);
        signup.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == view.findViewById(R.id.signup))
        {
            final EditText editText = (EditText) view.findViewById(R.id.editText);
            final EditText editText3 = (EditText) view.findViewById(R.id.editText3);
            String email = editText.getText().toString();
            String pass = editText3.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(getContext(), "Account Created Succesfully go to login page", Toast.LENGTH_LONG).show();
                    editText.setText("");
                    editText3.setText("");
                }
            });
        }
    }
}

