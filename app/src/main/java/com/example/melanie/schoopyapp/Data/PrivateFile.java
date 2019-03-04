package com.example.melanie.schoopyapp.Data;

public class PrivateFile extends PublicFile{
    private Student publisherStudent;
    private Room folderRoom;

    public PrivateFile(int fileId, String fileName, byte[] fileContent, LocalDate publishDate, Teacher publisherTeacher,Student publisherStudent, Room folderRoom) {
        super(fileId, fileName, fileContent, publishDate, publisherTeacher);
        this.publisherStudent = publisherStudent;
        this.folderRoom = folderRoom;
    }



    public Student getPublisherStudent() {
        return publisherStudent;
    }

    public void setPublisherStudent(Student publisherStudent) {
        this.publisherStudent = publisherStudent;
    }

    public Room getFolderRoom() {
        return folderRoom;
    }

    public void setFolderRoom(Room folderRoom) {
        this.folderRoom = folderRoom;
    }

    @Override
    public String toString() {
        String res="";
        if(publisherStudent==null && publisherTeacher!=null){
        res= fileName + " (" + publisherTeacher.getUsername() + "), " + publishDate;
        }else if(publisherStudent != null && publisherTeacher==null){
            res= fileName + " (" + publisherStudent.getUsername() + "), " + publishDate;
        }
        return res;
    }

}
