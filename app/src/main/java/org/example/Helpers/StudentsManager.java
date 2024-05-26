package org.example.Helpers;

import java.util.ArrayList;
import java.util.Scanner;

import org.example.Model.StudentModel;
import org.example.Structs.Student;

public class StudentsManager {
    private ArrayList<StudentModel> students;

    public StudentsManager() {
        fetchDB();
    }

    void fetchDB() {
        try {
            this.students = StudentModel.getAllStudents();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void add(StudentModel student) {
        if (student != null) {
            this.students.add(student);
        }
    }

    public StudentModel getStudentById(String studentID){
        for(StudentModel student : this.students) {
            if(student.getRoll().equals(studentID)){ 
                return student;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return this.students.isEmpty();
    }

    public ArrayList<StudentModel> getStudents() {
        return this.students;
    }

    public void uploadStudentsFromFile() {

    }

    public void parseAndSave(Scanner scanner) {
        try {

            this.students = new ArrayList<StudentModel>();

            ArrayList<Student> students_parsed = parseFile(scanner);

            for (Student student : students_parsed) {
                try{

                    StudentModel new_stud = new StudentModel(student.getRoll(), student.getName(), student.getEmail());
                    this.students.add(new_stud);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Student> parseFile(Scanner scanner) {
        ArrayList<Student> students = new ArrayList<Student>();

        int roll_idx = 0;
        int name_idx = 1;
        int email_idx = 2;
        String[] header = scanner.nextLine().split(",");

        String first_col_name = header[0].trim().toLowerCase();
        String second_col_name = header[1].trim().toLowerCase();
        String third_col_name = header[2].trim().toLowerCase();

        // *** parse header

        // following part handles if columns are jumbled like roll,email,name
        // email,roll,name etc..

        // 1st column
        switch (first_col_name) {
            case "roll":
                roll_idx = 0;
                break;
            case "name":
                name_idx = 0;
                break;
            case "email":
                email_idx = 0;
                break;
        }

        // 2nd column
        switch (second_col_name) {
            case "roll":
                roll_idx = 1;
                break;
            case "name":
                name_idx = 1;
                break;
            case "email":
                email_idx = 1;
                break;
        }

        // 3rd column
        switch (third_col_name) {
            case "roll":
                roll_idx = 2;
                break;
            case "name":
                name_idx = 2;
                break;
            case "email":
                email_idx = 2;
                break;
        }

        // *** parse rest of body

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(",");

            String roll = line[roll_idx].trim();
            String name = line[name_idx].trim();
            String email = line[email_idx].trim();

            students.add(new Student(name, roll, email));
        }

        return students;
    }
}
