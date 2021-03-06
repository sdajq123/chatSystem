import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MainServer {
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	

	  public static void main(String args[]) {
	    int portNumber = 8080;
	    
	    try {
	      serverSocket = new ServerSocket(portNumber);
	      System.out.println("Server sending to the client");
	    } catch (IOException e) {
	      System.out.println(e);
	    }

	   
	      try {
	    	  try{
	         while (true) {
	        	 clientSocket = serverSocket.accept();
			PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(),true);
			
			//Receiving or reading data from the socket, reading the client role 
			BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String role = fromClient.readLine();
			
			//check if the role is customer
			if(role.equals("Customer")){
				toClient.println("Welcome to our chat system customer");//until here test okay already 
				//create 2 threads for the customer 
				CustomerThread[] customerthreads = new CustomerThread[2];
				CustomerThread clientThread = new CustomerThread(clientSocket,customerthreads);
				clientThread.start();
				
			}
			//check if the role is agent 
			else if(role.equals("Agent")){
				toClient.println("Welcome to our chat system agent");
				//create 1 thread for agent
				AgentThread agentThread = new AgentThread(clientSocket);
				agentThread.start();
			}
			
	         }
	      }finally{
	    	  clientSocket.close();  
	      }
	      }catch(Exception ex){
	    	  System.out.println("Exception from server"+ ex);
	      }
	    
	  }
}