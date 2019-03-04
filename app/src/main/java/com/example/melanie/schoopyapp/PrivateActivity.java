package com.example.melanie.schoopyapp;

import android.content.Intent;
import android.net.*;
import android.os.*;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.webkit.*;
import android.widget.*;

import com.example.melanie.schoopyapp.Data.*;

import java.io.*;
import java.util.ArrayList;

public class PrivateActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CardView.OnClickListener{
    private static Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ListView privateDocs;
    private CardView Upload;
    final int ACTIVITY_CHOOSE_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);

        try {
            db = Database.newInstance();
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            initComponents();
            setListener();
            this.setTitle(db.getCurLoggedInStudent().getVisitedClass().getRoomDescription());
            fillListWithDocuments();
        }catch(Exception ex){
            Toast.makeText(this,"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void fillListWithDocuments() throws Exception {
        ArrayList<PrivateFile> privateDoc=db.getAllPrivateFiles(Database.getCurLoggedInStudent().getVisitedClass().getRoomNr());


        ArrayAdapter<PrivateFile> arrayAdapter = new ArrayAdapter<PrivateFile>(
                this,
                android.R.layout.simple_list_item_1,
                privateDoc );

        privateDocs.setAdapter(arrayAdapter);
        privateDocs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                try{
                    PrivateFile selFile = (PrivateFile) adapterView.getItemAtPosition(pos);
                    // Get the directory for the user's public pictures directory.
                    File folder = new File(Environment.getExternalStorageDirectory() + "/myFolder");
                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdir();
                    }
                    if (success) {
                        //safe file in order to open it later
                        File file= new File(folder.getAbsolutePath() +"/"+ selFile.getFileName());
                        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                        fos.write(selFile.getFileContent());
                        fos.close();

                        //launch intent
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        Uri uri = Uri.fromFile(file);
                        String url = uri.toString();
                        //grab mime
                        String newMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
                        i.setDataAndType(uri, newMimeType);
                        startActivity(i);
                    } else {
                        throw new Exception("could not open folder");
                    }
                }catch(Exception ex){
                    System.out.println("Err:"+ex.getMessage());
                }

            }

        });
    }

    private void initComponents() { mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        Upload= findViewById(R.id.btnUpload);
        Upload.setOnClickListener(this);
        privateDocs = findViewById(R.id.listPrivate);
        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setListener() {
        mDrawerLayout.addDrawerListener(mToggle);
        navigationView.setNavigationItemSelectedListener(this);
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
    public void onClick(View v) {
        try{
            switch(v.getId()){
                case R.id.btnUpload:{
                    Intent chooseFile;
                    Intent intent;
                    chooseFile = new Intent(Intent.ACTION_GET_CONTENT);

                    chooseFile.setType("*/*");

                    intent = Intent.createChooser(chooseFile, "Choose a file");
                    startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
                }
            }
        }catch (Exception ex){
            Toast.makeText(this,"Error caused by Upload: " + ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case ACTIVITY_CHOOSE_FILE: {
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String filePath = uri.getPath();
                    File file = new File(filePath);
                    int size = (int) file.length();
                    byte[] bytes = new byte[size];

                    try {
                        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file)); //error??
                        buf.read(bytes, 0, bytes.length);
                        buf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("finished:" + filePath);
                }
            }
        }

    }
}
