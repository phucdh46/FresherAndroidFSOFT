// IAIDLServerInterface.aidl
package com.example.aidlserver;

import com.example.aidlserver.Student;
// Declare any non-default types here with import statements

interface IAIDLServerInterface {
            //create
            boolean createStudent(in Student student);
            //get all
            List<Student> getAllStudent();
            //find an student
            Student getStudentByName(String name);
            //update
            boolean updateStudent(in Student student);
            //find study has best average
            List<Student> findBestAverage();
            //find study has worst average point
             List<Student> findWorstAverage();
}