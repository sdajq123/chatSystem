import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ConnectionHandler extends Thread{
       Socket client;
      
      public ConnectionHandler(Socket socket) {
    	  client = socket; 
      }
      
      public void run() {
    	  String address = client.getInetAddress().toString();
    	  try {
    		  try {
    			  System.out.println("Connection from "+ address);
    			  
    			  BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
    			  System.out.println("Client instrunction : "+ fromClient.readLine());
                  
    			
  				//sending or writing data from the socket 
  				
  				System.out.println("Server sending to the client");
  				//write data to the socket 
  				PrintWriter toClient = new PrintWriter(client.getOutputStream(),true);
  				toClient.println("Welcome- sever running ");
  				
  				//Receiving or reading data from the socket 
  				BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
  				String strfromClient = input.readLine();
  			
  				String [] test = strfromClient.split(";");
  				String reply= "";
  				if(test[0].equals("JQ")&&test[1].equals("123")){
  					reply = "C";
  				}
  				else{
  					reply = "W";
  				}
  				
  				//Sending or writing data from the socket 
  				toClient.println(reply);
    			  toClient.flush();
    			  client.close();
    			  
    		  }finally{
    			  client.close();
    		  }
    	  }catch(Exception ex) {
    		  System.out.println("Hander exception");
    	  }
      }
}
