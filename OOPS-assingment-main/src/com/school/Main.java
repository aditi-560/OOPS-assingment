package com.school;

import java.util.ArrayList;
import java.util.List;

public class Main {

    // Helper method to demonstrate polymorphism with Person objects
    public static void displaySchoolDirectory(List<Person> people) {
        System.out.println("\n--- School Directory ---");
        if (people.isEmpty()) {
            System.out.println("No people in the directory.");
            return;
        }
        for (Person person : people) {
            person.displayDetails(); // Polymorphic call
        }
    }

    public static void main(String[] args) {
        System.out.println("--- School Administration & Attendance System (Polymorphism Demo) ---");

        // --- Data Setup ---
        Student student1 = new Student("Alice Wonderland", "Grade 10");
        Student student2 = new Student("Bob The Builder", "Grade 9");
        Teacher teacher1 = new Teacher("Dr. Emily Carter", "Physics");
        Staff staff1 = new Staff("Mr. John Davis", "Librarian");

        List<Person> schoolPeople = new ArrayList<>();
        schoolPeople.add(student1);
        schoolPeople.add(student2);
        schoolPeople.add(teacher1);
        schoolPeople.add(staff1);

        // Demonstrate polymorphic call through a helper method
        displaySchoolDirectory(schoolPeople);

        // --- Course Setup ---
        Course course1 = new Course("Intro to Quantum Physics");
        Course course2 = new Course("Advanced Algorithms");
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        System.out.println("\n\n--- Available Courses ---");
        for(Course c : courses) c.displayDetails();

        // --- Create FileStorageService and AttendanceService instances ---
        FileStorageService storageService = new FileStorageService();
        AttendanceService attendanceService = new AttendanceService(storageService);

        // --- Create lists of Student objects (allStudents) and Course objects (allCourses) ---
        List<Student> allStudents = new ArrayList<>();
        allStudents.add(student1);
        allStudents.add(student2);
        
        List<Course> allCourses = new ArrayList<>();
        allCourses.add(course1);
        allCourses.add(course2);

        // --- Call the different overloaded versions of attendanceService.markAttendance(...) ---
        // Using Student and Course objects directly
        attendanceService.markAttendance(student1, course1, "Present");
        attendanceService.markAttendance(student2, course1, "Absent");
        attendanceService.markAttendance(student1, course2, "Daydreaming"); // Invalid status
        
        // Using studentId and courseId with lookup
        attendanceService.markAttendance(student1.getId(), course1.getCourseId(), "Present", allStudents, allCourses);
        attendanceService.markAttendance(student2.getId(), course2.getCourseId(), "Absent", allStudents, allCourses);

        // --- Call the different overloaded versions of attendanceService.displayAttendanceLog(...) ---
        attendanceService.displayAttendanceLog(); // Display all records
        attendanceService.displayAttendanceLog(student1); // Display records for student1
        attendanceService.displayAttendanceLog(course1); // Display records for course1

        // --- Saving Data (Storable interface still uses IDs for simplicity) ---
        System.out.println("\n\n--- Saving Data to Files ---");
        // We need to convert List<Person> to List<Student> or handle saving different person types.
        // For simplicity, let's save only students from the schoolPeople list if they are students.
        List<Student> studentsToSave = new ArrayList<>();
        for(Person p : schoolPeople){
            if(p instanceof Student){
                studentsToSave.add((Student) p);
            }
        }
        if(!studentsToSave.isEmpty()){
            storageService.saveData(studentsToSave, "students.txt");
        } else {
             System.out.println("No student data to save from school directory.");
        }

        storageService.saveData(courses, "courses.txt");
        
        // --- Call attendanceService.saveAttendanceData() at the end ---
        attendanceService.saveAttendanceData();

        System.out.println("\nSession 7: Polymorphic Behaviour Demonstrated Complete.");
    }
}