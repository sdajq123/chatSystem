
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


class AgentThread extends Thread {

  private DataInputStream fromClient = null;
  private PrintStream toClient = null;
  private Socket clientSocket = null;
  private int maxClientsCount;

  public AgentThread(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {

    try {
      /*
       * Create input and output streams for this client.
       */
      fromClient = new DataInputStream(clientSocket.getInputStream());
      toClient = new PrintStream(clientSocket.getOutputStream());
      toClient.println("Enter your name.");
      String name = fromClient.readLine().trim();
      toClient.println("Hello " + name
          + " to our chat room.\nTo leave enter /quit in a new line");
      System.out.println("*** A new user " + name
              + " entered the chat room !!! ***");
     
     while (true) {
        String line = fromClient.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
       
            toClient.println("<" + name + ":  " + line);
         
        }
    
       toClient.println("*** Bye " + name + " ***");
       		toClient.close();
	     fromClient.close();
	     clientSocket.close();
    }catch(Exception ex){
    	  System.out.println("Exception from Agent Thread");
      }
	     
      }

  }
