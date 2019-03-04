package com.example.melanie.schoopyapp;

import android.content.Intent;
import android.os.*;
import android.support.annotation.*;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.melanie.schoopyapp.Data.*;

import java.util.ArrayList;
import java.util.stream.*;

public class LessonsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    public ListView Monday, Tuesday, Wednesday, Thursday, Friday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        try {
            db = Database.newInstance();
            initComponents();
            setListener();

            fillTimetable();
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void fillTimetable() throws Exception {

        ArrayList<Lesson> allLessons = db.getAllLessonsByRoomNr(Database.getCurLoggedInStudent().getVisitedClass().getRoomNr());
        ArrayList<Lesson> mon = new ArrayList<>();
        ArrayList<Lesson> tue = new ArrayList<>();
        ArrayList<Lesson> wed = new ArrayList<>();
        ArrayList<Lesson> thu = new ArrayList<>();
        ArrayList<Lesson> fri = new ArrayList<>();

        //semi prof. still change. maybe one table.
        for (Lesson l : allLessons) {
            switch (l.getWeekDay()) {
                case MONDAY:
                    if ((l.getSchoolHour() - mon.size()) > 1) {
                        for (int i = fri.size(); i < l.getSchoolHour()-1; i++) {
                            mon.add(new Lesson(new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new TeacherSpecialization(new Teacher("", "", "", "FreiStd", ""), new Subject(0, "", "FreiStd")), l.getWeekDay(), i));
                        }
                    }
                    mon.add(l);
                    break;
                case TUESDAY:
                    if ((l.getSchoolHour() - tue.size()) > 1) {
                        for (int i = tue.size(); i < l.getSchoolHour()-1; i++) {
                            tue.add(new Lesson(new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new TeacherSpecialization(new Teacher("", "", "", "FreiStd", ""), new Subject(0, "", "FreiStd")), l.getWeekDay(), i));
                        }
                    }
                    tue.add(l);
                    break;
                case WEDNESDAY:
                    if ((l.getSchoolHour() - wed.size()) > 1) {
                        for (int i = wed.size(); i < l.getSchoolHour() - 1; i++) {
                            wed.add(new Lesson(new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new TeacherSpecialization(new Teacher("", "", "", "FreiStd", ""), new Subject(0, "", "FreiStd")), l.getWeekDay(), i));
                        }
                    }
                    wed.add(l);
                    break;
                case THURSDAY:
                    if ((l.getSchoolHour() - thu.size()) > 1) {
                        for (int i = thu.size(); i < l.getSchoolHour() - 1; i++) {
                            thu.add(new Lesson(new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new TeacherSpecialization(new Teacher("", "", "", "FreiStd", ""), new Subject(0, "", "FreiStd")), l.getWeekDay(), i));
                        }
                    }
                    thu.add(l);
                    break;
                case FRIDAY:
                    if ((l.getSchoolHour() - fri.size()) > 1) {
                        for (int i = fri.size(); i < l.getSchoolHour() - 1; i++) {
                            fri.add(new Lesson(new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new Room("FreiStd", "FreiStd", Department.NONE, new double[]{0, 0}), new TeacherSpecialization(new Teacher("", "", "", "FreiStd", ""), new Subject(0, "", "FreiStd")), l.getWeekDay(), i));
                        }
                    }
                    fri.add(l);
                    break;
                default:
                    break;
            }
        }
        EventTimetablerListAdapter evt = new EventTimetablerListAdapter(this, mon);
        Monday.setAdapter(evt);
        evt = new EventTimetablerListAdapter(this, tue);
        Tuesday.setAdapter(evt);
        evt = new EventTimetablerListAdapter(this, wed);
        Wednesday.setAdapter(evt);
        evt = new EventTimetablerListAdapter(this, thu);
        Thursday.setAdapter(evt);
        evt = new EventTimetablerListAdapter(this, fri);
        Friday.setAdapter(evt);
    }

    private void initComponents() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        Monday = findViewById(R.id.monday);
        Tuesday = findViewById(R.id.tuesday);
        Wednesday = findViewById(R.id.wednesday);
        Thursday = findViewById(R.id.thursday);
        Friday = findViewById(R.id.friday);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setListener() {
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);
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
}
