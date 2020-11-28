package emailClient;

import java.util.Properties;
import courses.Course;
import courses.CourseIO;
import student.Student;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import student.StudentIO;

/**
 * File implements 'notification' function
 * Informs student via Official NTU Email (Outlook 365) notification
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17
 */
public class email{
    public static void sendEmail(String matricNo, String courseCode, String indexNumber) {
        final String username = "OODP_SE2_STARS@outlook.com"; //Enter your Outlook 365 username here (Testing)
        final String password = "Assignment"; //Enter your Outlook 365 password here (Testing)
        
        /**
         * Properties of Email Service provider defined here
         */
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");
        
        /**
         * New session is Authenticated
         */
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          }
        );

        try {
        	/**
        	 * Email is generated and sent here
        	 */
            Message message = new MimeMessage(session);
            Student S = StudentIO.getStudent(matricNo);
            Course C = CourseIO.returnCourse(courseCode);
            String toEmail = S.getUsername() + "@e.ntu.edu.sg";
            //Enter your Outlook 365 username here (Testing)
            message.setFrom(new InternetAddress(username)); 
            message.setRecipients(Message.RecipientType.TO, 
            		//Enter reciever's Outlook 365 username here (Testing)
            		InternetAddress.parse(toEmail));
            message.setSubject("Course Allocated!");
            String content = "Dear " + S.getStudentName() + ",\n" + "\nStudent Name:\t" + S.getStudentName() + 
            "\nMatriculation Number:\t" + S.getMatriculation_Card() + 
            "\n\nWe are please to inform you that you have been allocated the following course in Semester 2, AY 2020-21.\n" + 
            C.getCourseCode() + "\t" + C.getCourseName() + "\t" + indexNumber + 
            "\n\nPlease view Print Courses Registered to view your class timetable and attend the right classes based on the registered index numbers." + 
            "\n\nRegards\nSTARS Team";
            message.setText(content);

            Transport.send(message);

        } catch (MessagingException e) {
        	/**
        	 * Errors, Exception - displayed in console 
        	 */
            throw new RuntimeException(e);
        }
    }
}