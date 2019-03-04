package com.example.melanie.schoopyapp.Data;

import java.util.Arrays;

public class Room {
    private String roomNr;    //eindeutig
    private String roomDescription;
    private Department department; //is null if it not a class room
    private double[] roomCoordinates;

    public Room() {
        this("33","33",Department.NONE,new double[]{0,0});
    }

    public Room(String roomNr, String roomDescription, Department department, double[] coord) {
        this.roomNr = roomNr;
        this.roomDescription = roomDescription;
        this.department = department;
        this.roomCoordinates = coord;
    }


    public String getRoomNr() {
        return roomNr;
    }

    public void setRoomNr(String roomNr) {
        this.roomNr = roomNr;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public double[] getRoomCoordinates() {
        return roomCoordinates;
    }

    public void setRoomCoordinates(double[] roomCoordinates) {
        this.roomCoordinates = roomCoordinates;
    }

    @Override
    public String toString() {
        return "Room{" + "roomNr=" + roomNr + ", roomDescription=" + roomDescription + ", department=" + department + ", roomCoordinates=" + roomCoordinates + '}';
    }

}
