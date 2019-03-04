package com.example.melanie.schoopyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.melanie.schoopyapp.Data.Database;

public class DocumentsActivity extends AppCompatActivity implements Button.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private static Database db;
    private Button btnPublic, btnPrivate;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        try{
            db = Database.newInstance();
            initComponents();
            setListener();
        }catch (Exception ex){
            Toast.makeText(this,"Error: " +ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void setListener() throws Exception{
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);
        btnPublic.setOnClickListener(this);
        btnPrivate.setOnClickListener(this);
    }

    private void initComponents() throws Exception{
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        btnPublic = (Button) findViewById(R.id.btnPublic);
        btnPrivate = (Button) findViewById(R.id.btnPrivate);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnPublic:{
                    startActivity(new Intent(this, PublicActivity.class));
                    break;
                }
                case R.id.btnPrivate:{
                    startActivity(new Intent(this, PrivateActivity.class));
                    break;
                }
            }
        }catch(Exception ex){
            Toast.makeText(this,"Error: " +ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        try{
            switch (menuItem.getItemId()){
                case R.id.homepage:{
                    startActivity(new Intent(this, Start.class));
                    break;
                }
                case R.id.logout:{
                    //current user auf null setzen und ausloggen

                    db.setCurLoggedInStudent(null);
                    Intent intent= new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
                case R.id.myAccount:{
                    startActivity(new Intent(this, MyAccountActivity.class));
                    break;
                }
                case R.id.timetable:{
                    startActivity(new Intent(this, LessonsActivity.class));
                    break;
                }

                case R.id.findTheTeacher:{
                    startActivity(new Intent(this, FindTeacher.class));
                    break;
                }
                case R.id.documents:{
                    startActivity(new Intent(this, DocumentsActivity.class));
                    break;
                }

                default: {
                    Toast.makeText(this," Nothing selected in the menu!!",Toast.LENGTH_LONG).show();
                    break;
                }
            }

        }catch (Exception ex){
            Toast.makeText(this,"Error caused by Menu: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return false;
    }
}
