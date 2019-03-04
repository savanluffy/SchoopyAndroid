package com.example.melanie.schoopyapp.Misc;


import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControllerSynch extends AsyncTask<String, Void, String> {

    private static final String URI_2ND = "WebServiceSchoopy/webresources/";
    private String url_1st;

    public ControllerSynch(String url) throws Exception {
        this.url_1st = url;
    }

    @Override
    protected String doInBackground(String... command) {
        boolean isPut = false, isPost=false,isGetWithHeaders=false, isDelete=false;

        BufferedReader reader = null;
        BufferedWriter writer = null;
        String content = null;
        URL url = null;
        HttpURLConnection conn=null;


        try {
            switch(command[0]){
                case "LOGINSTUDENT" :
                    url = new URL(url_1st + URI_2ND + "students/login");
                    isPost = true;
                    break;
                case "UPDATESTUDENT":
                    url = new URL(url_1st + URI_2ND + "students/update");
                    isPut = true;
                    break;
                case "GETLESSONSBYROOMNR":
                    url = new URL(url_1st + URI_2ND + "lessons/"+command[1]);
                    break;
                case "GETLESSONSBYTEACHERATCURRDAY":
                    url = new URL(url_1st + URI_2ND + "lessons/teachers/"+command[1]+"/"+command[2]);
                    break;
                case "GETALLPUBLICFILES":
                    url = new URL(url_1st + URI_2ND + "publicfiles");
                    break;
                case "GETALLPRIVATEFILESBYROOMNR":
                    url = new URL(url_1st + URI_2ND + "privatefiles/"+command[1]);
                    break;
                case "ADDNEWPRIVATEFILE" :
                    url = new URL(url_1st + URI_2ND + "privatefiles");
                    isPost = true;
                    break;
                default: break;
            }

            if(!isGetWithHeaders){
                conn = (HttpURLConnection) url.openConnection();
            }

            if (isPost) {
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                writer.write(command[1]); //object in json format
                writer.flush();
                writer.close();
                System.out.println("n"+conn.getContentType());
                System.out.println("m:"+conn.getResponseCode());
                System.out.println("noda");
            }
            else if(isPut){
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");
                writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                writer.write(command[1]); //object in json format
                writer.flush();
                writer.close();
                conn.getResponseCode();
            } else if(isDelete){
                conn.setRequestMethod("DELETE");
            }
            if(conn.getResponseCode()==404 || conn.getResponseCode()==400){
                System.out.println("nokkkda");
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

            }else if(conn.getResponseCode()==200){
                System.out.println("nolllllda");
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            }

            StringBuilder sb = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            content = sb.toString();
            reader.close();


            conn.disconnect();
        } catch (Exception ex) {
            content=  ex.getMessage();
        }
        return content;
    }
}
