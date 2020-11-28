package courses;

import java.io.Serializable;
import java.util.ArrayList;
/**   
 * Represents a course.   
 * A course contains an array of indexes.   
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-18  
 */
public class Course implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Attributes
    /**
     * The Course Code of the course.
     */
	private String CourseCode;
    /**
     * The Course Name of the course.
     */
	private String CourseName;
    /**
     * The school providing the course.
     */
	private String School;
    /**
     * The Academic Units of the course.
     */
	private int CourseAU;
    /**
     * The array of indexes in the course.
     */
   	private ArrayList<Index> CourseIndex = new ArrayList<Index>();
    /**
     * The type of the course.
     */
    private String courseType;
   
	//Constructor
    /**
     * Constructor to add a course.
	 * Initialize CourseCode, CourseName, School, CourseAU, ArrayList of index, and courseType.
     * @param CourseCode
     * @param CourseName
     * @param School
     * @param CourseAU
     * @param index
     * @param courseType
     */
	Course(String CourseCode, String CourseName, String School, int CourseAU, 
			ArrayList<Index> index,String courseType)
	{this.CourseCode = CourseCode;
	this.CourseName = CourseName;
	this.CourseAU = CourseAU;
	this.School = School;
	this.CourseIndex = index;
	this.courseType = courseType;
	}
	
	//Methods
	/**
	 * To print out course information
	 */
	public void printCourse() {
		System.out.println("Course Code: "+CourseCode);
		System.out.println("Course Name: "+CourseName);
		System.out.println("Course offered by: "+School);
		System.out.println("Course AU: "+CourseAU+"AU");
		System.out.println("Course Type: "+courseType);
		System.out.println("Course Vacancy: "+this.vacancy());
		
	}
	//
	/**
	 * To calculate course vacancy
	 * @return number of vacancy in the course
	 */
	public int vacancy() {
		int tot_vac=0;
		int ind_vac;
		for (int i=0;i<CourseIndex.size();i++) {
			ind_vac = CourseIndex.get(i).getVacancy();
			tot_vac = tot_vac + ind_vac;
			
		}
		return tot_vac;
		
	}
	
	//accessor and mutator for each attribute
    /**
     * Gets the course's code.
     * @return the code of this course.
     */
	public String getCourseCode() {
		return CourseCode;		
	}
	/**
	 * Changes the course's code
	 * @param CourseCode the course's code
	 */
	public void setCourseCode(String CourseCode) {
		this.CourseCode = CourseCode;
	}
    /**
     * Gets the course's name.
     * @return the name of this course.
     */
	public String getCourseName() {
		return CourseName;		
	}
	/**
	 * Changes the course's name.
	 * @param CourseName the course's name.
	 */
	public void setCourseName(String CourseName) {
		this.CourseName = CourseName;
	}
    /**
     * Gets the course's type.
     * @return the type of this course.
     */
	public String getcourseType() {
		return courseType;		
	}
	/**
	 * Changes the course's type.
	 * @param courseType the course's type.
	 */
	public void setcourseType(String courseType) {
		this.courseType = courseType;
	}
    /**
     * Gets the course's AU.
     * @return the AU of this course.
     */
	public int getCourseAU() {
		return CourseAU;		
	}
	/**
	 * Changes the course's AU.
	 * @param CourseAU the course's AU.
	 */
	public void setCourseAU(int CourseAU) {
		this.CourseAU = CourseAU;
	}
    /**
     * Gets the course's index arraylist.
     * @return the index arraylist of this course.
     */
	public ArrayList<Index> getCourseIndex() {
		return CourseIndex;
	}
	/**
	 * Changes the course's index arraylist.
	 * @param index course's index arraylist.
	 */
	public void setCourseIndex(ArrayList<Index> index) {
		this.CourseIndex = index;
	}


	
}



