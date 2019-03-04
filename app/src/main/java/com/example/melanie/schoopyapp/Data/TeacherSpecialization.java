package com.example.melanie.schoopyapp.Data;

public class TeacherSpecialization {
    private Teacher teacher;
    private Subject subject;

    public TeacherSpecialization(Teacher teacher, Subject subject) {
        this.teacher = teacher;
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "TeacherSpecialization{" + "teacher=" + teacher + ", subject=" + subject + '}';
    }



}
