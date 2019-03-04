package com.example.melanie.schoopyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.melanie.schoopyapp.Data.*;

import java.util.ArrayList;

public class EventTeacherListAdapter extends BaseAdapter {

        private ArrayList<Lesson> singleRow;
        private AdapterView.OnItemClickListener mListener;
        private LayoutInflater thisInflator;
        private Database db = Database.newInstance();

        public EventTeacherListAdapter(Context context, ArrayList<Lesson> aRow)
        {
            this.singleRow = aRow;
            this.thisInflator = (LayoutInflater.from(context));
        }
        @Override
        public int getCount() {
            return singleRow.size();
        }

        @Override
        public Object getItem(int position) {
            return singleRow.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = thisInflator.inflate(R.layout.listviewitem_teacher, parent, false);
            } else {
                convertView.getTag();
            }

            EditText NameOfTeacher = (EditText) convertView.findViewById(R.id.txtTeacherName);
            TextView hour = (TextView) convertView.findViewById(R.id.txtHour);
            TextView whereIsTeacher = (TextView) convertView.findViewById(R.id.txtWhere);
            ImageView teacherImage = (ImageView) convertView.findViewById(R.id.imgKategorie);

            Lesson currentTeacherLessons = (Lesson) getItem(position);

            NameOfTeacher.setText("" + currentTeacherLessons.getTeachingInfo().getTeacher().getUsername());

            switch (currentTeacherLessons.getSchoolHour()){
                case 1:{
                    hour.setText("8:00 - 8:50");
                    break;
                }
                case 2:{
                    hour.setText("8:50 - 9:40");
                    break;
                }
                case 3:{
                    hour.setText("9:40- 10:30");
                    break;
                }
                case 4:{
                    hour.setText("10:40- 11:30");
                    break;
                }
                case 5:{
                    hour.setText("11:30- 12:20");
                    break;
                }
                case 6:{
                    hour.setText("12:20- 13:10");
                    break;
                }
                case 7:{
                    hour.setText("13:20- 14:10");
                    break;
                }
                case 8:{
                    hour.setText("14:10- 15:00");
                    break;
                }
                case 9:{
                    hour.setText("15:00- 15:50");
                    break;
                }
                case 10:{
                    hour.setText("16:00- 16:50");
                    break;
                }
            }
            whereIsTeacher.setText(""+currentTeacherLessons.getTeachingRoom().getRoomNr());
            teacherImage.setImageResource(R.drawable.teacher);

            return convertView;
        }
}
