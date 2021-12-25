package com.homework;

import java.util.ArrayList;
import java.util.Arrays;

public class Classroom {
    private final int number;
    private final ArrayList<Student> students;

    public Classroom(int number) {
        this.number = number;
        students = new ArrayList<>();
    }

    public Classroom(int number, Student[] students) {
        this.number = number;
        this.students = new ArrayList<>(Arrays.asList(students));
    }


    public int getNumber() {
        return number;
    }

    public boolean addStudent(Student student) {
        return students.add(student);
    }

    public Student[] getStudents() {
        return students.toArray(new Student[0]);
    }
}
