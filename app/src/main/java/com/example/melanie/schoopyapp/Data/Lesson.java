package com.example.melanie.schoopyapp.Data;

public class Lesson {
    private Room schoolRoom;
    private Room teachingRoom;
    private TeacherSpecialization teachingInfo;
    private WeekDay weekDay;
    private int schoolHour;

    public Lesson(Room schoolRoom, Room teachingRoom, TeacherSpecialization teachingInfo, WeekDay weekDay, int schoolHour) {
        this.schoolRoom = schoolRoom;
        this.teachingRoom = teachingRoom;
        this.teachingInfo = teachingInfo;
        this.weekDay = weekDay;
        this.schoolHour = schoolHour;
    }

    public TeacherSpecialization getTeachingInfo() {
        return teachingInfo;
    }

    public void setTeachingInfo(TeacherSpecialization teachingInfo) {
        this.teachingInfo = teachingInfo;
    }



    public Room getSchoolRoom() {
        return schoolRoom;
    }

    public void setSchoolRoom(Room schoolRoom) {
        this.schoolRoom = schoolRoom;
    }

    public Room getTeachingRoom() {
        return teachingRoom;
    }

    public void setTeachingRoom(Room teachingRoom) {
        this.teachingRoom = teachingRoom;
    }



    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public int getSchoolHour() {
        return schoolHour;
    }

    public void setSchoolHour(int schoolHour) {
        this.schoolHour = schoolHour;
    }




    @Override
    public String toString() {
        return "Lesson{" + "schoolRoom=" + schoolRoom + ", teachingRoom=" + teachingRoom + ", teachingInfo=" + teachingInfo + ", weekDay=" + weekDay + ", schoolHour=" + schoolHour + '}';
    }

}
