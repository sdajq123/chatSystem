

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client4A{

  // The client socket
  private static Socket clientSocket = null;
  private static boolean closed = false;
  
  public static void main(String[] args) {

    // The default port.
    int portNumber = 8080;
    // The default host.
    String host = "localhost";

    try {
    	try{
    		clientSocket = new Socket(host, portNumber);
    		
    		PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(),true); 
    	     Scanner input = new Scanner(System.in);
    	     
    	    //receving welcome message from server
    		BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    		  String welcome = fromServer.readLine();
    		  System.out.println(welcome);
    		  
    	    //sending user details to the server 
    		  System.out.println("Enter your name role and details ");
    		  String name = input.nextLine();
    		  toServer.println(name);
    		  
    		  
    		  //receving connection confirmation from the server
    		 
    		  welcome = fromServer.readLine();
    		  System.out.println(welcome);
    		 
    		  
    		  
    		  
    		  
    		
    		  
    	}finally{
    		clientSocket.close();
    	}
    }catch (Exception e) {
      System.out.println("Don't know about host " + host);
    } 
    }
}
   
  