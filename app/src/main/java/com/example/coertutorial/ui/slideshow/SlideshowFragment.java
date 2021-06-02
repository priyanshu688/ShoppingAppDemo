package com.example.coertutorial.ui.slideshow;

import android.content.Intent;
import android.net.Uri;
import  android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.coertutorial.R;

public class SlideshowFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slideshow,container,false);
    }

   private EditText temail, tsubject, tmessage;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        temail = view.findViewById(R.id.contactemail);
        tsubject = view.findViewById(R.id.contactsubject);
        tmessage = view.findViewById(R.id.contactbody);
       Button button = view.findViewById(R.id.contactbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = temail.getText().toString();
                String subject = tsubject.getText().toString();
                String message = tmessage.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "vaishnavipriyanshu198@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "message");

                startActivity(Intent.createChooser(intent, "Send Email"));
           }
       });
    }
}