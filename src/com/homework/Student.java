package com.homework;

import sun.security.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Student implements Comparable<Student> {
    final private String name;
    final private String surname;
    final private Classroom classroom;
    final private String subject;
    final private int score;


    public Student(String name, String surname, Classroom classroom, String subject, int score) {
        this.name = name;
        this.surname = surname;
        this.classroom = classroom;
        this.subject = subject;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public String getSubject() {
        return subject;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Student student) {
        return student.getScore() - this.getScore();
    }

    static Student fromString(String string, ArrayList<Classroom> classrooms) {
        String[] words = string.split("\\s+");
        if(words.length < 5) return null;
        String name = words[1];
        String surname = words[0];
        int classroomNumber;
        try {
            classroomNumber = Integer.parseUnsignedInt(words[2]);
        } catch (Exception e) {
            return null;
        }
        Classroom classroom;
        try {
            classroom = classrooms.stream().filter((classroom1) -> classroom1.getNumber() == classroomNumber).findFirst().get();
        } catch (Exception e) {
            classroom = new Classroom(classroomNumber);
            classrooms.add(classroom);
        }
        String subject = words[3];
        int score;
        try {
            score = Integer.parseUnsignedInt(words[4]);
        } catch (Exception e) {
            return null;
        }
        Student student = new Student(name, surname, classroom, subject, score);
        classroom.addStudent(student);
        return student;
    }
}
