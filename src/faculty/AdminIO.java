package faculty;

import java.util.Scanner;
import java.util.ArrayList;
import displayIO.*;

/**
 * Class to store and retrieve Admin data from a binary file admin.dat
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17
 */
public class AdminIO extends IO {
	
	/**
	 * Method that reads in Admin data from the user 
	 * @param username Username for the new Admin record
	 * @return An Admin object with new data read from the user
	 */
	private static Admin inputAdmin(String username) {
		Scanner sc = new Scanner(System.in);
		
		//Reading in the admin information
		System.out.println("Admin Name: ");
		String name = sc.nextLine();
		System.out.println("Password: ");
		String password = sc.next();
		String pass = encrypt(password, 5);
		
		//Creating a Student Object to be returned 
		Admin A = new Admin(name, username, pass);
		sc.close();
		return A;
	}
	
	/**
	 * Method to return an Admin object with the provided username and password
	 * @param username
	 * @param password
	 * @return An Admin object if the username is valid, otherwise null
	 */
	public static Admin check(String username, String password) {
		filepath = "admin.dat";
		ArrayList<Object> adminList = ReadObjectFromFile();
		for (int counter = 0; counter < adminList.size(); counter++) { 		      
	        Object B = adminList.get(counter);
			if (B instanceof Admin)
			{
				Admin C = (Admin) B;
				String s = C.getPassword();
				if (username.equals(C.username) && password.equals(encrypt(s,-5))) {
					return C;
				}
			}
		}
		return null;
	}
	
	/**
	 * Method to check if the provided username already exists in the file
	 * @param username
	 * @return true if the username exists in the file
	 */
	public static boolean checkUsername(String username) {
		filepath = "admin.dat";
		ArrayList<Object> adminList = ReadObjectFromFile();
		for (int counter = 0; counter < adminList.size(); counter++) { 		      
	        Object B = adminList.get(counter);
			if (B instanceof Admin)
			{
				Admin C = (Admin) B;
				if (username.equals(C.username)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method to write an Admin object into the binary file
	 */
	public static void addAdmin() {
		filepath = "admin.dat";
		Scanner sc = new Scanner(System.in);
		System.out.println("Username: ");
		String username = sc.next();
		
		//Checking if the Admin is already present in the file
		if (checkUsername(username)) {
			System.out.println("Admin already exists.");
			sc.close();
			return;
		}
		
		//Creating a Admin object and writing to file
		Admin A = inputAdmin(username);
		WriteObjectToFile(A);
		sc.close();
	}
	
	public static void printAllAdmins() {
		filepath = "admin.dat";
		ArrayList<Object> adminList = ReadObjectFromFile();
		for (int counter = 0; counter < adminList.size(); counter++) { 		      
	        Object B = adminList.get(counter);
			if (B instanceof Admin)
			{
				Admin C = (Admin) B;
				System.out.println(C.getName());
			}
		}
	}
	
	public static void main(String args[]) {
		addAdmin();
	}
}