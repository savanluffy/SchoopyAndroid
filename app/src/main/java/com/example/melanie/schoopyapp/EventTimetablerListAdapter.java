package com.example.melanie.schoopyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.melanie.schoopyapp.Data.*;

import java.util.ArrayList;

public class EventTimetablerListAdapter extends BaseAdapter {

        private ArrayList<Lesson> singleRow;
        private AdapterView.OnItemClickListener mListener;
        private LayoutInflater thisInflator;
        private Database db = Database.newInstance();

        public EventTimetablerListAdapter(Context context, ArrayList<Lesson> aRow)
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
                convertView = thisInflator.inflate(R.layout.listviewitem_lesson, parent, false);
            } else {
                convertView.getTag();
            }


            TextView subject = (TextView) convertView.findViewById(R.id.txtSubject);
            TextView teacher = (TextView) convertView.findViewById(R.id.txtTeacher);
            TextView room = (TextView) convertView.findViewById(R.id.txtRoom);

            Lesson currentLesson = (Lesson) getItem(position);

            subject.setText("" + currentLesson.getTeachingInfo().getSubject().getSubjectShortcut());
            teacher.setText(""+currentLesson.getTeachingInfo().getTeacher().getUsername());
            room.setText(""+currentLesson.getTeachingRoom().getRoomNr());

            return convertView;
        }
}
