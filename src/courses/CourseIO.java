package courses;

import java.util.Scanner;
import java.time.*;
import java.util.ArrayList;
import displayIO.IO;
import student.Student;

/**
 * Class to store and retrieve Course data from a binary file course.bin
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17
 */
public class CourseIO extends IO {
	private static ArrayList<Course> Courses = new ArrayList<Course>();
	
	/**
	 * Function to check whether the time entered is valid
	 * @param i1
	 * @param i2
	 * @return true if the time is valid
	 */
	private static boolean validateTime(String i1, String i2) {
		int a = 0;
		int b = 0;
		if (i1.matches("[0-9]+")) {
			a = Integer.parseInt(i1);
		}
		else 
			return false;
		if (i2.matches("[0-9]+")) {
			b = Integer.parseInt(i2);
		}
		else
			return false;
		if (0 <= a && a <= 24) {
			if (0 <= b && b <= 60) {
				return true;
			}
			else
				return false;
		}
		else
			return false;
	
	}
	
	/**
	 * Method to read in new Course data from the user
	 * @param code New Course Code
	 * @return A Course object containing the new course data
	 */
	private static Course inputCourse(String code, Scanner sc) {
		
		//Reading in the course information
		System.out.println("Course Name: ");
		String courseName = sc.nextLine();
		System.out.println("School offering the course: ");
		String school = sc.nextLine();
		int AUs = 0;
		while (AUs == 0) {
			System.out.println("Course AUs: ");
			String string_AUs = sc.next();
			if (string_AUs.matches("[0-9]+")) {
				AUs = Integer.parseInt(string_AUs);
			}
			else 
				System.out.println("AUs can only be an integer. Please re-enter.");
		}
		System.out.println("Course Type (Core, GER-Core, GERPE-BM, GERPE-LA, GERPE-STS, UE): ");
		String courseType = sc.next();
		String hh, mm;
		//Details of the indices for the course
		ArrayList<Index> indexes= new ArrayList<Index>();
		int n = 0;
		while (n == 0) {
			System.out.println("Enter number of indices: ");
			String string_n = sc.next();
			if (string_n.matches("[0-9]+")) {
				n = Integer.parseInt(string_n);
			}
			else 
				System.out.println("Number of indices can only be an integer. Please re-enter.");
		}
		
		//Looping over the number of indices to read in data
		for (int i = 0; i < n; i++) {
			String indexNum;
			do {
			System.out.println("Index Number: ");
			indexNum = sc.next();
			if (CourseIO.checkCourseIndex(indexNum)) {
				System.out.println("Index already exists.");
				System.out.println("Please re-enter.");
			}
			}while(CourseIO.checkCourseIndex(indexNum));
			
			//Reading in the lecture details
			System.out.println("Lecture:\nStart Time (hh mm): ");
			do {
			hh = sc.next();
			mm = sc.next();
			if (!validateTime(hh,mm)) {
				System.out.println("Error in time. Please re-enter!");
			} }while(!validateTime(hh,mm));
			int h = Integer.parseInt(hh);
			int m = Integer.parseInt(mm);
			LocalTime start = LocalTime.of(h,m,0);
			System.out.println("End Time (hh mm): ");
			do {
				hh = sc.next();
				mm = sc.next();
				if (!validateTime(hh,mm)) {
					System.out.println("Error in time. Please re-enter!");
				} }while(!validateTime(hh,mm));
			h = Integer.parseInt(hh);
			m = Integer.parseInt(mm);
			LocalTime end = LocalTime.of(h,m,0);
			int day = 0, flag = 0;
			while (flag == 0) {
				System.out.println("Day (1 - Mon, 2 - Tue, 3 - Wed, 4 - Thu, 5 - Fri): ");
				String string_d = sc.next();
				if (string_d.matches("[0-9]+")) {
					day = Integer.parseInt(string_d);
					if (0 <= day && day <= 5) {
						flag = 1;
					}
					else
						System.out.println("It can only be an integer from 0 to 5");
				}
				else 
					System.out.println("Day can only be an integer, please re-enter.");
			}
			System.out.println("Location: ");
			sc.nextLine();
			String loc = sc.nextLine();
			Session lecture = new Session(day, start, end, loc);
			Session tutorial = null;
			
			//Reading in the tutorial information
			System.out.println("Is there a tutorial? (y/n) ");
			char ch = sc.next().charAt(0);
			if (ch == 'y' || ch == 'Y') {
				System.out.println("Start Time (hh mm): ");
				do {
					hh = sc.next();
					mm = sc.next();
					if (!validateTime(hh,mm)) {
						System.out.println("Error in time. Please re-enter!");
					} }while(!validateTime(hh,mm));
				h = Integer.parseInt(hh);
				m = Integer.parseInt(mm);
				start = LocalTime.of(h,m,0);
				System.out.println("End Time (hh mm): ");
				do {
					hh = sc.next();
					mm = sc.next();
					if (!validateTime(hh,mm)) {
						System.out.println("Error in time. Please re-enter!");
					} }while(!validateTime(hh,mm));
				h = Integer.parseInt(hh);
				m = Integer.parseInt(mm);
				end = LocalTime.of(h,m,0);
				day = 0;
				flag = 0;
				while (flag == 0) {
					System.out.println("Day (1 - Mon, 2 - Tue, 3 - Wed, 4 - Thu, 5 - Fri): ");
					String string_d = sc.next();
					if (string_d.matches("[0-9]+")) {
						day = Integer.parseInt(string_d);
						if (0 <= day && day <= 5) {
							flag = 1;
						}
						else
							System.out.println("It can only be an integer from 0 to 5");
					}
					else 
						System.out.println("Day can only be an integer, please re-enter.");
				}
				System.out.println("Location: ");
				sc.nextLine();
				loc = sc.nextLine();
				tutorial = new Session(day, start, end, loc);
			}
			
			//Reading in the lab details
			Session lab = null;
			System.out.println("Is there a lab? (y/n) ");
			ch = sc.next().charAt(0);
			if (ch == 'y' || ch == 'Y') {
				System.out.println("Start Time (hh mm): ");
				do {
					hh = sc.next();
					mm = sc.next();
					if (!validateTime(hh,mm)) {
						System.out.println("Error in time. Please re-enter!");
					} }while(!validateTime(hh,mm));
				h = Integer.parseInt(hh);
				m = Integer.parseInt(mm);
				start = LocalTime.of(h,m,0);
				System.out.println("End Time (hh mm): ");
				do {
					hh = sc.next();
					mm = sc.next();
					if (!validateTime(hh,mm)) {
						System.out.println("Error in time. Please re-enter!");
					} }while(!validateTime(hh,mm));
				h = Integer.parseInt(hh);
				m = Integer.parseInt(mm);
				end = LocalTime.of(h,m,0);
				day = 0;
				flag = 0;
				while (flag == 0) {
					System.out.println("Day (1 - Mon, 2 - Tue, 3 - Wed, 4 - Thu, 5 - Fri): ");
					String string_d = sc.next();
					if (string_d.matches("[0-9]+")) {
						day = Integer.parseInt(string_d);
						if (0 <= day && day <= 5) {
							flag = 1;
						}
						else
							System.out.println("It can only be an integer from 0 to 5");
					}
					else 
						System.out.println("Day can only be an integer, please re-enter.");
				}
				System.out.println("Location: ");
				sc.nextLine();
				loc = sc.nextLine();
				lab = new Session(day, start, end, loc);
			}
			
			//Reading in the number of vacancies in the index	
			int vacancy = 0;
			while (vacancy == 0) {
				System.out.println("Enter vacancies: ");
				String string_vac = sc.next();
				if (string_vac.matches("[0-9]+")) {
					vacancy = Integer.parseInt(string_vac);
				}
				else 
					System.out.println("Number of vacancies can only be an integer. Please re-enter.");
			}
			sc.nextLine();
			Index a = new Index(indexNum, code, vacancy, lecture, tutorial, lab);
			indexes.add(a);
		}
		
		//Creating a Course Object to be returned 
		Course A = new Course(code, courseName, school, AUs, indexes, courseType);
		return A;
	}
	
	/**
	 * Method to check whether the course code passed as a parameter already exists in the file
	 * @param code Course code 
	 * @return true if the course code already exists
	 */
	public static boolean checkCourseCode(String code) {
		filepath = "course.dat";
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		if (courseList != null)
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				if (code.equals(C.getCourseCode())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method to check whether the Index passed as a parameter exists in the file
	 * @param courseIndex Index to be checked
	 * @return true if the index exists
	 */
	public static boolean checkCourseIndex(String courseIndex) {
		filepath = "course.dat";
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				ArrayList<Index> indexes = C.getCourseIndex();
				for (int i = 0; i < indexes.size(); i++) {
					if (courseIndex.equals(indexes.get(i).getIndexNo())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Function to check whether the Course Index belongs to the Course
	 * @param code - Course Code
	 * @param courseIndex
	 * @return
	 */
	public static boolean checkCourseIndex(String code, String courseIndex) {
		filepath = "course.dat";
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				if (C.getCourseCode().equals(code)) {
					ArrayList<Index> indexes = C.getCourseIndex();
					for (int i = 0; i < indexes.size(); i++) {
						if (courseIndex.equals(indexes.get(i).getIndexNo())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Method to edit Course data already stored in the file
	 */
	public static void editCourse(Scanner sc) {
		System.out.println("Enter Course Code to be edited: ");
		String code = sc.next();
		
		//to check if the course to be edited exists
		if(!checkCourseCode(code)) {
			System.out.println("Course doesn't exist. Please add Course first.");
			return;
		}
		
		//reading in the new details
		System.out.println("Enter new details: ");
		System.out.println("Course Code: ");
		String code1 = sc.next();
		sc.nextLine();
		Course A = inputCourse(code1,sc);
		filepath = "course.dat";
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		ArrayList<Course> newCourseList = new ArrayList<Course>();
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				if (code.equals(C.getCourseCode())) {
					newCourseList.add(A);
				}
				else
					newCourseList.add(C);
			}
		}
		writeSerializedObject(newCourseList);
		displayCourses();
	}
	
	/**
	 * Method to return a Course object associated with the Course Code passed as a parameter
	 * @param code Course Code
	 * @return A course object if the course code is found in the file, otherwise null
	 */
	public static Course returnCourse(String code) {
		filepath = "course.dat";
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				if (C.getCourseCode().equals(code)) {
					return C;
				}
			}
		}
		return null;
	}
	
	/**
	 * Method to return a list of all the courses stored in the file
	 * @return A list of existing courses
	 */
	public static ArrayList<Course> returnAllCourses() {
		filepath = "course.dat";
		ArrayList<Object> fromFile = (ArrayList<Object>) readSerializedObject();
		ArrayList<Course> courseList = new ArrayList<Course>();
		for (int counter = 0; counter < fromFile.size(); counter++) { 		      
	        Object B = fromFile.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				courseList.add(C);
			}
		}
		return courseList;
	}
	
	/**
	 * Method to return the Index object associated with the passed Index number
	 * @param index Index Number of the Index object to be extracted
	 * @return An Index object if the Index number is found, otherwise null
	 */
	public static Index returnIndex(String index) {
		filepath = "course.dat";
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				ArrayList<Index> indexList = C.getCourseIndex();
				for (int i = 0; i < indexList.size(); i++) {
					Index I = indexList.get(i);
					if (I.getIndexNo().equals(index)) {
						return I;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Method to return all the indexes that are stored in the file
	 * @return A list of existing indexes
	 */
	public static ArrayList<Index> returnAllIndexes() {
		filepath = "course.dat";
		ArrayList<Object> fromFile = (ArrayList<Object>) readSerializedObject();
		ArrayList<Index> indexList = new ArrayList<Index>();
		for (int counter = 0; counter < fromFile.size(); counter++) { 		      
			Object B = fromFile.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				ArrayList<Index> indexes = C.getCourseIndex();
				for (int i = 0; i < indexes.size(); i++) {
					Index I = indexes.get(i);
					indexList.add(I);
				}
			}
		}
		return indexList;
	}
	
	/**
	 * Function to display all the indexes available
	 */
	public static void displayAllIndexes() {
		filepath = "course.dat";
		System.out.println("The available indexes are: ");
		ArrayList<Object> fromFile = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < fromFile.size(); counter++) { 		      
			Object B = fromFile.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				ArrayList<Index> indexes = C.getCourseIndex();
				for (int i = 0; i < indexes.size(); i++) {
					Index I = indexes.get(i);
					System.out.print(I.getIndexNo());
					System.out.print(", ");
				}
			}
		}
		System.out.println();
	}
	
	/**
	 * Function to display the vacancy in the given index
	 * @param indexNo
	 */
	public static void vacancyByIndex(String indexNo) {
		filepath = "course.dat";
		ArrayList<Object> fromFile = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < fromFile.size(); counter++) { 		      
			Object B = fromFile.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				ArrayList<Index> indexes = C.getCourseIndex();
				for (int i = 0; i < indexes.size(); i++) {
					Index I = indexes.get(i);
					if (I.getIndexNo().equals(indexNo)) {
						System.out.print("The vacancy in the given Index is: ");
						System.out.println(I.getVacancy());
						return;
					}
				}
			}
		}
		System.out.println("Index not found.");
	}
	
	/**
	 * Function that updates the binary file with the new values of the Index
	 * @param In
	 */
	public static void updateIndex(Index In) {
		filepath = "course.dat";
		ArrayList<Course> fromFile = (ArrayList<Course>) readSerializedObject();
		for (int counter = 0; counter < fromFile.size(); counter++) { 		      
			Object B = fromFile.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				ArrayList<Index> indexes = C.getCourseIndex();
				for (int i = 0; i < indexes.size(); i++) {
					Index I = indexes.get(i);
					if (I.getIndexNo().equals(In.getIndexNo())) {
						indexes.remove(i);
						indexes.add(i, In);
						C.setCourseIndex(indexes);
						fromFile.remove(counter);
						fromFile.add(counter,C);
					}
				}
			}
		}
		writeSerializedObject(fromFile);
	}
	
	/**
	 * Method to add a Course to the binary file
	 */
	public static void addCourse(Scanner sc) {
		System.out.println("Course Code: ");
		String code = sc.next();
		sc.nextLine();
		//Checking if the course is already present in the file
		if (checkCourseCode(code)) {
			System.out.println("Course already exists. Please edit Course first.");
			return;
		}
		
		//Creating a Course object and writing to file
		Course A = inputCourse(code,sc);
		filepath = "course.dat";
		Courses = (ArrayList<Course>) readSerializedObject();
		if (Courses == null)
			Courses = new ArrayList<Course>();
		Courses.add(A);
		writeSerializedObject(Courses);
		System.out.println("Course successfully added.");
		displayCourses();
	}
	
	/**
	 * Method to display all the courses stored in the file
	 */
	@SuppressWarnings("unchecked")
	public static void displayCourses() {
		filepath = "course.dat";
		System.out.println("The courses stored currently are: ");
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		System.out.print("Number of courses read: ");
		System.out.println(courseList.size());
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				C.printCourse();
				System.out.println();
			}
		}
	}
	
	/**
	 * Function that adds a student to the Waitlist
	 * @param S - Student to be added
	 * @param I1 - Index to which the student is to be added
	 */
	public static void addToWaitlist(Student S, Index I1) {
		filepath = "course.dat";
		ArrayList<Object> fromFile = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < fromFile.size(); counter++) { 		      
			Object B = fromFile.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				ArrayList<Index> indexes = C.getCourseIndex();
				for (int i = 0; i < indexes.size(); i++) {
					Index I = indexes.get(i);
					if (I.getIndexNo().equals(I1.getIndexNo())) {
						I.appendNameWL(S.getMatriculation_Card());
						indexes.remove(i);
						indexes.add(I);
						C.setCourseIndex(indexes);
						editCourse(C);
						return;
					}
				}
			}
		}
	}
	
	/**
	 * Function to add a student to the Registered list
	 * @param S - Student to be added
	 * @param I1 - Index to which it needs to added
	 */
	public static void addToRegisteredList(Student S, Index I1) {
		filepath = "course.dat";
		ArrayList<Object> fromFile = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < fromFile.size(); counter++) { 		      
			Object B = fromFile.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				ArrayList<Index> indexes = C.getCourseIndex();
				for (int i = 0; i < indexes.size(); i++) {
					Index I = indexes.get(i);
					if (I.getIndexNo().equals(I1.getIndexNo())) {
						ArrayList<String> registered = I.getRegiStudName();
						if (registered != null)
							registered.add(S.getMatriculation_Card());
						else {
							registered = new ArrayList<String>();
							registered.add(S.getMatriculation_Card());
						}
						I.setRegiStudName(registered);
						indexes.remove(i);
						indexes.add(I);
						C.setCourseIndex(indexes);
						editCourse(C);
						return;
					}
				}
			}
		}
	}
	
	public static void removeFromRegisteredList(Student S, Index I1) {
		filepath = "course.dat";
		ArrayList<Object> fromFile = (ArrayList<Object>) readSerializedObject();
		for (int counter = 0; counter < fromFile.size(); counter++) { 		      
			Object B = fromFile.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				ArrayList<Index> indexes = C.getCourseIndex();
				for (int i = 0; i < indexes.size(); i++) {
					Index I = indexes.get(i);
					if (I.getIndexNo().equals(I1.getIndexNo())) {
						ArrayList<String> registered = I.getRegiStudName();
						if (registered != null && registered.contains(S.getMatriculation_Card()));
							registered.remove(S.getMatriculation_Card());
						I.setRegiStudName(registered);
						indexes.remove(i);
						indexes.add(I);
						C.setCourseIndex(indexes);
						editCourse(C);
						return;
					}
				}
			}
		}
	}
	
	/**
	 * Function to edit a Course into the binary file
	 * @param A - Course to be edited
	 */
	public static void editCourse(Course A) {
		filepath = "course.dat";
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		ArrayList<Course> newCourseList = new ArrayList<Course>();
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				if (A.getCourseCode().equals(C.getCourseCode())) {
					newCourseList.add(A);
				}
				else
					newCourseList.add(C);
			}
		}
		writeSerializedObject(newCourseList);
	}
	
	/**
	 * Function to display all the courses available
	 */
	public static void displayAllCourses() {
		System.out.println("The available options are: ");
		filepath = "course.dat";
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		System.out.print("Number of courses read: ");
		System.out.println(courseList.size());
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				System.out.println(C.getCourseCode()+ " " + C.getCourseName());
			}
		}
	}
	
	/**
	 * Function that reads a Course and then Index
	 * @param sc - Scanner
	 * @return
	 */
	public static ArrayList<String> courseThenIndex(Scanner sc) {
		filepath = "course.dat";
		ArrayList<Object> courseList = (ArrayList<Object>) readSerializedObject();
		displayAllCourses();
		System.out.println("\nEnter Course code: ");
		String CourseCode= sc.next();
		ArrayList<String> result = new ArrayList<String>();
		result.add(CourseCode);
		for (int counter = 0; counter < courseList.size(); counter++) { 		      
	        Object B = courseList.get(counter);
			if (B instanceof Course)
			{
				Course C = (Course) B;
				if (C.getCourseCode().equals(CourseCode)) {
					System.out.println("The available index options are: ");
					ArrayList<Index> indexes = C.getCourseIndex();
					for (int i = 0; i < indexes.size(); i++) {
						System.out.print(indexes.get(i).getIndexNo() + ", ");
					}
					break;
				}
			}
		}
		System.out.println("\nEnter Course Index: ");
		String CourseIndex= sc.next();	
		sc.nextLine();
		result.add(CourseIndex);
		return result;
	}
	
}