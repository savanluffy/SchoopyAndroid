package com.example.melanie.schoopyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.melanie.schoopyapp.Data.*;

import java.util.*;

public class FindTeacher extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener{

    private Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private SearchView searching;
    private ListView listFindTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_teacher);

        try {
            db= Database.newInstance();
            initComponents();
            setListener();
        }catch(Exception ex){
            Toast.makeText(this,"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    private void initComponents() {
        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        searching = (SearchView) findViewById(R.id.searching);
        listFindTeacher= (ListView) findViewById(R.id.list_find_teacher);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setListener() {
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);
        searching.setOnQueryTextListener(this);
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

    @Override
    public boolean onQueryTextSubmit(String teacherUN) {
        try {
            Calendar currentDate=new GregorianCalendar();
            String dayOfWeek = currentDate.getDisplayName( Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.US);
            ArrayList<Lesson> curTeacherLessons=db.getAllLessonsByTeacherUNForCurDay(teacherUN,dayOfWeek.toUpperCase());
            EventTeacherListAdapter evt = new EventTeacherListAdapter(this, curTeacherLessons);
            listFindTeacher.setAdapter(evt);

        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
