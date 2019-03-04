package com.example.melanie.schoopyapp;

import android.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.webkit.*;
import android.widget.*;

import com.example.melanie.schoopyapp.Data.*;

import java.io.*;
import java.util.ArrayList;

public class PublicActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static Database db;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    private ListView publicDocs;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int WRITE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        try {
            db = Database.newInstance();
            initComponents();
            setListener();
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            fillListWithDocuments();

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }




        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void fillListWithDocuments() throws Exception {
        ArrayList<PublicFile> pubdocs = db.getAllPublicFiles();

        ArrayAdapter<PublicFile> arrayAdapter = new ArrayAdapter<PublicFile>(
                this,
                android.R.layout.simple_list_item_1,
                pubdocs);

        publicDocs.setAdapter(arrayAdapter);
        publicDocs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                try{
                    PublicFile selFile = (PublicFile) adapterView.getItemAtPosition(pos);
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
                        String newMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                                MimeTypeMap.getFileExtensionFromUrl(url));

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

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

    private void initComponents() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        publicDocs = (ListView) findViewById(R.id.listPublic);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
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
