
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


class CustomerThread extends Thread {

  private DataInputStream fromClient = null;
  private PrintStream toClient = null;
  private Socket customerSocket = null;
  private final CustomerThread[] customerthreads;
  private int maxCustomersCount;

  public CustomerThread(Socket customerSocket, CustomerThread[] threads) {
	    this.customerSocket = customerSocket;
	    this.customerthreads = threads;
	    maxCustomersCount = threads.length;
	  }


  public void run() {
    int maxClientsCount = this.maxCustomersCount;
    CustomerThread[] customerthreads = this.customerthreads;

    try {
      /*
       * Create input and output streams for this customer.
       */
      fromClient = new DataInputStream(customerSocket.getInputStream());
      toClient = new PrintStream(customerSocket.getOutputStream());
      toClient.println("Enter your name.");
      String name = fromClient.readLine().trim();
      toClient.println("Hello " + name
          + " to our chat room.\nTo leave enter /quit in a new line");
      for (int i = 0; i < maxClientsCount; i++) {
        if (customerthreads[i] != null && customerthreads[i] != this) {
        	customerthreads[i].toClient.println("*** A new user " + name
              + " entered the chat room !!! ***");
        }
      }
      while (true) {
        String line = fromClient.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
        for (int i = 0; i < maxClientsCount; i++) {
          if (customerthreads[i] != null) {
        	  customerthreads[i].toClient.println("<" + name + " :  " + line);
          }
        }
      }
      for (int i = 0; i < maxClientsCount; i++) {
        if (customerthreads[i] != null && customerthreads[i] != this) {
        	customerthreads[i].toClient.println("*** The user " + name
              + " is leaving the chat room !!! ***");
        }
      }
      toClient.println("*** Bye " + name + " ***");

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      for (int i = 0; i < maxClientsCount; i++) {
        if (customerthreads[i] == this) {
        	customerthreads[i] = null;
        }
      }

      /*
       * Close the output stream, close the input stream, close the socket.
       */
      toClient.close();
      fromClient.close();
      customerSocket.close();
    } catch (Exception e) {
    	System.out.println("Exception from ClientThread"+ e);
    }
  }
}