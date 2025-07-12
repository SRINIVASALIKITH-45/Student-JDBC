package com.likith.app;

import com.likith.model.Student;
import com.likith.jdbc.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentApp {
    private static final Logger LOGGER = Logger.getLogger(StudentApp.class.getName());

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            LOGGER.info("Enter Student Name: ");
            String name = sc.nextLine();

            LOGGER.info("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine(); 

            LOGGER.info("Enter Course: ");
            String course = sc.nextLine();

            Student student = new Student(name, age, course);

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO students (name, age, course) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, student.getName());
                    stmt.setInt(2, student.getAge());
                    stmt.setString(3, student.getCourse());

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        LOGGER.info("Student inserted successfully.");
                    } else {
                        LOGGER.warning(" No rows were inserted.");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, " Error inserting student: ", e);
        }
    }
}
