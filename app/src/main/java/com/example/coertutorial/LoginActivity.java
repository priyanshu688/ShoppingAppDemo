package com.example.coertutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coertutorial.dao.MyLocalDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
     private MyLocalDatabase md;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
         setContentView(R.layout.login);

         initViews();
    }
    private EditText temail, tpass;
    private CheckBox tcheck;

    private void initViews(){

         temail = findViewById(R.id.loginemail);
         tpass = findViewById(R.id.loginpassword);
         tcheck = findViewById(R.id.logincheckbox);
         md = new MyLocalDatabase(this);
        SingleTask singleTask = (SingleTask) getApplication();
        sp = singleTask.getSharedPreferences();
    }

    public void goToRegisterPage(View view){

        Intent in=new Intent(this,RegisterActivity.class);
        startActivity(in);
        finish();
    }
    public void login(View view){

      boolean forget = tcheck.isChecked();

      if (forget){
          //forget password code here , if checked
      }else{
          //Toast.makeText(this,email+"\t"+pass, Toast.LENGTH_SHORT).show();

          if (valid()){
              //store data into database
            boolean flag =  md.login(email,pass);
            if (flag){
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor e  = sp.edit();
                e.putBoolean("status",true);
                e.putString("email",email);
                e.commit();

                Intent in =new Intent(this,HomePage.class);
                startActivity(in);
                finish();
            }else{
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }

          }
      }

    }
    private String email,pass;

    private boolean valid(){
        email = temail.getText().toString();
         pass = tpass.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!isEmailValidated(email)){
            Toast.makeText(this, "please enter email in proper format", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(pass)){
            Toast.makeText(this, "please enter password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private String emailPattern="^[a-zA-Z0-9]{1,20}@[a-zA-Z]{1,20}.[a-zA-Z]{2,3}$";
   private String passwordPattern="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!?&()@$%#]).{6,15})";

    private boolean isEmailValidated(String email){
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValidated(String password){
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
