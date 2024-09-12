package com.mycompany.appointmentmanagement;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Doctor {
    private String name;
    private String specialization;
    private String availability;

    public Doctor(String name, String specialization, String availability) {
        this.name = name;
        this.specialization = specialization;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void displayAvailability() {
        System.out.println("Doctor " + name + " is available at " + availability);
    }
}

class GeneralPractitioner extends Doctor {
    public GeneralPractitioner(String name, String availability) {
        super(name, "General Practitioner", availability);
    }

    @Override
    public void displayAvailability() {
  System.out.println("General Practitioner " + getName() + " is available for walk-in patients at " + getAvailability());
    }
}

class Specialist extends Doctor {
    public Specialist(String name, String availability) {
        super(name, "Specialist", availability);
    }

    @Override
    public void displayAvailability() {
        System.out.println("Specialist " + getName() + " requires an appointment. Availability: " + getAvailability());
    }
}

class Patient {
    private String name;
    private int age;

    public Patient(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String date;

    public Appointment(Doctor doctor, Patient patient, String date) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDate() {
        return date;
    }

    public void saveToFile() {
        try (FileWriter fw = new FileWriter("appointments.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("Appointment:");
            out.println("Doctor: " + doctor.getName() + " (" + doctor.getSpecialization() + ")");
            out.println("Patient: " + patient.getName() + " (Age: " + patient.getAge() + ")");
            out.println("Date: " + date);
            out.println();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the appointment.");
        }
    }
}

public class AppointmentManagement {
    private static ArrayList<Doctor> doctors = new ArrayList<>();
    private static ArrayList<Patient> patients = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeDoctors();

        while (true) {
            System.out.println("1. Register Patient");
            System.out.println("2. View Doctors");
            System.out.println("3. Book Appointment");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    viewDoctors();
                    break;
                case 3:
                    bookAppointment();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeDoctors() {
        doctors.add(new GeneralPractitioner("Dr. Mahmudul Hasan", "9:00 AM - 5:00 PM"));
        doctors.add(new Specialist("Dr. Sultana Razia", "10:00 AM - 4:00 PM"));
        doctors.add(new Specialist("Dr. Ziaur Rahman", "11:00 AM - 3:00 PM"));
    }

    private static void registerPatient() {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline

        patients.add(new Patient(name, age));
        System.out.println("Patient registered successfully.");
    }

    private static void viewDoctors() {
        for (Doctor doctor : doctors) {
            doctor.displayAvailability();
        }
    }

    private static void bookAppointment() {
        System.out.print("Enter patient name: ");
        String patientName = scanner.nextLine();
        Patient patient = findPatientByName(patientName);

        if (patient == null) {
            System.out.println("Patient not found. Please register first.");
            return;
        }

        System.out.print("Enter doctor name: ");
        String doctorName = scanner.nextLine();
        Doctor doctor = findDoctorByName(doctorName);

        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        System.out.print("Enter appointment date (e.g., 2024-09-12): ");
        String date = scanner.nextLine();

        Appointment appointment = new Appointment(doctor, patient, date);
        appointment.saveToFile();
        System.out.println("Appointment booked successfully.");
    }

    private static Patient findPatientByName(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equals(name)) {
                return patient;
            }
        }
        return null;
    }

    private static Doctor findDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equals(name)) {
                return doctor;
            }
        }
        return null;
    }
}
