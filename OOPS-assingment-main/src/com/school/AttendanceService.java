package com.school;

import java.util.ArrayList;
import java.util.List;

public class AttendanceService {
    private List<AttendanceRecord> attendanceLog;
    private FileStorageService storageService;

    public AttendanceService(FileStorageService storageService) {
        this.attendanceLog = new ArrayList<>();
        this.storageService = storageService;
    }

    public void markAttendance(Student student, Course course, String status) {
        AttendanceRecord record = new AttendanceRecord(student, course, status);
        attendanceLog.add(record);
    }

    public void markAttendance(int studentId, int courseId, String status, List<Student> allStudents, List<Course> allCourses) {
        Student student = findStudentById(studentId, allStudents);
        Course course = findCourseById(courseId, allCourses);
        
        if (student == null) {
            System.out.println("Warning: Student with ID " + studentId + " not found.");
            return;
        }
        
        if (course == null) {
            System.out.println("Warning: Course with ID " + courseId + " not found.");
            return;
        }
        
        markAttendance(student, course, status);
    }

    private Student findStudentById(int studentId, List<Student> allStudents) {
        for (Student student : allStudents) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        return null;
    }

    private Course findCourseById(int courseId, List<Course> allCourses) {
        for (Course course : allCourses) {
            if (course.getCourseId() == courseId) {
                return course;
            }
        }
        return null;
    }

    public void displayAttendanceLog() {
        System.out.println("\n--- Attendance Log (All Records) ---");
        if (attendanceLog.isEmpty()) {
            System.out.println("No attendance records yet.");
        } else {
            for (AttendanceRecord record : attendanceLog) {
                record.displayRecord();
            }
        }
    }

    public void displayAttendanceLog(Student student) {
        System.out.println("\n--- Attendance Log for Student: " + student.getName() + " (ID: " + student.getId() + ") ---");
        boolean found = false;
        for (AttendanceRecord record : attendanceLog) {
            if (record.getStudent().getId() == student.getId()) {
                record.displayRecord();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No attendance records found for this student.");
        }
    }

    public void displayAttendanceLog(Course course) {
        System.out.println("\n--- Attendance Log for Course: " + course.getCourseName() + " (ID: C" + course.getCourseId() + ") ---");
        boolean found = false;
        for (AttendanceRecord record : attendanceLog) {
            if (record.getCourse().getCourseId() == course.getCourseId()) {
                record.displayRecord();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No attendance records found for this course.");
        }
    }

    public void saveAttendanceData() {
        storageService.saveData(attendanceLog, "attendance_log.txt");
    }
}

