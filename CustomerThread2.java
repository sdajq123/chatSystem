import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CustomerThread2 {
	private DataInputStream fromClient = null;
	  private PrintStream toClient = null;
	  private Socket customerSocket = null;

	  public CustomerThread2(Socket customerSocket) {
		    this.customerSocket = customerSocket;    
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
	        System.out.println("Enter reply message");
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
