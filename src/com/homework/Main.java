package com.homework;

import javax.security.auth.Subject;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private enum VerboseMode {
        None,
        Default,
        SubjectSorted
    }

    public static void main(String[] args) {

        VerboseMode verboseMode = VerboseMode.Default;

        if (args.length < 1) {
            System.err.println("You must pass filename as command line argument");
            return;
        }

        try {
            String verboseModeString = args[1].split("=")[1];
            switch (verboseModeString) {
                case "none":
                    verboseMode = VerboseMode.None;
                    break;
                case "subject":
                    verboseMode = VerboseMode.SubjectSorted;
                    break;
                default:
                    verboseMode = VerboseMode.Default;
            }
        } catch (Exception ignored) {
        }

        File file = new File(args[0]);

        ArrayList<Classroom> classrooms = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader rd = new BufferedReader(new FileReader(file))) {
            for (String line = rd.readLine(); line != null; line = rd.readLine()) {
                Student student = Student.fromString(line, classrooms);
                if (student == null) {
                    System.err.printf("Line [%s] malformed", line);
                } else students.add(student);
            }
        } catch (FileNotFoundException e) {
            System.err.printf("File [%s] not found", file.getName());
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (verboseMode != VerboseMode.None) {
            System.out.println("Среднее арифметическое каждого класса:%n");
            for (Classroom classroom : classrooms) {
                System.out.printf(">>> Класс номер %d:%n", classroom.getNumber());
                double scoreSum = 0;
                for (Student student : classroom.getStudents()) scoreSum += student.getScore();
                System.out.printf(">>>>> Среднее арифметическое оценок %f%n", scoreSum / classroom.getStudents().length);
                if (verboseMode == VerboseMode.Default) {
                    System.out.printf(">>>>> Список школьников%n");
                    int ix = 1;
                    for (Object obj : Arrays.stream(classroom.getStudents()).sorted().toArray()) {
                        Student student = (Student) obj;
                        System.out.printf("%d: %s %s %s %d%n", ix++, student.getName(), student.getSurname(), student.getSubject(), student.getScore());
                    }
                    System.out.println();
                }
            }
            if (verboseMode == VerboseMode.SubjectSorted) {
                for (Object obj : students.stream().map(Student::getSubject).sorted().toArray()) {
                    String subject = (String) obj;
                    System.out.printf("%n>>> Оценки по %s:%n", subject);
                    for (Object obj1 : classrooms.stream().sorted((c1, c2) -> c1.getNumber() - c2.getNumber()).toArray()) {
                        Classroom classroom = (Classroom) obj1;
                        int ix = 1;
                        System.out.printf(">>>>> Для класса %d:%n", classroom.getNumber());
                        for (Object obj2 : Arrays.stream(Arrays.stream(classroom.getStudents()).sorted((s1, s2) -> (s1.getName() + s1.getSurname()).compareTo(s2.getName() + s2.getSurname())).toArray()).filter(student -> Objects.equals(((Student) student).getSubject(), subject)).toArray()) {
                            Student student = (Student) obj2;
                            System.out.printf("%d: %s %s %s %d%n", ix++, student.getName(), student.getSurname(), student.getSubject(), student.getScore());
                        }
                    }
                }
            }
        } else {
            System.out.println("Введите название предмета:");
            String subject = (new Scanner(System.in)).nextLine();

            int ix = 1;
            for (Object obj : students.stream().filter(student -> subject.equals(student.getSubject())).sorted().toArray()) {
                Student student = (Student) obj;
                System.out.printf("%d: %s %s %d%n", ix++, student.getName(), student.getSurname(), student.getScore());
            }
        }
    }
}
