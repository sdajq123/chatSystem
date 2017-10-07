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
	  public static String linefromAgent;
	  
	  public AgentThread(Socket agentSocket) {
		    this.agentSocket = agentSocket; 
		    agentSocketarr.add(agentSocket);
		  }


	  public void run() {
	  
	    try {      
	      fromClient = new DataInputStream(agentSocket.getInputStream());
	      toClient = new PrintStream(agentSocket.getOutputStream());
	      
	      System.out.println("Agent thread running");
	      while (true) {
			   	fromClient = new DataInputStream(agentSocket.getInputStream());
					linefromAgent = fromClient.readLine();
		        if (linefromAgent.startsWith("EXIT")) {      	 
		        	toClient.println("*** Bye " + " ***");	
		        	agentSocket.close();
		          break;
		        }    
		  }
		  fromClient.close(); 
	     // toClient.print("Enter request: >");
	   
	          
	    } catch (Exception e) {
	    	System.out.println("Exception from ClientThread"+ e);
	    }
	  }

		
		public void write(String reply){
			  try{
			   toClient = new PrintStream(agentSocket.getOutputStream());		
			   toClient.print("Agent: >");
			   toClient.println(reply);
			  toClient.close(); 
			  }catch(Exception ex){
				  System.out.println("Exception");
			  }
		}

}
