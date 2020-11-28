package courses;

import java.io.Serializable;
import java.time.*;

/**   
 * Represents a class session of a course, under certain index.   
 * A session can be lecture, tutorial or lab.   
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17  
 */
public class Session implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The start time of the session.
     */
    private LocalTime starttime;
    
    /**
     * The end time of the session.
     */
	private LocalTime endtime;
    
	/**
     * The location of the session.
     */
	private String location;
    
	/**
     * The day number of the week of the session.
     * day 1-5 represents Mon to Fri
     */
	private int day;
	
	/**
	 * Constructor to add a session in a course index.
	 * Initialize day, start time, end time, venue and indexid of the session.
	 * @param day day of the week.
	 * @param st start time.
	 * @param et end time.
	 * @param venue venue of the class.
	 * @param indexid index ID that this session belongs to.
	 */
	public Session(int day, LocalTime st, LocalTime et, String loc)
	{
		this.day = day;
		this.starttime =st;
		this.endtime = et;
		this.location = loc;	}
	
	/**
	 * Get start time of the session.
	 * @return start time
	 */
	public LocalTime getStarttime() {
		return starttime;
	}
	
	/**
	 * Change start time of the session.
	 * @param starttime new start time
	 */
	public void setStarttime(LocalTime starttime) {
		this.starttime = starttime;
	}
	
	/**
	 * Get end time of the session.
	 * @return end time
	 */
	public LocalTime getEndtime() {
		return endtime;
	}
	
	/**
	 * Change end time of the session.
	 * @param endtime new end time
	 */
	public void setEndtime(LocalTime endtime) {
		this.endtime = endtime;
	}
	
	/**
	 * Get venue of the session.
	 * @return venue
	 */
	public String getVenue() {
		return location;
	}
	
	/**
	 * Change venue of the session.
	 * @param venue
	 */
	public void setVenue(String venue) {
		this.location = venue;
	}
	
	/**
	 * Get day of the session.
	 * @return day
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Change day of the session.
	 * @param day
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
	/**
	 * Print out the day of the week.
	 * @param i day number
	 */
	public static void printDay(int i){ 
	      switch(i){ 
	          case 1: System.out.print("Monday"); break; 
	          case 2: System.out.print("Tuesday");break; 
	          case 3: System.out.print("Wednesday");break; 
	          case 4: System.out.print("Thursday");break; 
	          case 5: System.out.print("Friday");break; 
	          default:System.out.print("wrong number!"); 
         } 
	} 
	public boolean checkSessionClash(Session s) {
		if (this.getDay() != s.getDay()) 
			return false;
		else  {
			if (this.getStarttime()==s.getStarttime() )
				return true;
			else if (this.getEndtime()==s.getEndtime() )
				return true;
			else if (this.getStarttime().isAfter( s.getStarttime() ) && this.getStarttime().isBefore( s.getEndtime() ))
				return true;
			else if (this.getEndtime().isAfter( s.getStarttime() ) && this.getEndtime().isBefore( s.getEndtime() ))
				return true;
			else if (s.getEndtime().isAfter( this.getStarttime() )&& s.getEndtime().isBefore( this.getEndtime() ))
				return true;
			else if (s.getStarttime().isAfter( this.getStarttime() ) && s.getStarttime().isBefore( this.getEndtime() ))
				return true;
			else return false;
		}
		
	}

	
}