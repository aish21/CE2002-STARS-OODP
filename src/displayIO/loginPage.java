package displayIO;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;

import courses.CourseIO;
import faculty.accessTime;
import faculty.*;
import student.*;

/**
 * File implements the login section of STARS 
 * Boundary Class
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17
 */

public class loginPage extends JFrame implements ActionListener {
	/**
	 * Initialise and create labels, text fields, panel and button
	 */
	private static final long serialVersionUID = 1L;
	static JPanel panel= new JPanel(new GridLayout(3, 1));
	JLabel username_label, password_label, message;
	JTextField usernameText;
	JPasswordField passwordText;
	JButton submit, cancel;
	private static Scanner sc = new Scanner(System.in);
	
	public static Scanner returnScanner() {
		return sc;
	}
	
	public loginPage() {
		/**
		 * Labels and Button defined here
		 */
		// Username Label
		username_label = new JLabel();
		username_label.setText("Username :");
		usernameText = new JTextField();
		
		// Password Label
		password_label = new JLabel();
		password_label.setText("Password :");
		passwordText = new JPasswordField();
		
		// Submit Button
		submit = new JButton("SUBMIT");
		//panel = new JPanel(new GridLayout(3, 1));
		panel.add(username_label);
		panel.add(usernameText);
		panel.add(password_label);
		panel.add(passwordText);
		message = new JLabel();
		panel.add(message);
		panel.add(submit);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Listeners 
		submit.addActionListener(this);
		add(panel, BorderLayout.CENTER);
		setTitle("STARS Login");
		setSize(500,150);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		/**
		 * Event Handler
		 * Controls the flow of events as button is clicked
		 * and options are picked
		 */
		int selection;
		boolean exit = false;
		//Scanner sc = new Scanner(System.in);
		String userName = usernameText.getText();
		@SuppressWarnings("deprecation")
		String password = passwordText.getText();
		Student S = StudentIO.check(userName, password);
		Admin A = AdminIO.check(userName, password);
		
		if (S != null){
			panel.setVisible(false);
			int flag = 0;
			if (S.getAccessTime() != null)
				flag = accessTime.checkAccessTime(S);
			else {
				System.out.println("You have not been alloted an access time yet.");
				System.out.println("Please try again later.");
				System.exit(0);
			}
			if(flag == 1) {
				System.out.println("Welcome " + S.getStudentName() + " to STARS!");
				System.out.print("You are currently registered to ");
				System.out.print(S.getAUs_registered());
				System.out.println(" AUs.");
				loginPage.panel.setVisible(false);
				do {
					System.out.println("\nWhat would you like to do today?\n" + 
               				"1.  Add course\n" + 
               				"2.  Drop course\n" + 
               				"3.  Print Courses Registered\n" + 
               				"4.  Check Vacancies Available\n" + 
               				"5.  Check Courses Available\n" +
               				"6.  Change Index Number of Course\n" + 
               				"7.  Swap Index Number with Another Student\n" + 
               				"8.  Quit\n");
					System.out.println("Please state your option here: ");
					selection = sc.nextInt();
					sc.nextLine();
					switch(selection) {
					case 1:
						System.out.println("You have chosen: Add course, as a student.");
						S.Add_Course(sc);
						System.out.println("Press enter to continue..");
						sc.nextLine();
						break;
					case 2:
						System.out.println("You have chosen: Drop course, as a student.");
						S.Drop_Course(sc);
						System.out.println("Press enter to continue..");
						sc.nextLine();
						break;
					case 3:
						System.out.println("You have chosen: Print courses registered, as a student.");
						S.print_courses();
						System.out.println("Press enter to continue..");
						sc.nextLine();
						break;
					case 4:
						System.out.println("You have chosen: Check vacancies available, as a student.");
						System.out.println("The indexes available are: ");
						CourseIO.displayAllIndexes();
						S.vacancy_available(sc);
						System.out.println("Press enter to continue..");
						sc.nextLine();
						break;
					case 5:
						System.out.println("You have chosen: Check courses available, as a student.");
						CourseIO.displayCourses();
						System.out.println("Press enter to continue..");
						sc.nextLine();
						break;
					case 6:
						System.out.println("You have chosen: Change Index Number of course, as a student.");
						S.change_index_number(sc);
						System.out.println("Press enter to continue..");
						sc.nextLine();
						break;
					case 7:
						System.out.println("You have chosen: Swap Index with another student, as a student.");
						System.out.println("Enter Peer's Matriculation Number: ");
						String matricNo = sc.next();
						Student S1 = StudentIO.getStudent(matricNo);
						if(S1 != null)
							S.swop_index(S1,sc);
						else
							System.out.println("Peer not found.");
						System.out.println("Press enter to continue..");
						sc.nextLine();
						break;
					case 8:
						System.out.println("Thank you for using STARS! Have a nice day.");
						exit = true;
						System.exit(0);
						break;
					default:
	                	  System.out.println("Incorrect option entered.");
	                	  System.out.println("Press enter to continue..");
	                	  sc.nextLine();
                	 }
				}while(exit == false);
			}
			else if(flag==0) {
				System.out.println("You have missed your date!");
				System.exit(0);
			}
			else if(flag==-1){
				System.out.println("Access Restricted!");
				System.exit(0);
			}
         } 
		else if (A != null){
			panel.setVisible(false);
        	System.out.println("Welcome " + A.getName() + "!"); 
        	System.out.println("Registering students on the Waitlist...");
        	System.out.println("Please wait....");
        	A.registerCourse();
        	System.out.println("Pending Registrations Complete!");
             do {
            	 System.out.println("\nWhat would you like to do today?\n" + 
           				"1.  Edit student access period\n" + 
           				"2.  Add a student\n" + 
           				"3.  Update a student\n" +
           				"4.  Display all students\n" + 
           				"5.  Add Course\n"+
           				"6.  Display all Courses\n" + 
           				"7.  Update a course\n" + 
           				"8.  Check available vacancies for an index number\n" + 
           				"9.  Print student list by index number.\n" + 
           				"10. Print student list by course (all students registered for the selected course)\n" + 
           				"11. Quit\n");
                  System.out.println("Please state your option here: ");
                  selection = sc.nextInt();
                  sc.nextLine();
                  switch(selection) {
                  case 1:
                	  System.out.println("You have chosen: Edit student access period, as staff.");
                	  System.out.println("Matriculation Number: ");
                	  String matricNo = sc.next();
                	  Student s1 = StudentIO.getStudent(matricNo);
                	  if (s1 != null)
                		  A.setAccessTime_student(s1,sc);
                	  else
                		  System.out.println("Student not found. Please add student first.");
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 2:
                	  System.out.println("You have chosen: Add a student, as staff.");
                	  A.add_student(sc);
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 3:
                	  System.out.println("You have chosen: Add a student, as staff.");
                	  A.edit_student(sc);
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 4:
                	  System.out.println("You have chosen: Display all students, as staff.");
                	  StudentIO.displayStudents();
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 5:
                	  System.out.println("You have chosen: Add a course, as staff.");
                	  A.add_course(sc);
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 6:
                	  System.out.println("You have chosen: Display all courses, as staff.");
                	  CourseIO.displayCourses();
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 7:
                	  System.out.println("You have chosen: Update a course, as staff.");
                	  A.update_course(sc);
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 8:
                	  System.out.println("You have chosen: Check available vacancies for an index number, as staff.");
                	  A.printVacancyByIndex(sc);
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 9:
                	  System.out.println("You have chosen: Print Student list by index number, as staff.");
                	  A.studentListSortindex(sc);
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 10:
                	  System.out.println("You have chosen: Print Student list by course (all students registred for the selected course), as staff.");
                	  A.studentListSortcourse(sc);
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
                	  break;
                  case 11:
                	  System.out.println("Thank you for using STARS! Have a nice day.");
                	  exit = true;
                	  System.exit(0);
                	  break;
                  default:
                	  System.out.println("Incorrect option entered.");
                	  System.out.println("Press enter to continue..");
                	  sc.nextLine();
            	 }
             }while(exit == false);
         }
		else {
        	 message.setText(" Invalid user...");
         }
	}
}