package com.example.melanie.schoopyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.melanie.schoopyapp.Data.Database;
import com.example.melanie.schoopyapp.Data.Student;

public class MyAccountActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CardView.OnClickListener {

    private static Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private CardView changePassword;
    private EditText username, firstname, lastname, email, classroom, passwordNew, passwordRp;           //rp == repeat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        try {
            db = Database.newInstance();
            initComponents();
            setListener();

            username.setEnabled(false);
            firstname.setEnabled(false);
            lastname.setEnabled(false);
            email.setEnabled(false);
            classroom.setEnabled(false);
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void initComponents() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        changePassword = (CardView) findViewById(R.id.btnChangePW);

        username = (EditText) findViewById(R.id.txtUsername);
        firstname = (EditText) findViewById(R.id.txtFirstName);
        lastname = (EditText) findViewById(R.id.txtLastName);
        email = (EditText) findViewById(R.id.txtEmail);
        classroom = (EditText) findViewById(R.id.txtClass);
        passwordNew = (EditText) findViewById(R.id.txtPassword);
        passwordRp = (EditText) findViewById(R.id.txtPasswordR);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Student curLoggedIn = db.getCurLoggedInStudent();
        username.setText("" + curLoggedIn.getUsername());
        firstname.setText("" + curLoggedIn.getFirstName());
        lastname.setText("" + curLoggedIn.getLastName());
        email.setText("" + curLoggedIn.getSchoolemail());
        classroom.setText("" + curLoggedIn.getVisitedClass().getRoomDescription());
    }

    private void setListener() {
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);
        changePassword.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        try {
            switch (menuItem.getItemId()) {
                case R.id.homepage: {
                    startActivity(new Intent(this, Start.class));
                    break;
                }
                case R.id.logout: {
                    //current user auf null setzen und ausloggen
                    db.setCurLoggedInStudent(null);
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
                case R.id.myAccount: {
                    startActivity(new Intent(this, MyAccountActivity.class));
                    break;
                }
                case R.id.timetable: {
                    startActivity(new Intent(this, LessonsActivity.class));
                    break;
                }

                case R.id.findTheTeacher: {
                    startActivity(new Intent(this, FindTeacher.class));
                    break;
                }
                case R.id.documents: {
                    startActivity(new Intent(this, DocumentsActivity.class));
                    break;
                }

                default: {
                    Toast.makeText(this, " Nothing selected in the menu!!", Toast.LENGTH_LONG).show();
                    break;
                }
            }

        } catch (Exception ex) {
            Toast.makeText(this, "Error caused by Menu: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnChangePW: {
                    Student s = db.getCurLoggedInStudent();
                    if (!(passwordNew.getText().toString().equals(passwordRp.getText().toString()))) {
                        throw new Exception(passwordNew.getText() + "Neue Passwörter stimmem nicht überein!" + passwordRp.getText());
                    }
                    String text = passwordNew.getText().toString();
                    s.setPassword(text);
                    db.updateStudent(s);

                    Intent intent = new Intent(this, Start.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
