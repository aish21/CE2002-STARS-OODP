package student;

//Imports
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import displayIO.*;

/**
 * Class to store and retrieve Student data from a binary file student.dat
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17
 */
public class StudentIO extends IO {
	
	/**
	 * Method to read in a new Student's details from the User
	 * @param matricNo Matriculation Number of the new student
	 * @return A student object with the new student's details
	 */
	private static Student inputStudent(String matricNo, Scanner sc) {
		
		//Reading in the student information
		System.out.println("Student Name: ");
		String studentName = sc.nextLine();
		System.out.println("Username: ");
		String username = sc.next();
		String password;
		do {
			System.out.println("Password: ");
			password = sc.next();
		if (password.length() < 8) {
			System.out.println("Weak Password, Try again.");
		} }while(password.length() < 8);
		String encrypted = encrypt(password, 5);
		System.out.println("Gender: ");
		String gender = sc.next();
		sc.nextLine();
		System.out.println("Address: ");
		String Address = sc.nextLine();
		System.out.println("Nationality: ");
		String Nationality = sc.next();
		sc.nextLine();
		
		//Creating a Student Object to be returned 
		Student A = new Student(studentName, username, encrypted, matricNo, gender, Address, Nationality);
		return A;
	}
	
	/**
	 * Method to check whether the provided username and password are correct
	 * @param username
	 * @param password
	 * @return Returns a student object whose username and password are provided. If not found, returns null
	 */
	public static Student check(String username, String password) {
		filepath = "student.dat";
		ArrayList<Object> studentList = (ArrayList<Object>) readSerializedObject();
		if (studentList != null)
			for (int counter = 0; counter < studentList.size(); counter++) { 		      
		        Object B = studentList.get(counter);
				if (B instanceof Student)
				{
					Student C = (Student) B;
					String pass = encrypt(C.getStudentPassword(),-5);
					if (username.equals(C.getUsername()) && password.equals(pass)) {
						return C;
					}
				}
			}
		return null;
	}
	
	/**
	 * Method to check if the provided matriculation card number is present in the student records
	 * @param matricNo Matriculation number to be checked
	 * @return true if the record exists
	 */
	public static boolean checkMatricNo(String matricNo) {
		filepath = "student.dat";
		ArrayList<Object> studentList = (ArrayList<Object>) readSerializedObject();
		if (studentList != null)
			for (int counter = 0; counter < studentList.size(); counter++) { 		      
		        Object B = studentList.get(counter);
				if (B instanceof Student)
				{
					Student C = (Student) B;
					if (matricNo.equals(C.getMatriculation_Card())) {
						return true;
					}
				}
			}
		return false;
	}
	
	public static Student getStudent(String matricNo) {
		filepath = "student.dat";
		ArrayList<Object> studentList = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < studentList.size(); counter++) { 		      
	        Object B = studentList.get(counter);
			if (B instanceof Student)
			{
				Student C = (Student) B;
				if (matricNo.equals(C.getMatriculation_Card())) {
					return C;
				}
			}
		}
		return null;
	}
		
	/**
	 * Method to edit a student record in the file
	 * @param S New student record that has to replace the old record
	 */
	@SuppressWarnings("unchecked")
	public static void editStudent(Student S) {
		filepath = "student.dat";
		ArrayList<Object> studentList = (ArrayList<Object>) readSerializedObject();
		
		for (int counter = 0; counter < studentList.size(); counter++) { 		      
	        Object B = studentList.get(counter);
			if (B instanceof Student)
			{
				Student C = (Student) B;
				if (S.getMatriculation_Card().equals(C.getMatriculation_Card())) {
					studentList.remove(counter);
					studentList.add(counter, S);
				}
			}
		}
		writeSerializedObject(studentList);
	}
	
	public static void editStudent(Scanner sc) {
		filepath = "student.dat";
		ArrayList<Object> studentList = (ArrayList<Object>) readSerializedObject();
		
		System.out.println("Enter Matriculation Number: ");
		String matricNo = sc.next();
		if (checkMatricNo(matricNo)) {
			System.out.println("Enter Matriculation Number: ");
			String matricNo1 = sc.next();
			sc.nextLine();
			Student S = inputStudent(matricNo1, sc);
			for (int counter = 0; counter < studentList.size(); counter++) { 		      
		        Object B = studentList.get(counter);
				if (B instanceof Student)
				{
					Student C = (Student) B;
					if (matricNo.equals(C.getMatriculation_Card())) {
						studentList.remove(counter);
						studentList.add(counter, S);
					}
				}
			}
			writeSerializedObject(studentList);
			System.out.println("Student edited.");
		}
		
	}
	/**
	 * Method to read all the Student Records from the file
	 * @return List of all students stored in the records
	 */
	public static ArrayList<Student> returnAllStudents() {
		filepath = "student.dat";
		ArrayList<Student> fromFile = (ArrayList<Student>) readSerializedObject();
		return fromFile;
		/*for (int counter = 0; counter < fromFile.size(); counter++) { 		      
			Object B = fromFile.get(counter);
			if (B instanceof Student)
			{
				Student C = (Student) B;
				studentList.add(C);
			}
		}
		return studentList;*/
	}
	
	/**
	 * Method to add a Student in to the Binary file
	 */
	public static void addStudent(Scanner sc) {
		filepath = "student.dat";
		System.out.println("Matriculation Number: ");
		String matricNo = sc.next();
		sc.nextLine();
		
		//Checking if the Student is already present in the file
		if (checkMatricNo(matricNo)) {
			System.out.println("Student already exists. Please edit Student instead.");
			return;
		}
		
		//Creating a Student object and writing to file
		Student S = inputStudent(matricNo, sc);
		ArrayList<Student> studentList = (ArrayList<Student>) readSerializedObject();
		if (studentList == null)
			studentList = new ArrayList<Student>();
		studentList.add(S);
		writeSerializedObject(studentList);
		System.out.println("Student added.");
		displayStudents();
	}
	
	/**
	 * Method to display all the students stored in the Binary file
	 */
	public static void displayStudents() {
		filepath = "student.dat";
		ArrayList<Object> studentList = (ArrayList<Object>) readSerializedObject();
		if (studentList != null) {
			System.out.print("Number of Students read: ");
			System.out.println(studentList.size());
			for (int counter = 0; counter < studentList.size(); counter++) { 		      
		        Object B = studentList.get(counter);
				if (B instanceof Student)
				{
					Student C = (Student) B;
					C.printStudent();
				}
			}
		}
	}
	
	public static String nameFromMatricNo(String MatricNo) {
		filepath = "student.dat";
		ArrayList<Object> studentList = (ArrayList<Object>) readSerializedObject();
		if (studentList != null) {
			for (int counter = 0; counter < studentList.size(); counter++) { 		      
		        Object B = studentList.get(counter);
				if (B instanceof Student)
				{
					Student C = (Student) B;
					if(C.getMatriculation_Card().equals(MatricNo)) {
						return C.getStudentName();
					}
				}
			}
		}
		return null;
	}
}