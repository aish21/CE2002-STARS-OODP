package faculty;

import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Arrays;
import student.*;

/**
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17
 */

public class accessTime {
	private static String[] nowDateTime,inpDateTime,nowDateonly,nowTimeonly,inpDateonly,inpTimeonly;
	private static String formatDateTime,dateTime;
	public static void setnowTime() //function to set current time
	{
		LocalDateTime now = LocalDateTime.now();    
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
        formatDateTime = now.format(format);  
        System.out.println("Time now: " + formatDateTime);  
        nowDateTime = formatDateTime.split(" ");		
	}
	public static void inputTime(Scanner sc) //function to input accessTime for setting
	{
		setnowTime(); //function to set current time 
		System.out.println("Enter student access date and time (dd-MM-yyyy HH:mm:ss): ");
		sc.nextLine();
		dateTime= sc.nextLine();
		inpDateTime = dateTime.split(" ");
	}
	public static void splitTimeFormat(String[] nowDT, String[] inpDT) //function to split Date and time into dd,mm,yyyy and HH,mm,ss respectively
	{
		nowDateonly = nowDT[0].split("-");
    	nowTimeonly = nowDT[1].split(":");
    	inpDateonly = inpDT[0].split("-");
    	inpTimeonly = inpDT[1].split(":");
        int flag =1; //flag to detect incorrect input. 

        if(2020<=Integer.parseInt(nowDateonly[2])&& Integer.parseInt(nowDateonly[2])<=2030)
        {
            if(1<=Integer.parseInt(nowDateonly[1]) && Integer.parseInt(nowDateonly[1])<=12)
            {
                if(0<Integer.parseInt(nowDateonly[0]) && Integer.parseInt(nowDateonly[0])<=30)
                {
                    flag = 0; //flag set to 0 if all data is valid
                } 
            }
        }
    	if(flag==1)
        {
            System.out.println("Error! Please check if date and time are in correct range");

        }
	}
	public static String setAccessTime(Scanner sc) {  
        inputTime(sc); //function to input accessTime for setting
		int flag =0,count=0;
		while(flag==0) {
        if (formatDateTime!=dateTime)
        {
        	//function to split Date and time into dd,mm,yyyy and HH,mm,ss respectively
        	splitTimeFormat(nowDateTime,inpDateTime);
        	
        	//convert string array to int array 
        	int[] Date_array = Arrays.stream(inpDateonly).mapToInt(Integer::parseInt).toArray();
        	int[] nowDate_array = Arrays.stream(nowDateonly).mapToInt(Integer::parseInt).toArray();
        	int[] Time_array = Arrays.stream(inpTimeonly).mapToInt(Integer::parseInt).toArray();
        	int[] nowTime_array = Arrays.stream(nowTimeonly).mapToInt(Integer::parseInt).toArray();
        	count = 0; //count to check if day is same
        	
        	//loop to check if date has past
        	for(int i=Date_array.length - 1 ; i>=0 ; i-- )
        	{
        		if(Date_array[i]<nowDate_array[i])
        		{
        			System.out.println("This date has passed!");
        			flag = 1;
        			break;
        		}
        		else if(Date_array[i]==nowDate_array[i])
        		{
        			count+=1;
        		}
        	}
        	
        	if(flag==0)
        	{ //when date is same, check time
	        	if(count==3)
        		{
	        		if (Time_array[0] < nowTime_array[0]) {
	        			System.out.println("This time has passed!");
	        			flag = 1;
        			}
	        		else if (Time_array[0] == nowTime_array[0] && Time_array[1] < nowTime_array[1]) {
	        			System.out.println("This time has passed!");
	        			flag = 1;
	        		}
	        		else if (Time_array[0] == nowTime_array[0] && Time_array[1] == nowTime_array[1] && Time_array[2] < nowTime_array[2]) {
	        			System.out.println("This time has passed!");
	        			flag = 1;
	        		}
        		}
	        	if(flag==0)
	        	{
	        		System.out.println("Access time has been set.\n"); 
	        		flag=1;
	        		return dateTime;
	        	}
        	}
        	
        }
        else
        {
        	System.out.println("Date has already been set!");
        }
	}
        return null; 
    }  
	public static int checkAccessTime(Student s1)
	{
		setnowTime();
		int count = 0,flag =0; //count to check if day is same
		String studAccessTime[] = s1.getAccessTime().split(" ");
		splitTimeFormat(nowDateTime,studAccessTime);
		//student's access time
    	int[] Date_array = Arrays.stream(inpDateonly).mapToInt(Integer::parseInt).toArray(); 
    	int[] Time_array = Arrays.stream(inpTimeonly).mapToInt(Integer::parseInt).toArray();
    	
    	//present time
    	int[] nowDate_array = Arrays.stream(nowDateonly).mapToInt(Integer::parseInt).toArray();
    	int[] nowTime_array = Arrays.stream(nowTimeonly).mapToInt(Integer::parseInt).toArray();
    		
		//loop to check if date is in the future or is current date
    	for(int i=Date_array.length - 1 ; i>=0 ; i-- )
    	{
    		if(Date_array[i]==nowDate_array[i])
    		{
    			count += 1;
    		}
    		else if(Date_array[i]>nowDate_array[i])
    		{
    			flag = -1; //Access Restricted
    		}
    		else if(Date_array[i]<nowDate_array[i])
    		{
    			flag = 0; //You have missed your date!
    		}
    		
    	}
    	//when date is the same, check if time is within 3 hour limit
    	if(count==3)
		{
    		if(Time_array[0] < nowTime_array[0] || (Time_array[0] == nowTime_array[0] && Time_array[1] <= nowTime_array[1]))
    		{
    			if(nowTime_array[0]<=Time_array[0]+3)
    			{
    				System.out.println("You can now enter!");
    				flag = 1; //you can enter
    			}
    			else
    			{
    				flag = 0; //You have missed your date!
    			}
    		}
    		else
    		{
    			flag = -1;
    		}
		}
    	return flag;
	}
}