import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerThread extends Thread{
	private DataInputStream fromClient = null;
	  private PrintStream toClient = null;
	  private Socket customerSocket = null;
	  private static ArrayList<Socket> customerSocketarr = new ArrayList<Socket>();
      public String linefromCustomer;
	  public CustomerThread(Socket customerSocket) {
		    this.customerSocket = customerSocket; 
		  }

	  public void run() {
	  
	    try {	 
	      System.out.println("Customer thread running"); 
	     // toClient.print("Enter request: >");
	      while (true) {
			   	fromClient = new DataInputStream(customerSocket.getInputStream());
					linefromCustomer = fromClient.readLine();
		        if (linefromCustomer.startsWith("EXIT")) {      	 
		        	toClient.println("*** Bye " + " ***");	
		        	customerSocket.close();
		          break;
		        }    
		  }
		  fromClient.close();
	    } catch (Exception e) {
	    	System.out.println("Exception from ClientThread"+ e);
	    }
	  }
	  
	  
	  public void write(String reply){
		  try{
			  toClient = new PrintStream(customerSocket.getOutputStream()); 
		      toClient.println(reply);
		      toClient.close(); 
		  }catch(Exception ex){
			  System.out.println("Exception from write customer thread");
		  }
	  }
	  
 }
	  