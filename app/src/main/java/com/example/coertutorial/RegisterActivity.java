package com.example.coertutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coertutorial.dao.MyLocalDatabase;
import com.example.coertutorial.model.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText tname, temail, tmobile, tpass;
    private Spinner tstate;
    private MyLocalDatabase md;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        initViews();
    }

    private void initViews() {
        tname = findViewById(R.id.regname);
        temail = findViewById(R.id.regemail);
        tmobile = findViewById(R.id.regmobile);
        tpass = findViewById(R.id.regpass);
        tstate = findViewById(R.id.regstate);
        md = new MyLocalDatabase(this);
    }

    public void goToLoginPage(View view) {
        Intent in = new Intent(this, LoginActivity.class);
        startActivity(in);
        finish();
    }

    public void submit(View view) {
        if (valid()){

            User user=new User();
            user.setName(name);
            user.setEmail(email);
            user.setState(state);
            user.setPass(pass);
            user.setMobile(Long.parseLong(mobile));


           long t=md.insert(user);
           if (t>0){
               Toast.makeText(this, "Successfully store", Toast.LENGTH_SHORT).show();
           }else {
               Toast.makeText(this, "Storing Failed", Toast.LENGTH_SHORT).show();
           }
        }
    }

    private String name, email,mobile, pass,state;

    private boolean valid(){
       name = tname.getText().toString();
       email = temail.getText().toString();
       mobile = tmobile.getText().toString();
       pass = tpass.getText().toString();
       state = tstate.getSelectedItem().toString();

       if (TextUtils.isEmpty(name)){
           Toast.makeText(this, "please enter name", Toast.LENGTH_SHORT).show();
           tname.requestFocus();
           return false;
       } else  if (TextUtils.isEmpty(email)){
           Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
           temail.requestFocus();
           return false;
       }else  if (TextUtils.isEmpty(mobile)){
           Toast.makeText(this, "please enter mobile", Toast.LENGTH_SHORT).show();
           tmobile.requestFocus();
           return false;
       }else  if (TextUtils.isEmpty(pass)){
           Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
           tpass.requestFocus();
           return false;
       } else  if (state.equalsIgnoreCase("select state")){
           Toast.makeText(this, "please select state", Toast.LENGTH_SHORT).show();
           return false;
       }else{
           return true;
       }
    }
    public void reset(View view) {
        clear();
    }
    private void clear() {
        tname.setText("");
        temail.setText("");
        tmobile.setText("");
        tpass.setText("");
    }
}