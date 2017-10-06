import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerThread extends Thread{
	private DataInputStream fromClient = null;
	  private PrintStream toClient = null;
	  private Socket customerSocket = null;
	  private static ArrayList<Socket> customerSocketarr = new ArrayList<Socket>();

	  public CustomerThread(Socket customerSocket,ArrayList arr) {
		    this.customerSocket = customerSocket; 
		    this.customerSocketarr = arr;
		  }


	  public void run() {
	  
	    try {
	      /*
	       * Create input and output streams for this customer.
	       */
	      
	      fromClient = new DataInputStream(customerSocket.getInputStream());
	      toClient = new PrintStream(customerSocket.getOutputStream());
	      
	      System.out.println("Customer thread running");
	   
	      while (true) {
	        String line = fromClient.readLine();
	        if (line.startsWith("EXIT")) {
	          break;
	        }
	      
	        System.out.println(line);
	        Scanner inputServer = new Scanner(System.in);
	
	        System.out.print("C Server :>");
	        String reply = inputServer.nextLine();
	        toClient.println(reply);
	       
	     
	      }
	      toClient.println("*** Bye " + " ***");
	      toClient.close();
	      fromClient.close();
	      customerSocket.close();
	    } catch (Exception e) {
	    	System.out.println("Exception from ClientThread"+ e);
	    }
	  }
	  
}
