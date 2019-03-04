package com.example.melanie.schoopyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.melanie.schoopyapp.Data.Database;
import com.example.melanie.schoopyapp.Data.Department;
import com.example.melanie.schoopyapp.Data.Room;
import com.example.melanie.schoopyapp.Data.Student;
import com.example.melanie.schoopyapp.R;

public class LoginActivity extends AppCompatActivity implements CardView.OnClickListener{
    private CardView login;
    private EditText username, password;

    private static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try{
            db = Database.newInstance();
            db.setURL("http://192.168.1.3:8080/");
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();

            initComponents();
            setListener();
        }catch (Exception ex){
            Toast.makeText(this,"Error: " +ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void setListener() throws Exception{
        login.setOnClickListener(this);
    }

    private void initComponents() throws Exception{
        login = (CardView) findViewById(R.id.btnLogin);
        username = (EditText) findViewById(R.id.txtUsername);
        password=(EditText) findViewById(R.id.txtPassword);
    }


    @Override
    public void onClick(View view) {
        try{
            switch(view.getId()){
                case R.id.btnLogin:{
                    checkAndLogin();
                    Intent intent =new Intent(this, Start.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
            }

        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void checkAndLogin() throws Exception {
        if(username.getText().toString().equals("")){
            throw new Exception("Fill in your username");
        }
        else if(password.getText().toString().equals("")){
            throw new Exception("Fill in your password");
        }
        Student s = new Student(new Room("33","kj",Department.IT,new double[]{0.0,0.0}),"sd","sd","o",username.getText().toString(),password.getText().toString());
        db.loginStudent(s);
    }
}
