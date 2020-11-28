package student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import courses.*;
import student.Student;

/**
 * Class to edit, retrieve and update course details of students
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17
 */

public class Student implements Serializable{
	/**
	 * To write to the binary file
	 */
	private static final long serialVersionUID = 1L;
	/**
 	 * Name of the student
 	 */
	private String StudentName;
	/**
 	 * Matriculation Number of the student
 	 */
	private String Matriculation_Card;
	/**
 	 * Total AUs Registered for the student
 	 */
	private int AUs_registered;
	/**
 	 * Gender of the student
 	 */
	private String gender;
	/**
 	 * Address of the student
 	 */
	private String address;
	/**
 	 * Nationality of the student
 	 */
	private String nationality;
	/**
 	 * Added or registered courses of the student
 	 */
	private ArrayList<Course> course = new ArrayList<Course>();
	/**
 	 * Added or registered indexes of the student
 	 */
	private ArrayList<Index>  index = new ArrayList<Index>();
	/**
 	 * Total number of courses added by the student
 	 */
	private int no_of_courses;
	/**
 	 * personalized access time of the student
 	 */
	private String accessTime;
	/**
 	 * Username of the student
 	 */
	private String Username;
	/**
 	 * Password of the student
 	 */
	private String StudentPassword;
	 
	 /**
     * Constructor to add student details.
	 * Initialize StudentName, Username, StudentPassword, Matriculation_Card, gender, address and nationality with the parameters passed.
	 * Initialize AUs_registered = 0, no_of_courses = 0 and accessTime = null.
     * @param StudentName 
     * @param Username  
     * @param StudentPassword 
     * @param Matriculation_Card 
     * @param gender 
     * @param address
     * @param nationality
     */
	
	Student (String name,String Username, String StudentPassword,String Matriculation_Card,String gender,String address,String nationality)
	{	this.StudentName= name;
		this.Username= Username;
		this.StudentPassword= StudentPassword;
	    this.Matriculation_Card=Matriculation_Card;
	    this.AUs_registered=0;
	    this.gender=gender;
	    this.address= address;
	    this.nationality=nationality;
	    no_of_courses= 0;
	    this.accessTime= null;    
	}
		
	/**
	 * Method to add courses to the waitlist list 
	 * Calls returnCourse() of courseIO to retieve the course object by passing the course code entered by the student as a parameter 
	 * Calls returnIndex() of courseIO to retieve the index object by passing the course code entered by the student as a parameter
	 * Appends student's Matriculation number to the course's waitlist
	 * Calls checkIndexClash() of Index to ensure no clashing of lectures, tutorials and labs by passing the index to add  
	 * Calls editStudent() of StudentIO to store the updated student details in the student file by passing the current student object 
	 */
	public void Add_Course(Scanner sc)
	{			
		String CourseCode, CourseIndex;
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<Index> indexes = new ArrayList<Index>();
		ArrayList<String> result = CourseIO.courseThenIndex(sc);
		CourseCode = result.get(0);
		CourseIndex = result.get(1);
		if (CourseIO.checkCourseIndex(CourseCode, CourseIndex)) {
			Index I = CourseIO.returnIndex(CourseIndex);
			Course C1 = CourseIO.returnCourse(CourseCode);
			ArrayList<Index> indexes1 = C1.getCourseIndex();
			for (int i = 0; i < indexes1.size(); i++) {
				Index currentIndex = indexes1.get(i);
				ArrayList<String> waitlist = currentIndex.getWaitlistStudName();
				if(waitlist != null && waitlist.contains(Matriculation_Card))
				{	System.out.println("Course is already added to waitlist!");
					return;
				}
				ArrayList<String> regis = currentIndex.getRegiStudName();
				if(regis != null && regis.contains(Matriculation_Card))
				{	System.out.println("Course is already registered!");
					return;
				}
			}
			if (I == null) {
				System.out.println("Index not found!");
				return;
			}
			boolean flag = false;
			if (index != null) {
				for (int a = 0; a < index.size(); a++) {
					if (I.checkIndexClash(index.get(a))) {
						flag = true;
					}
				}
			}
			ArrayList<String> waitlist = I.getWaitlistStudName();
			if(waitlist != null && waitlist.contains(Matriculation_Card))
			{	System.out.println("Index Number: "+ CourseIndex+ " is already added to waitlist!");
				return;
			}
			ArrayList<String> regis = I.getRegiStudName();
			if(regis != null && regis.contains(Matriculation_Card))
			{	System.out.println("Index Number: "+ CourseIndex+ " is already registered!");
				return;
			}
			if (flag) {
				System.out.println("This Index clashes with your Registered Courses!");
				return;
			}
			Course C = CourseIO.returnCourse(CourseCode);
			courses.add(C);
			indexes.add(I);
			CourseIO.addToWaitlist(this, I);
			System.out.println("You have been added to the Waitlist for " + I.getIndexNo() + "!");
		}
		else
			System.out.println("Incorrect Course code or Index Number");
		if (course!= null) {
			index.addAll(indexes);
			course.addAll(courses);
		}
		else {
			course = courses;
			index = indexes;
		}
		no_of_courses= no_of_courses + 1;
		StudentIO.editStudent(this);
	}
		
		
		
		/**
		 * Method to drop courses 
		 * Removes the course from student's course and index array
		 * Removes the student's matriculation number from the course's waitlist or registered list
		 * Updates the vacancy by 1 if the dropped course was registered
		 * Calls editStudent() of StudentIO to store the updated student details in the student file by passing the current student object 
		 */
		public void Drop_Course(Scanner sc)
		{ 	
			String CourseCode; 
			System.out.println("Enter Course code to drop: ");
			CourseCode= sc.next();
			sc.nextLine();
			for(int i=0;i<no_of_courses;i++)
			{ 	
				Course C = course.get(i);
				if(C.getCourseCode().equals(CourseCode))
				{	
					course.remove(i);
					for (int t = 0; t < no_of_courses; t++) {
						if (index.get(t).getcoursecode().equals(CourseCode)) {
							index.remove(t);
							break;
						}
					}
					ArrayList<Index> indexes = CourseIO.returnAllIndexes();
					for (int j = 0; j < indexes.size(); j++) {
						Index I = indexes.get(j);
						if (CourseIO.checkCourseIndex(CourseCode, I.getIndexNo())) {
							ArrayList<String> registered = I.getRegiStudName();
							ArrayList<String> waitlist = I.getWaitlistStudName();
							if (registered != null && registered.contains(Matriculation_Card))
							{	
								AUs_registered = AUs_registered - C.getCourseAU();
								registered.remove(Matriculation_Card);
						 		I.setRegiStudName(registered);
						 		I.setVacancy(I.getVacancy()+1);
						 		CourseIO.updateIndex(I);
							}
							else if (waitlist != null && waitlist.contains(Matriculation_Card))
							{	
								waitlist.remove(Matriculation_Card);
						 		I.setWaitlistStudName(waitlist);
						 		CourseIO.updateIndex(I);
							} 
						}
					}
					no_of_courses=no_of_courses-1;
					StudentIO.editStudent(this);
					return;
				}
			}
			System.out.println(CourseCode+" is not an added course! ");
		}
		
		/**
		 * Method to print the details of all courses added by the student
		 */
		public void print_courses()
		{ 	
			if (no_of_courses!=0)
			{		for(int i=0;i<no_of_courses;i++)
					{	
					index.get(i).printIndex();
					ArrayList<Index> indexes = CourseIO.returnAllIndexes();
					String indexNo;
					for (int j = 0; j < indexes.size(); j++) {
						indexNo = indexes.get(j).getIndexNo();
						if (indexNo.equals(index.get(i).getIndexNo())) {
								ArrayList<String> registered = indexes.get(j).getRegiStudName();
								if (registered != null) {
									if (registered.contains(Matriculation_Card))
										System.out.println("Status: Registered\n");
									else {
										System.out.println("Status: Waitlist\n");
									}
								}
								else
									System.out.println("Status: Waitlist\n");
							}
						}
					}
	
				System.out.println("Number of AUs Registered: "+AUs_registered);
			}

			else
				System.out.println("No courses added! ");
				
		}
		
		/**
		 * Method to display the vacancies available for an index 
		 */
		public void vacancy_available(Scanner sc)
		{	
			String Indexno;
			Index i;
			System.out.println("Enter Index Number of the course: ");
			Indexno= sc.nextLine();
			i= CourseIO.returnIndex(Indexno);
			if(i.equals(null))
				System.out.println("Index Number "+Indexno+" is invalid! ");
			else
				i.printIndex();
		}
		
		/**
		 * Method to change the index number of an added course to another index number of the same course
		 * Changes the index of a registered course only if there is vacancy in the new index
		 * Updates the vacancies of the current and new index if they are of a registered course
		 * Calls editStudent() of StudentIO to store the updated student details in the student file by passing the current student object 
		 */
		public void change_index_number(Scanner sc)
		{	
			String CourseIndex1,CourseIndex2;
			Index I1,I2;
			System.out.println("Enter Current Index Number: ");
			CourseIndex1= sc.next();
			I1= CourseIO.returnIndex(CourseIndex1);
			System.out.println("Enter New Index Number: ");
			CourseIndex2= sc.next();
			sc.nextLine();
			I2= CourseIO.returnIndex(CourseIndex2);
			if(I1.equals(null)||I2.equals(null))
				System.out.println("Index Number is invalid! ");
			else
			{
			if(I1.getcoursecode().equals(I2.getcoursecode())) {
				for(int i=0;i<no_of_courses;i++)
				{	
					if(index.get(i).getIndexNo().equals(CourseIndex1))
					{		
						ArrayList<String> waitlist = I1.getWaitlistStudName();
							if (waitlist != null && waitlist.contains(Matriculation_Card))
							{	
								waitlist.remove(Matriculation_Card);
								I1.setWaitlistStudName(waitlist);
								index.remove(i);
								index.add(i, I2);
								CourseIO.addToWaitlist(this, I2);
								CourseIO.updateIndex(I1);
								System.out.println("Index Number "+CourseIndex1+" has been changed to "+CourseIndex2);
								StudentIO.editStudent(this);
								break;
							}
							else {
								ArrayList<String> registered = I1.getRegiStudName();
								if (registered != null && registered.contains(Matriculation_Card))
								{	
									if(I2.getVacancy()!=0)
									{	
										registered.remove(Matriculation_Card);
										I1.setRegiStudName(registered);
										index.remove(i);
										index.add(i,I2);
										I1.setVacancy(I1.getVacancy() + 1);
										I2.setVacancy(I2.getVacancy()-1);
										CourseIO.updateIndex(I1);
										CourseIO.addToRegisteredList(this, I2);
										System.out.println("Index Number "+CourseIndex1+" has been changed to Index Number "+CourseIndex2);
										StudentIO.editStudent(this);
										break;
									}
									else
									{	
										System.out.println("Index Number "+CourseIndex2+" have no vacancies ");
										System.out.println("Index Number "+CourseIndex1+" cannot be changed to Index Number "+CourseIndex2);
										break;
									}
								}
							}
						}
					
				}	
			}
			else 
				System.out.println("Index Number "+CourseIndex1+" cannot be changed to Index Number "+CourseIndex2);
		}
		}
		/**
		 * Method to swap course index with another index with another student of the same course
		 * Swaps index only if both students are registered to the same course that holds the index
		 * Calls editStudent() of StudentIO to store the updated student details of both students in the student file by passing the current student object 
		 * @param s Student object with the another index to be swapped with
		 */
		public void swop_index(Student s, Scanner sc)
		{	
			int flag1=0,flag2=0, flag3=0, cflag1=0,cflag2=0;
			System.out.println("Enter your Index Number: ");
			String CourseIndex1= sc.next();	
			System.out.println("Enter your Peer's Index Number: ");
			String CourseIndex2= sc.next();	
			sc.nextLine();
			ArrayList<Index> indexes = CourseIO.returnAllIndexes();
			for (int y = 0; y < index.size(); y++) {
				String indexFromStudent = index.get(y).getIndexNo();
				for (int z = 0; z < indexes.size(); z++) {
					String indexFromFile = indexes.get(z).getIndexNo();
					if (indexFromStudent.equals(indexFromFile)) {
						index.remove(y);
						index.add(y,indexes.get(z));
					}
				}
			}
			for (int y = 0; y < s.getNo_of_courses(); y++) {
				String indexFromStudent = s.index.get(y).getIndexNo();
				for (int z = 0; z < indexes.size(); z++) {
					String indexFromFile = indexes.get(z).getIndexNo();
					if (indexFromStudent.equals(indexFromFile)) {
						s.index.remove(y);
						s.index.add(y,indexes.get(z));
					}
				}
			}
			for( int i=0;i<no_of_courses;i++)
				if (index.get(i).getIndexNo().equals(CourseIndex1))
				{	
					cflag1=1;
					if(index.get(i).getRegiStudName().contains(Matriculation_Card))
					{	
						flag1=1;
						for( int j=0;j<s.no_of_courses;j++)
							if (s.index.get(j).getIndexNo().equals(CourseIndex2))
							{	
								cflag2=1;
								if(s.index.get(j).getRegiStudName().contains(s.Matriculation_Card))
								{	
									flag2=1;
									if(index.get(i).getcoursecode().equals(s.index.get(j).getcoursecode()))
									{	
										flag3=1;
										s.index.remove(j);
										s.index.add(j,index.get(i));
										index.remove(i);
										index.add(i,CourseIO.returnIndex(CourseIndex2));
										System.out.println(Matriculation_Card+"-Index Number "+CourseIndex1+" has been successfully swapped with "+s.getMatriculation_Card()+"-Index Number "+CourseIndex2);
										StudentIO.editStudent(this);
										StudentIO.editStudent(s);
										CourseIO.updateIndex(index.get(i));
										CourseIO.updateIndex(s.index.get(j));
										CourseIO.addToRegisteredList(this, index.get(i));
										CourseIO.removeFromRegisteredList(this, s.index.get(j));
										CourseIO.removeFromRegisteredList(s, index.get(i));
										CourseIO.addToRegisteredList(s, s.index.get(j));
										return;
									}	
								}
							}
					}
				}
			if(no_of_courses==0)
				System.out.println("No courses added/registered!");
			else
				if(cflag1==0)
					System.out.println(CourseIndex1+" is not an added index");
				else
					if(flag1==0)
						System.out.println(Matriculation_Card+"-Index Number "+CourseIndex1+ " is not registered");
					else
						if(cflag2==0)
							System.out.println(CourseIndex2+" is not an added index");
						else
							if(flag2==0)
								System.out.println(s.Matriculation_Card+"-Index Number "+CourseIndex2+ " is not registered");
							else
								if(flag3==0)
									System.out.println("Course of Index Number: "+CourseIndex1+" and "+CourseIndex2+ " is not same");
		}
		
		/**
	     * Accessor to get the Student Name
	     * @return Student Name
	     */
		public String getStudentName() 
		{
			return StudentName;		
		}
		
		/**
	     * Mutator to set the student name
	     * @param StudentName
	     */
		public void setStudentName(String StudentName) 
		{
			this.StudentName = StudentName;
		}
		
		/**
	     * Accessor to get the Matriculation Number of the student
	     * @return Matriculation Number of the student
	     */
		public String getMatriculation_Card() 
		{
			return Matriculation_Card;		
		}
		
		/**
	     * Mutator to set the Matriculation Number of the student
	     * @param Matriculation_Card
	     */
		public void setMatriculation_Card(String Matriculation_Card) 
		{
			this.Matriculation_Card = Matriculation_Card;
		}
		
		/**
	     * Accessor to get the AUs Registered for the student
	     * @return AUs Registered for the student
	     */
		public int getAUs_registered() 
		{
			return AUs_registered;		
		}
		
		/**
	     * Mutator to set the AUs Registered for the student
	     * @param AUs_registered
	     */
		public void setAUs_registered(int AUs_registered) 
		{
			this.AUs_registered = AUs_registered;
		}
		
		/**
	     * Accessor to get the Gender of the student
	     * @return Gender of the student
	     */
		public String getGender() 
		{
			return gender;		
		}
		
		/**
	     * Mutator to set the Gender of the student
	     * @param gender
	     */
		public void setGender(String gender) 
		{
			this.gender = gender;
		}
		
		/**
	     * Accessor to get the Address of the student
	     * @return Address of the student
	     */
		public String getAddress() 
		{
			return address;		
		}
		
		/**
	     * Mutator to set the Address of the student
	     * @param address
	     */
		public void setAddress(String address) 
		{
			this.address = address;
		}
		
		/**
	     * Accessor to get the Nationality of the student
	     * @return Nationality of the student
	     */
		public String getNationality() 
		{
			return nationality;		
		}
		
		/**
	     * Mutator to set the Nationality of the student
	     * @param nationality
	     */
		public void setNationality(String nationality) 
		{
			this.nationality = nationality;
		}
		
		/**
	     * Accessor to get the Total number of courses added by the student
	     * @return Total number of courses added by the student
	     */
		public int getNo_of_courses() 
		{
			return no_of_courses;		
		}
		
		/**
	     * Mutator to set the Total number of courses added by the student
	     * @param no_of_courses
	     */
		public void setNo_of_courses(int no_of_courses) 
		{
			this.no_of_courses = no_of_courses;
		}
		
		/**
	     * Accessor to get the personalized access time of the student
	     * @return personalized access time of the student
	     */
		public String getAccessTime() 
		{
			return accessTime;		
		}
		
		/**
	     * Mutator to set the personalized access time of the student
	     * @param accessTime
	     */
		public void setAccessTime(String accessTime) 
		{
			this.accessTime = accessTime;
		}
		
		/**
	     * Accessor to get the Password of the student
	     * @return Password of the student
	     */
		public String getStudentPassword() 
		{
			return StudentPassword;		
		}
		
		/**
	     * Mutator to set the Password of the student
	     * @param StudentPassword
	     */
		public void setStudentPassword(String StudentPassword) 
		{
			this.StudentPassword = StudentPassword;
		}
		
		/**
	     * Accessor to get the Username of the student
	     * @return Username of the student
	     */
		public String getUsername() 
		{
			return Username;		
		}
		
		/**
	     * Mutator to set the Username of the student
	     * @param Username
	     */
		public void setSUsername(String Username) 
		{
			this.Username = Username;
		}
		
		public void printStudent() {
			System.out.println("Student Name: " + StudentName);
			System.out.println("Matriculation Number: " + Matriculation_Card);
			System.out.println();
		}
	}



