package com.example.melanie.schoopyapp.Data;

import com.example.melanie.schoopyapp.Misc.ControllerSynch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Database {
    private static Database db = null;
    private static ControllerSynch controller = null;
    private static String url = null;
    private static Student curLoggedInStudent = null;


    private Database() {
    }

    public static Database newInstance() {
        if (db == null)
            db = new Database();
        return db;
    }

    public static void setURL(String _url) {
        url = _url;
    }

    public static Student getCurLoggedInStudent() {
        return curLoggedInStudent;
    }

    public static void setCurLoggedInStudent(Student curUser) {
        Database.curLoggedInStudent = curUser;
    }

    public Student loginStudent(Student student) throws Exception {
        Gson gson = new Gson();

        controller = new ControllerSynch(url);

        String paras[] = new String[2];
        paras[0] = "LOGINSTUDENT";
        paras[1] = gson.toJson(student, Student.class);
        System.out.println("sodi:"+gson.toJson(student, Student.class));
        controller.execute(paras);
        System.out.println("000");
        String strFromWebService = controller.get();
        System.out.println("s:"+strFromWebService);
        Type collectionType = new TypeToken<Student>() {
        }.getType();
        Student loggedUser = gson.fromJson(strFromWebService, collectionType);
        System.out.println("heey");
        System.out.println("njlk"+loggedUser);
        if (loggedUser == null)
            throw new Exception("wrong pw or username....");

        curLoggedInStudent = loggedUser;
        return loggedUser;
    }


    public void updateStudent(Student student) throws Exception {
        Gson gson = new Gson();
        controller = new ControllerSynch(url);

        String strStudent = gson.toJson(student, Student.class);

        String paras[] = new String[2];
        paras[0] = "UPDATESTUDENT";
        paras[1] = strStudent;
        controller.execute(paras);
        String res = controller.get();
        if (res.length() != 0) {
            throw new Exception(res);
        }
    }


    public ArrayList<PrivateFile> getAllPrivateFiles(String roomNr) throws Exception {
        Gson gson = new Gson();
        controller = new ControllerSynch(url);

        String paras[] = new String[2];
        paras[0] = "GETALLPRIVATEFILESBYROOMNR";
        paras[1] = roomNr;
        controller.execute(paras);

        String strFromWebService = controller.get();


        Type collectionType = new TypeToken<ArrayList<PrivateFile>>() {
        }.getType();
        ArrayList<PrivateFile> vec = gson.fromJson(strFromWebService, collectionType);
        return vec;
    }

    public ArrayList<PublicFile> getAllPublicFiles() throws Exception {
        Gson gson = new Gson();
        controller = new ControllerSynch(url);

        controller.execute("GETALLPUBLICFILES");
        String strFromWebService = controller.get();
        Type collectionType = new TypeToken<ArrayList<PublicFile>>() {}.getType();
        ArrayList<PublicFile> vec = gson.fromJson(strFromWebService, collectionType);

        return vec;
    }

    public ArrayList<Lesson> getAllLessonsByRoomNr(String roomNr) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSynch(url);

        String paras[] = new String[2];
        paras[0] = "GETLESSONSBYROOMNR";
        paras[1] = roomNr;
        controller.execute(paras);

        String strFromWebService = controller.get();


        Type collectionType = new TypeToken<ArrayList<Lesson>>() {
        }.getType();
        ArrayList<Lesson> vec = gson.fromJson(strFromWebService, collectionType);
        return vec;
    }


    public void addPrivateFile(PrivateFile pv) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSynch(url);

        String strPrivateFile = gson.toJson(pv);

        String paras[] = new String[2];
        paras[0] = "ADDNEWPRIVATEFILE";
        paras[1] = strPrivateFile;
        controller.execute(paras);
        String res=  controller.get();
        if(res.length()!=0) {
            throw new Exception(res);
        }
    }

    public ArrayList<Lesson> getAllLessonsByTeacherUNForCurDay(String teacherUN, String wk) throws Exception{
        Gson gson = new Gson();
        controller = new ControllerSynch(url);

        String paras[] = new String[3];
        paras[0] = "GETLESSONSBYTEACHERATCURRDAY";
        paras[1] = teacherUN;
        paras[2] = wk;
        controller.execute(paras);

        String strFromWebService = controller.get();

        Type collectionType = new TypeToken<ArrayList<Lesson>>() {}.getType();
        ArrayList<Lesson> vec = gson.fromJson(strFromWebService, collectionType);
        return vec;


    }
}
