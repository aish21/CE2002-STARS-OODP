package displayIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Parent class that contains the functions to read and write from a binary file
 * @author  Zhang Yilin
 * @author  Kesarimangalam Srinivasan Abhinaya
 * @author  Musthiri Sajna
 * @author  Singh Aishwarya
 * @author  Unnikrishnan Malavika
 * @version 1.0   
 * @since   2020-11-17
 */
public abstract class IO {
	
	/**
	 * Variable to store the file path to the binary file
	 */
	public static String filepath = null;

	public static final void  writeSerializedObject(List list) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filepath);
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
		//	System.out.println("Object Persisted");
		} catch (IOException ex) {
			//ex.printStackTrace();
		}
	}
	
	public static final List readSerializedObject() {
		List Details = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filepath);
			in = new ObjectInputStream(fis);
			Details = (ArrayList) in.readObject();
			in.close();
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
	
		return Details;
	}
	
	
	// Encrypts text using a shift of s 
    public static String encrypt(String text, int s) 
    { 

        StringBuffer result = new StringBuffer();
        for (int i=0; i<text.length(); i++) 
        { 
        	int val = text.charAt(i) + s;
        	val = val % 256;
        	result.append((char)val);
        } 
        return result.toString(); 
    }
    
  //function to write an object to the file
  	protected static void WriteObjectToFile(Object serObj) {
          try {
          	File file = new File (filepath);
          	ObjectOutputStream out = null;
              if (!file.exists ()) 
              	out = new ObjectOutputStream ( new FileOutputStream(filepath));
              else {
              	out = new AppendableObjectOutputStream (new FileOutputStream(filepath,true));
              }
              out.writeObject(serObj);
              //System.out.println("The Object was successfully written to a file");
              out.close();
          } catch (Exception ex) {
          	ex.printStackTrace();
              return;
          }
      }
  	
      //Modifying the output stream so that objects can be appended to the binary file
  	private static class AppendableObjectOutputStream extends ObjectOutputStream {
          public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
          }

          @Override
          protected void writeStreamHeader() throws IOException {

          	}
          }
  	
  	//Function to return a list of all the objects  in the file
  	protected static ArrayList<Object> ReadObjectFromFile() {
  		ArrayList<Object> objectsList = new ArrayList<Object>();
  		try {	
  		FileInputStream fis = new FileInputStream(filepath);
          	ObjectInputStream input = new ObjectInputStream(fis);
          	while (fis.available() != 0) {
          	    Object obj = input.readObject();
          	      objectsList.add(obj);
          	  }
          	 fis.close();
          	return objectsList;
          } catch (Exception ex) {
          	ex.printStackTrace();
          	return objectsList;
          }
          	
  	}
}
