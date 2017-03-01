package com.example.dell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class SigninFragment extends Fragment implements View.OnClickListener{

    View view;
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_signin,container,false);
        Button signin=(Button)view.findViewById(R.id.signin);
        signin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == view.findViewById(R.id.signin)) {
            final EditText emailview = (EditText) view.findViewById(R.id.editText);
            final EditText passview = (EditText) view.findViewById(R.id.editText3);
            String email = emailview.getText().toString();
            String pass = passview.getText().toString();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        emailview.setText("");
                        passview.setText("");
                        Toast.makeText(getContext(), "signed in successfully", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "try again", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
}

