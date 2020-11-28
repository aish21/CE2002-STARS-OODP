package faculty;

import java.util.Scanner;
import java.io.Serializable;
import java.util.ArrayList;
import student.*;
import courses.*;
import emailClient.email;
/**
 * Admin class for performing all function pertaining to course registration
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17
 */
public class Admin implements Serializable {
	/**
	 * To be able to write to a binary file
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	public String username;
	private String password; 
	/**
	 * Details of admin
	 * @param name
	 * @param username
	 * @param password
	 * 
	 * constructor Admin() initialises all these values
	 */
	
	Admin(String name, String username, String password)
	{	this.setName(name);
		this.username = username;
		this.password = password;
	}
	/**
	 * 
	 * @param s1 Student object used to set access time
	 * set access time belongs to accessTime class
	 */
	public void setAccessTime_student(Student s1, Scanner sc)
	{
		s1.setAccessTime(accessTime.setAccessTime(sc));
		StudentIO.editStudent(s1);
	}
	/**
	 *  the following functions use the studentIO to add student 
	 *  and courseIO to add and edit courses
	 */
	public void add_student(Scanner sc)
	{
		StudentIO.addStudent(sc);
	}
	public void add_course(Scanner sc)
	{
		CourseIO.addCourse(sc);
	}
	public void edit_student(Scanner sc) {
		StudentIO.editStudent(sc);
	}
	public void update_course(Scanner sc)
	{
		CourseIO.editCourse(sc);
	}
	public void printVacancyByIndex(Scanner sc) {
		CourseIO.displayAllIndexes();
		System.out.println("Enter index: ");
		String index = sc.next();
		sc.nextLine();
		CourseIO.vacancyByIndex(index);
	}
	/**
	 * loop control variables
	 * i - to iterate in registered list
	 * j - to iterate in full student list to find student
	 * wlLoop - to iterate and update wait list after registration
	 * m - to traverse full course list
	 * n - to traverse full index list
	 * updatedAU 
	 * vac 
	 * 
	 */
	public void registerCourse()
	{
		int i,j,updatedAU,vac,m,n,t; 
		ArrayList<Course> fullCourselist = CourseIO.returnAllCourses();
		for(m=0;m<=fullCourselist.size()-1;m++)	
		{	
			ArrayList<Index> fullIndexlist = fullCourselist.get(m).getCourseIndex();
			for(n=0;n<=fullIndexlist.size()-1;n++)
			{
				vac = fullIndexlist.get(n).getVacancy();
				ArrayList<Student> fullStudentslist = StudentIO.returnAllStudents();
				if(vac!=0)
				 {
					ArrayList<String> waitlist = fullIndexlist.get(n).getWaitlistStudName(), registeredList=fullIndexlist.get(n).getRegiStudName();
					 if (waitlist == null) {
						 continue;
					 }
					 if (registeredList == null)
						 registeredList = new ArrayList<String>();
					 i= vac;
					 t=0;
					for(;i>0 && t < waitlist.size();i--,t++)
					 {
						registeredList.add(waitlist.get(t)); //register students to course from waitlist for the index
						fullIndexlist.get(n).setVacancy((vac - 1));
						String matricNo = waitlist.remove(t);
						 //update no. of AUs of student
						 for(j=0;j<fullStudentslist.size();j++)
						 {	
							 Student s = fullStudentslist.get(j);
							 if(s.getMatriculation_Card().equals(matricNo))
							 {
								 updatedAU = s.getAUs_registered()+fullCourselist.get(m).getCourseAU();
								 s.setAUs_registered(updatedAU);
								 email.sendEmail(s.getMatriculation_Card(), fullCourselist.get(m).getCourseCode(), fullIndexlist.get(n).getIndexNo());
								 StudentIO.editStudent(s);
								 break;
							 }
						 }
					 }
					 fullIndexlist.get(n).setWaitlistStudName(waitlist);
					 fullIndexlist.get(n).setRegiStudName(registeredList);
				     CourseIO.updateIndex(fullIndexlist.get(n));
				 }
			}
		}
	}

	/**
	 * studentListSortcourse sorts students according to course
	 */
	public void studentListSortcourse(Scanner sc)
	{
		int j =0;
		boolean flag = true;
		CourseIO.displayAllCourses();
		System.out.println("Enter Course code:");
		String courseCode = sc.next();
		sc.nextLine();
		if(CourseIO.checkCourseCode(courseCode))
		{
			Course course= CourseIO.returnCourse(courseCode);
			ArrayList<Index> IndexList = course.getCourseIndex();
			 for(j=0;j<IndexList.size();j++)
			 {	
				 ArrayList<String> registered = IndexList.get(j).getRegiStudName();
				 if (registered != null) {
					 for (int k = 0; k < registered.size(); k++) {
						 flag = false;
						 System.out.println(StudentIO.nameFromMatricNo(registered.get(k)));
					 }
				 }
			 }
			 if (flag) {
					System.out.println("No students registered!");
				}
		}
		else
		{
			System.out.println("Error! Please check course code");
		}
	}
	/**
	 * studentListSortindex sorts students according to course index
	 */
	public void studentListSortindex(Scanner sc)
	{
		int i = 0;
		boolean flag = true;
		CourseIO.displayAllIndexes();
		System.out.println("Enter Index number:");
		String indexNo = sc.next();
		sc.nextLine();
		if(CourseIO.checkCourseIndex(indexNo))
		{
			Index ind_no = CourseIO.returnIndex(indexNo);
			ArrayList<String> registered = ind_no.getRegiStudName();
			if (registered != null) {
				 for (int k = 0; k < registered.size(); k++) {
					 flag = false;
					 System.out.println(StudentIO.nameFromMatricNo(registered.get(i)));
				 }
			 }
			 if (flag) {
					System.out.println("No students registered!");
				}
		}
		else
		{
			System.out.println("Error! Please check index number");
		}
	}
	/**
	 * accessor function for password
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
