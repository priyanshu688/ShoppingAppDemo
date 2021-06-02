package com.example.coertutorial.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coertutorial.HomePage;
import com.example.coertutorial.R;
import com.example.coertutorial.dao.MyLocalDatabase;
import com.example.coertutorial.model.User;

public class ProfileFragment extends Fragment {
    private MyLocalDatabase myLocalDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_profile,container,false);
       myLocalDatabase = new MyLocalDatabase(getActivity());
        return view;
    }
     private EditText temail,tname,tpass,tmobile;
    private User user;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       temail = view.findViewById(R.id.profileemail);
        tname = view.findViewById(R.id.profilename);
        tpass = view.findViewById(R.id.profilepassword);
       tmobile = view.findViewById(R.id.profilemobile);
        Button updateButton = view.findViewById(R.id.profileupdate);


        HomePage homePage = (HomePage) getActivity();
        user = homePage.getUser();

        tname.setText(user.getName());
        temail.setText(user.getEmail());
        tmobile.setText(String.valueOf(user.getMobile()));
        tpass.setText(user.getPass());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setName(tname.getText().toString());
                user.setPass(tpass.getText().toString());
                user.setMobile(Long.parseLong(tmobile.getText().toString()));
               long i= myLocalDatabase.updateUser(user);
               if (i>0){
                   Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}
