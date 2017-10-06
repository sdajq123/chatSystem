import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class AgentThread extends Thread{
	  private DataInputStream fromClient = null;
	  private PrintStream toClient = null;
	  private Socket agentSocket = null;
	  private static ArrayList<Socket> agentSocketarr = new ArrayList<Socket>();
	  
	  public AgentThread(Socket agentSocket) {
		    this.agentSocket = agentSocket;    
		  }


	  public void run() {
	  
	    try {
	      /*
	       * Create input and output streams for this customer.
	       */
	    
	      
	      fromClient = new DataInputStream(agentSocket.getInputStream());
	      toClient = new PrintStream(agentSocket.getOutputStream());
	      
	      System.out.println("Agent thread running");
	   
	      while (true) {
	        String line = fromClient.readLine();
	        if (line.startsWith("EXIT")) {
	          break;
	        }

	        
	        
	        System.out.println(line);
	        Scanner inputServer = new Scanner(System.in);
	        System.out.println("R Agent Server:> ");
	        String reply = inputServer.nextLine();
	        toClient.println(reply);
	       
	      }
	      toClient.println("*** Bye " + " ***");
	      toClient.close();
	      fromClient.close();
	      agentSocket.close();
	    } catch (Exception e) {
	    	System.out.println("Exception from ClientThread"+ e);
	    }
	  }
}
